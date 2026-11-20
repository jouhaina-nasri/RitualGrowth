package com.yoterra.hippo.services;

import com.yoterra.hippo.exceptions.DuplicateElementException;
import com.yoterra.hippo.exceptions.MissingEntityException;
import com.yoterra.hippo.exceptions.permissions.DeletePermissionDeniedException;
import com.yoterra.hippo.forms.CompanyAddForm;
import com.yoterra.hippo.forms.CompanyCreateForm;
import com.yoterra.hippo.forms.CompanyEditForm;
import com.yoterra.hippo.jpa.entities.*;
import com.yoterra.hippo.jpa.entities.users.User;
import com.yoterra.hippo.jpa.entities.UserRole;
import com.yoterra.hippo.jpa.repositories.CompanyRepository;
import com.yoterra.hippo.jpa.repositories.RequestAccessRepository;
import com.yoterra.hippo.jpa.repositories.UserCompanyRepository;
import com.yoterra.hippo.jpa.repositories.UserRepository;
import com.yoterra.hippo.search.qbuilders.CompanyQueryBuilder;
import com.yoterra.hippo.search.requests.CompanySearchRequest;
import com.yoterra.hippo.security.ActiveUser;
import com.yoterra.hippo.security.model.AuthToken;
import com.yoterra.hippo.services.auth.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Calendar;
import java.util.function.Supplier;

@Service
public class CompanyService {

    private Supplier<MissingEntityException> MISSING_USER_EXC_SUPPLIER =
            ()->new MissingEntityException("The user account does not exist or it has been deleted or deactivated.");

    private Supplier<MissingEntityException> MISSING_Company_EXC_SUPPLIER =
            ()->new MissingEntityException("The company does not exist or it has been deleted or deactivated.");
    private Supplier<IllegalAccessError> ILLEGAL_ACCESS_USER_Company_EXC_SUPPLIER =
            ()->new IllegalAccessError("You are not authorized to access this company.");

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CompanyQueryBuilder companyQueryBuilder;

    @Autowired
    RequestAccessRepository requestAccessRepository;

    @Autowired
    AuthorizationService auth;

    @Autowired
    UserCompanyRepository userCompanyRepository;



    public Page<Company> searchCompanies(CompanySearchRequest searchRequest){
        Specification<Company> spec = companyQueryBuilder.build(searchRequest);
        Page<Company> page = companyRepository.findAll(spec, searchRequest.getPageable());
        return page;
    }

    public Page<Company> getAllowedCompanies(Pageable pageable){
        User user = ActiveUser.get();
        Page<Company> companies = userCompanyRepository.getCompaniesByUser(user,pageable);
        return companies;
    }

    public Page<Company> getCompanies(Pageable pageable){
        User user = ActiveUser.get();
        Page<Company> companies = companyRepository.findByOwner(user,pageable);
        return companies;
    }

    public Company getCompany(long id){
        User user = ActiveUser.get();
        Company company = companyRepository.findById(id).orElseThrow(MISSING_Company_EXC_SUPPLIER);
        UserCompany access = userCompanyRepository.findByUserAndCompany(user,company).orElseThrow(ILLEGAL_ACCESS_USER_Company_EXC_SUPPLIER);
        return company;
    }

    @Transactional(readOnly = true)
    private void checkUserDetails(CompanyCreateForm form, AuthToken token) throws DuplicateElementException {

        if(userRepository.includeDeactivated_countByApUid(token.getApUid()) > 0)
            throw new DuplicateElementException("The user already exists.");
        if(userRepository.includeDeactivated_countByEmail(token.getEmail()) > 0)
            throw new DuplicateElementException("A user with the specified e-mail address already exists.");
    }

    @Transactional
    public User addNewUser(CompanyCreateForm form, AuthToken token,Boolean status) throws DuplicateElementException{
        checkUserDetails(form, token);
        User user = new User();
        user.setEmail(token.getEmail());
        user.setApUid(token.getApUid());
        user.setCreationTime(Calendar.getInstance());
        user.setFullName(form.getFullName());
        user.setDisabled(status);
        userRepository.save(user);

        return user;
    }

    public RequestAccess requestAccessToCompany(CompanyCreateForm form) throws DuplicateElementException {
        User user = requestAccessUser(form);
        Company company = companyRepository.findByWebsite(form.getWebsite()).orElseThrow(MISSING_Company_EXC_SUPPLIER);
        RequestAccess requestAccess = new RequestAccess();
        requestAccess.setCreationTime(Calendar.getInstance());
        requestAccess.setStatus(RequestAccessStatus.CREATED);
        requestAccess.setUser(user);
        requestAccess.setAdmin(company.getOwner());
        requestAccess.setCompanyWebsite(form.getWebsite());
        requestAccess.setUserRole(UserRole.valueOf(form.getUserRole().getAlias()));
        requestAccessRepository.save(requestAccess);
        return requestAccess;
    }

    private void grantAccess(User user, Company company,String userRole){
        UserCompany userCompany = new UserCompany();
        userCompany.setUser(user);
        userCompany.setCompany(company);
        userCompany.setUserRole(UserRole.valueOf(userRole));
        userCompanyRepository.save(userCompany);
    }

    public User getUser(CompanyCreateForm form) throws DuplicateElementException {
        User user;
        Boolean userExits = userRepository.existsByEmail(form.getEmail());
        if(userExits)
            user =  ActiveUser.get();
        else {
            Calendar ts = Calendar.getInstance();
            AuthToken token = ActiveUser.getToken(true);
             user = addNewUser(form, token, false);
        }
        return user;
    }

   public User requestAccessUser(CompanyCreateForm form) throws DuplicateElementException {
        User user;
        Boolean userExits = userRepository.existsByEmail(form.getEmail());
        if(userExits)
            throw new DuplicateElementException("The user already exists.");
        else {
            Calendar ts = Calendar.getInstance();
            AuthToken token = ActiveUser.getToken(true);
            user = addNewUser(form, token, true);
        }
        return user;
    }

    private Company createCompany(User user, String name, String website,String userRole){
        Calendar ts = Calendar.getInstance();
        Company company = new Company();
        company.setOwner(user);
        company.setName(name);
        company.setWebsite(website);
        company.setCreationTime(ts);
        company.setUpdateTime(ts);
        company = companyRepository.save(company);
        grantAccess(user,company,userRole);
        return company;
    }
     public Company createUserCompany(CompanyCreateForm form) throws DuplicateElementException {
        User user = getUser(form);
        Company company = createCompany(user, form.getFullName(), form.getWebsite(),form.getUserRole().getAlias());
        return company;
    }

     public Company register(CompanyCreateForm form ) throws DuplicateElementException {
        Company company = null;

             Boolean companyExists = companyRepository.existsByWebsite(form.getWebsite());
             if(companyExists)
                 requestAccessToCompany(form);
             else
                 company = createUserCompany(form);

        return company;
    }

    public Company addCompany(CompanyAddForm form ) throws DuplicateElementException {
        User user = ActiveUser.get();
        Company company = createCompany(user, form.getName(), form.getWebsite(),form.getUserRole().getAlias());
        return company;
    }

    @Transactional
    public Company deleteCompany(Long id) throws EntityNotFoundException {

        Company company = companyRepository.findById(id).orElseThrow(MISSING_Company_EXC_SUPPLIER);

        auth.ownedOrSuperuser(company)
                .authorize(()->new DeletePermissionDeniedException("You are not authorized to delete this company."));

        companyRepository.delete(company);
        return company;
    }

    @Transactional
    public Company editCompany(CompanyEditForm form) throws EntityNotFoundException {

        Company company = companyRepository.findById(form.getCompanyId()).orElseThrow(MISSING_Company_EXC_SUPPLIER);

        auth.ownedOrSuperuser(company)
                .authorize(()->new DeletePermissionDeniedException("You are not authorized to edit this company."));
        company.setUpdateTime(Calendar.getInstance());
        company.setName(form.getName());
        company.setWebsite(company.getWebsite());
        companyRepository.save(company);
        return company;
    }


}
