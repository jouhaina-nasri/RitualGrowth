package com.yoterra.hippo.controllers;

import com.yoterra.hippo.dto.UserDto;
import com.yoterra.hippo.dto.UserFullDto;
import com.yoterra.hippo.exceptions.LimitExceededException;
import com.yoterra.hippo.forms.UserCreateForm;
import com.yoterra.hippo.forms.UserForm;
import com.yoterra.hippo.jpa.entities.Company;
import com.yoterra.hippo.jpa.entities.users.User;
import com.yoterra.hippo.req.Form;
import com.yoterra.hippo.security.ActiveUser;
import com.yoterra.hippo.security.model.AuthToken;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yoterra.hippo.exceptions.DuplicateElementException;
import com.yoterra.hippo.req.Form.Create;
import com.yoterra.hippo.res.Response;
import com.yoterra.hippo.services.ActiveUserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@RestController
@RequestMapping("/auth")
public class AuthenticationController extends BaseController {
	
	private static final Logger log = LoggerFactory.getLogger(AuthenticationController.class);

	@Autowired
	private ActiveUserService userAccountService;

	
	@RequestMapping(value = "/reqaccreactivation", params = "email", method = RequestMethod.POST)
	public Response<?> reactivateAccount(@RequestParam @Parameter(description = "E-mail of the user account") String email) {
		log.info("Reactivate user account: {}", email);
		userAccountService.initiateAccountReactivation(email);
		return Response.success("Reactivation e-mail sent successfully.");
	}
	
	
	@Operation(summary = "User details (live) verification",
			description = "Checks if the user details (username and email) that is being entered during the user creation process is already taken.")
	@RequestMapping(value = "/usercheck", method = RequestMethod.POST)
	public Response<HashMap> userCheck(@ModelAttribute @Validated(Create.class) UserForm userForm, BindingResult errors) {
		log.info("Checking user details: {}", userForm.toString());
		if(errors.hasErrors())
			Response.throwError(getOneErrorMessage(errors));
		boolean exists = userAccountService.emailCheck(userForm.getEmail());
		HashMap<String,Boolean> response = new HashMap<>();
		response.put("exists",exists);
		return Response.success(exists ? "The user exists." : "The user does not exist.", response);
	}




	@Operation(summary = "Add a new user - set basic info", description = "Add a new user. "
			+ "Specify the basic user information. The rest of the user information can be set/updated using other endpoints. "
			+ "\n<i>This is when the user actually gets created in the database. "
			+ "In the previous auth steps the app only communicates with Firebase and other exteranl services.</i>")
	@RequestMapping(value="/register", method = RequestMethod.POST)
	public Response<UserDto> register(
			@RequestParam(value="fcmToken", required = false) String fcmToken,
			@ModelAttribute @Validated(Create.class) UserCreateForm userCreateForm,
			BindingResult errors, 
			HttpServletRequest request) throws IllegalAccessException {
		
		log.info("Register a new user account: {}", userCreateForm);

		if(errors.hasErrors()) 
			Response.throwError(getOneErrorMessage(errors));
		
		AuthToken token = ActiveUser.getToken(true);
		
		try {
			User user = userAccountService.addNewUser(userCreateForm, token);
			if(StringUtils.isNotBlank(fcmToken)) {
				userAccountService.addClientAppPushNotificationToken(user, fcmToken);
			}
			return Response.success("User successfully added", UserFullDto.of(user));
		}catch (DuplicateElementException e) {
			Response.throwError(e.getMessage());
			return null;						// never happens
		}		
	}
}