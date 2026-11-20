package com.yoterra.hippo.services;

import java.util.Calendar;
import java.util.function.Supplier;

import javax.persistence.EntityNotFoundException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yoterra.commons.FIXME;
import com.yoterra.hippo.exceptions.DuplicateElementException;
import com.yoterra.hippo.exceptions.MissingEntityException;
import com.yoterra.hippo.exceptions.UnexpectedAccountStatusException;
import com.yoterra.hippo.exceptions.verification.VerificationException;
import com.yoterra.hippo.forms.UserCreateForm;
import com.yoterra.hippo.forms.UserForm;

import com.yoterra.hippo.jpa.entities.users.ClientApp;
import com.yoterra.hippo.jpa.entities.users.User;

import com.yoterra.hippo.jpa.repositories.ClientAppRepository;

import com.yoterra.hippo.jpa.repositories.UserRepository;

import com.yoterra.hippo.search.requests.iar.InfoAssociationRequest;
import com.yoterra.hippo.security.ActiveUser;
import com.yoterra.hippo.security.model.AuthToken;

@Service
public class ActiveUserService {

	private static final Logger log = LoggerFactory.getLogger(ActiveUserService.class);
	
	private Supplier<EntityNotFoundException> MISSING_USER_EXC_SUPPLIER = 
			()->new EntityNotFoundException("The user account does not exist or it has been deleted or deactivated.");
			
	@Autowired
	private UserRepository userRepository;
	

	
	@Autowired
	private ClientAppRepository clientAppRepository;
	
	@SuppressWarnings("unused")
	@Autowired
	private EmailSenderService emailSender;
	
	@SuppressWarnings("unused")
	@Autowired
	private ValidationTokenGenerator validationTokenGenerator;
	
	@Autowired
	private UsersServiceImpl userService;
	
	@Value("#{${com.hippo.user.account.reactivation.period.limit-hours:24} * 60 * 60 * 1000}")
	private long reactivationPeriodLimitInMilis;


	@Transactional(readOnly = true)
	public boolean usernameCheck(String username) {
		long c = userRepository.includeDeactivated_countByUsername(username);
		return c > 0;
	}


	@Transactional(readOnly = true)
	public boolean emailCheck(String email) {
		long c = userRepository.includeDeactivated_countByEmail(email);
		return c > 0;
	}
	
	public User getActiveUser(InfoAssociationRequest iar) {
		return userService.getUser(ActiveUser.getId(), iar);
	}

	@Transactional
	public User addNewUser(UserCreateForm userCreateForm, AuthToken token) throws DuplicateElementException{

		if(userRepository.includeDeactivated_countByApUid(token.getApUid()) > 0) 
			throw new DuplicateElementException("The user already exists.");
		if(userRepository.includeDeactivated_countByEmail(token.getEmail()) > 0) 
			throw new DuplicateElementException("A user with the specified e-mail address already exists.");
		
		User user = new User();
		
//		user.setAuthMethod(UserAuthMethod.extract(decToken));
		user.setEmail(token.getEmail());
		user.setApUid(token.getApUid());
		
		user.setCreationTime(Calendar.getInstance());

		user.setFullName(userCreateForm.getFullName());
		user.setDisabled(false);
		
		userRepository.save(user);

		
		return user;
	} 
	
	@Transactional
	public void updateUserInfo(UserForm userForm) {
		User cu = ActiveUser.get();
		User db = userRepository.findById(cu.getId()).orElseThrow(MISSING_USER_EXC_SUPPLIER);
		
		db.setFullName(userForm.getFullName());
		//db.setPhoneNumber(userForm.getPhoneNumber());

//		Not necessary ?
		cu.setFullName(userForm.getFullName());
		//cu.setPhoneNumber(userForm.getPhoneNumber());
		
		userRepository.save(db);
	}

	
	@Transactional
	public void addClientAppPushNotificationToken(User user, String token){
		ClientApp ca = clientAppRepository.findOneByUserAndMessagingToken(user, token);
		if(ca == null) {
			ca = new ClientApp();
			ca.setUser(user);
			ca.setMessagingToken(token);
			clientAppRepository.saveAndFlush(ca);
//			topicManager.registerTSRefreshTask(user);
		}
	}	
	
	@Transactional
	public void addClientAppPushNotificationToken(String token, boolean userRequired){
		User user = ActiveUser.get(userRequired);
		if(user == null)
			return;				// if not logged in just skip
		
		addClientAppPushNotificationToken(user, token);
	}	


	
	

	
	@Transactional
	public void deleteAccount(){
		User user = ActiveUser.get();
//		FIXME.warn("Test");
//		topicManager.unsubscribeFromAllTopics(user);		// don't register a batch job for this, cause the user will be deleted right after 
		userRepository.delete(user);
		FIXME.warn("Unsubscribe from topics");
	} 

	@Transactional
	public boolean deactivateAccount(){
		return FIXME.err("This feature has been temporarily disabled.");
//		User user = userRepository.findById(ActiveUser.getId()).orElseThrow(MISSING_USER_EXC_SUPPLIER);
//		String rToken = validationTokenGenerator.generateToken(32);		// generating reactivation token
//		user.setReactivationToken(rToken);
//		userRepository.save(user);
//		
////		FIXME.warn("Test");
////		topicManager.unsubscribeFromAllTopics(user);		// don't register a batch job for this, cause the user will be deactivated right after 
//		boolean b = deactivationProcessor.dectivateUser(user.getId());
//		return b;
	} 
	
	@Transactional(readOnly = true)
	public void initiateAccountReactivation(String email){
		FIXME.err("This feature has been temorarily disabled.");

//		Pair<Long, String> p = getReactivationStatus(email);
//		
//		String wtoken = validationTokenGenerator.wrapWithTimestamp(p.getRight());
//		emailSender.sendAccountReactivationEmail(email, wtoken);
	}


	
	@Transactional
	public boolean reactivateAccount(String email, String wtoken) throws VerificationException{
		
		return FIXME.err("This feature has been temorarily disabled.");

//		Pair<Long, String> p = getReactivationStatus(email);
//		
//		String unwrapped = validationTokenGenerator.unwrapWithTimestamplAndCheck(wtoken, reactivationPeriodLimitInMilis);
//		
//		if(!Objects.equals(unwrapped, p.getRight()))
//			throw new InvalidTokenException("The validation token is invalid");
//		
//		boolean b = deactivationProcessor.activateUser(email);
////		if(b) {
////			User actUser = userRepository.findByEmail(email);
////			FIXME.warn("Test");
////			topicManager.registerTSRefreshTask(actUser);
////		}
//		return b;
	}
	
	@SuppressWarnings("unused")
	private Pair<Long, String> getReactivationStatus(String email) {
		Pair<Long, String> p = userRepository.includeDeactivated_idAndReactivationTokenPairByEmail(email);
		if(p == null)
			throw new MissingEntityException("A user account with this e-mail does not exist");
		if(p.getLeft() > 0 || StringUtils.isBlank(p.getRight()))
			throw new UnexpectedAccountStatusException("The account is not deactivated");
		return p;
	}

}
