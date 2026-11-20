package com.yoterra.hippo.services;

import java.util.List;
import java.util.function.Supplier;

import com.yoterra.commons.FIXME;
import com.yoterra.hippo.jpa.entities.*;
import com.yoterra.hippo.jpa.repositories.CohortRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yoterra.hippo.exceptions.MissingEntityException;

import com.yoterra.hippo.jpa.entities.iasc.usrctx.UserCtxAware;

import com.yoterra.hippo.jpa.entities.users.User;
import com.yoterra.hippo.jpa.repositories.PrivilegeRepository;
import com.yoterra.hippo.jpa.repositories.UserRepository;

import com.yoterra.hippo.search.qbuilders.UserQueryBuilder;
import com.yoterra.hippo.search.requests.UserSearchRequest;
import com.yoterra.hippo.search.requests.iar.InfoAssociationRequest;
import com.yoterra.hippo.security.UserPrincipal;
import com.yoterra.hippo.services.auth.AuthorizationService;

@Service
public class UsersServiceImpl implements UserDetailsService ,UserService{

	private Supplier<MissingEntityException> MISSING_USER_EXC_SUPPLIER = 
			()->new MissingEntityException("The user account does not exist or it has been deleted or deactivated.");

	private final static Supplier<MissingEntityException> MISSING_BLOCK_EXC_SUPPLIER =
			()->new MissingEntityException("The user is not blocked");

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AuthorizationService auth;
	@Autowired
	private UserQueryBuilder userQueryBuilder;
	@Autowired
	private PrivilegeRepository privilegeRepository;

	@Autowired
	private CohortRepository cohortRepository;

	public boolean blockUser(long id){
		return FIXME.err("This feature has been temporarily disabled. It will completed latter.");

	}
	public boolean unblockUser(long id){
		return FIXME.err("This feature has been temporarily disabled. It will completed latter.");
	}


	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String emailOrUsername) {
		User user = userRepository.findByEmailAndDisabled(emailOrUsername,false);
		if (user == null) {
			user = userRepository.findByEmail(emailOrUsername);
			if(user == null) {
				throw new UsernameNotFoundException(emailOrUsername);
			}
		}
		return new UserPrincipal(user);
	}


	@Transactional(readOnly = true)
	public Page<User> searchUsers(UserSearchRequest req){
		Specification<User> spec = userQueryBuilder.build(req);
		Page<User> page = userRepository.findAll(spec, req.getPageable());
		//resolveUserContextsByIAR(page.getContent(), req.getIar());
		
		return page;
	}
	
	@Transactional(readOnly = true)
	public User getUser(long id, InfoAssociationRequest iar){
		User user = userRepository.findById(id).orElseThrow(MISSING_USER_EXC_SUPPLIER);
		return user;
	}

	public AuthorizationService getAuth() {
		return auth;
	}


	@Deprecated
	@Transactional
	public void setPrivilege(long userId, String priv) {
		User u = userRepository.findById(userId).orElseThrow(MISSING_USER_EXC_SUPPLIER);
		
		Privilege p = privilegeRepository.findOneByName(priv);
		if(p == null) {
			p = new Privilege();
			p.setName(priv);
			privilegeRepository.save(p);
		}
		u.getPrivileges().add(p);
		userRepository.save(u);
	}
	
	@Deprecated
	@Transactional
	public void removePrivilege(long userId, String priv) {
		User u = userRepository.findById(userId).orElseThrow(MISSING_USER_EXC_SUPPLIER);
		u.getPrivileges().removeIf(p->p.getName().equals(priv));
		userRepository.save(u);
	}
	public Class<? extends UserCtxAware> getUCAwareClass() {
		return User.class;
	}

	@Override
	public List<User> findAll()
	{
		return userRepository.findAll();
	}

	@Override
	public User findById(Long id) {
		if (id == null) {
			return null;
		}
		User user = userRepository.findById(id).orElse(null);

		return user;
	}

}
