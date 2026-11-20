package com.yoterra.hippo.controllers;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yoterra.hippo.dto.UserDto;
import com.yoterra.hippo.dto.UserProfileDto;
import com.yoterra.hippo.forms.UserForm;
import com.yoterra.hippo.jpa.entities.users.User;
import com.yoterra.hippo.req.Form.Edit;
import com.yoterra.hippo.res.Response;
import com.yoterra.hippo.search.requests.iar.InfoAssociationRequestProvider;
import com.yoterra.hippo.services.ActiveUserService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/active")
public class ActiveUserController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(ActiveUserController.class);

	@Autowired
	private ActiveUserService activeUserService;


	
	
	@Operation(summary = "Get current user full profile information", description = "Get current user full profile information")
	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public Response<UserDto> userProfile() {
		log.info("Load user profile page");
		User user = activeUserService.getActiveUser(InfoAssociationRequestProvider.ALL);
		return Response.success(null, UserProfileDto.of(user));
	}
	
	@Operation(summary = "Update current user basic info.", 
			description = "Update current user basic info (first and last name and the phone nubmer). "
					+ "The rest of the account related information can be changed using other endpoints.")
	@RequestMapping(value="/profile/update", method = RequestMethod.POST)
	public Response<?> updateProfile(@ModelAttribute @Validated(Edit.class) UserForm userForm, 
			BindingResult errors, HttpServletRequest request) {
		
		if(errors.hasErrors()) 
			Response.throwError(getOneErrorMessage(errors));
		
		activeUserService.updateUserInfo(userForm);
		return Response.success("User info successfully updated");
	}
	
	@Operation(summary = "Delete the current user account.", description = "Delete the current user account. "
			+ "After this operation the user should be logged out from the app.")
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public Response<?> deleteAccount() {
		log.info("Deleting user account.");
		activeUserService.deleteAccount();
		return Response.success("Account successfully deleted");
	}
	
	
	@Operation(summary = "Deactivate the current user account.", description = "Deactivate the current user account.")
	@RequestMapping(value = "/deactivate", method = RequestMethod.POST)
	public Response<?> deactivateAccount() {
		log.info("Deactivating user account.");
		activeUserService.deactivateAccount();
		return Response.success("Account successfully deactivated");
	}
	
}
