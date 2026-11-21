package com.yoterra.hippo.services;

import com.yoterra.hippo.exceptions.MissingEntityException;
import com.yoterra.hippo.exceptions.OwnedItemIllegalOperationException;
import com.yoterra.hippo.jpa.entities.Company;
import com.yoterra.hippo.jpa.entities.RequestAccess;
import com.yoterra.hippo.jpa.entities.RequestAccessStatus;
import com.yoterra.hippo.jpa.entities.UserCompany;
import com.yoterra.hippo.jpa.entities.users.User;
import com.yoterra.hippo.jpa.repositories.CompanyRepository;
import com.yoterra.hippo.jpa.repositories.RequestAccessRepository;
import com.yoterra.hippo.jpa.repositories.UserCompanyRepository;
import com.yoterra.hippo.jpa.repositories.UserRepository;
import com.yoterra.hippo.security.ActiveUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Supplier;

@Service
public class RequestAccessService {

    private static final Logger log = LoggerFactory.getLogger(RequestAccessService.class);

    private Supplier<MissingEntityException> MISSING_REQUEST_ACCESS_EXC_SUPPLIER =
            ()->new MissingEntityException("The request access does not exist or it has been deleted or deactivated.");
    private Supplier<MissingEntityException> MISSING_COMPANY_EXC_SUPPLIER =
            ()->new MissingEntityException("The company does not exist or it has been deleted or deactivated.");

    @Autowired
    RequestAccessRepository requestAccessRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserCompanyRepository userCompanyRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Transactional
    public Page<RequestAccess> getRequestAccess(Pageable pageable){
        User active = ActiveUser.get();
        Page<RequestAccess> page = requestAccessRepository.findByAdmin(active,pageable);
        return page;
    }

    private void grantAccess(RequestAccess requestAccess){
        if( requestAccess.getStatus() == RequestAccessStatus.ACCEPTED)
            throw new IllegalArgumentException("The access is already granted to the user.");

        requestAccess.setStatus(RequestAccessStatus.ACCEPTED);
        requestAccessRepository.save(requestAccess);
        User user = requestAccess.getUser();
        if(user.getDisabled()) {
            user.setDisabled(false);
            userRepository.save(user);
        }
        UserCompany userCompany = new UserCompany();
        userCompany.setUser(user);
        userCompany.setUserRole(requestAccess.getUserRole());
        Company company = companyRepository.findByWebsite(requestAccess.getCompanyWebsite()).orElseThrow(MISSING_COMPANY_EXC_SUPPLIER);
        userCompany.setCompany(company);
        userCompanyRepository.save(userCompany);
    }


    public RequestAccess grantRequestAccess(long id){
        RequestAccess requestAccess = requestAccessRepository.findById(id).orElseThrow(MISSING_REQUEST_ACCESS_EXC_SUPPLIER );
        if(ActiveUser.idIs(requestAccess.getAdmin().getId())) {
            grantAccess(requestAccess);
        }
        else
            throw new OwnedItemIllegalOperationException("You are not authorized to grant this access.");

        return requestAccess;
    }


    @Transactional(readOnly = false)
    public void rejectAccess(RequestAccess requestAccess){
        if( requestAccess.getStatus() == RequestAccessStatus.REJECTED)
            throw new IllegalArgumentException("The access is not granted to the user. You can not revoke it.");

        requestAccess.setStatus(RequestAccessStatus.REJECTED);
        requestAccessRepository.save(requestAccess);
    }

    @Transactional(readOnly = false)
    public void deniedAccess(String website, User user){
        Company company = companyRepository.findByWebsite(website).orElseThrow(MISSING_COMPANY_EXC_SUPPLIER);
        userCompanyRepository.deleteByUserAndCompany(user,company);
    }

    @Transactional(readOnly = false)
    public RequestAccess revokeRequestAccess(long id){
        RequestAccess requestAccess = requestAccessRepository.findById(id).orElseThrow(MISSING_REQUEST_ACCESS_EXC_SUPPLIER );
        if(ActiveUser.idIs(requestAccess.getAdmin().getId())) {
            rejectAccess(requestAccess);
            deniedAccess(requestAccess.getCompanyWebsite(),requestAccess.getUser());
        }
        else
            throw new OwnedItemIllegalOperationException("You are not authorized to revoke this access.");

        return requestAccess;
    }
}
