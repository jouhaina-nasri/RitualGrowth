package com.yoterra.hippo.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yoterra.hippo.dto.AcDto;
import com.yoterra.hippo.dto.EmailUserStatusDto;
import com.yoterra.hippo.dto.UserDto;
import com.yoterra.hippo.dto.UserFullDto;

import com.yoterra.hippo.jpa.entities.users.User;

import com.yoterra.hippo.res.RPage;
import com.yoterra.hippo.res.Response;
import com.yoterra.hippo.search.requests.UserAggSortRequest;
import com.yoterra.hippo.search.requests.UserSearchRequest;
import com.yoterra.hippo.search.requests.iar.IARProviderImpl;
import com.yoterra.hippo.search.requests.sort.UserAggSort;
import com.yoterra.hippo.services.UsersService;
import com.yoterra.utils.Opt;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/users")
public class UsersController extends BaseController implements ILongEntityController {

	private static final Logger log = LoggerFactory.getLogger(UsersController.class);

	@Autowired
	private UsersService userService;


	@Operation(summary = "Block one user.", description = "Block the indicated user.")
	@RequestMapping(value="/block", params="id", method = RequestMethod.GET)
	public Response<Boolean> blockUser(
			@RequestParam("id") @Parameter(description = "User ID") Long id){
		log.info("Block user: {}", id);
		userService.blockUser(id);
		return Response.success("User blocked successfully", true);
	}

	@Operation(summary = "Unblock one user.", description = "Unblock the indicated user.")
	@RequestMapping(value="/unblock", params="id", method = RequestMethod.GET)
	public Response<Boolean> unblockUser(
			@RequestParam("id") @Parameter(description = "User ID") Long id){
		log.info("Block user: {}", id);
		userService.unblockUser(id);
		return Response.success("User unblocked successfully", true);
	}

	@Operation(summary = "Search for (get a page of) users", 
			description = "Search for (get a page of) users. Optionaly, apply filters (see the parameters). "
					+ "Returns records with limited information (full info records should be only used for single user info screens). "
					+ "It should be used on screens for displaying multiple users info.")
	@RequestMapping(method = RequestMethod.GET)
	public Response<RPage<UserDto>> searchUsers(@ModelAttribute UserSearchRequest req){

		Page<User> uPage = null ;
		log.info("Users search page");
		uPage = userService.searchUsers(req);
		RPage<UserDto> page = RPage.of(uPage, UserDto::new);
		return Response.success(null, page);
	}
	
	@Operation(summary = "Get a list of user names matching a specified query.",
			description = "Get a list of user IDs and names matching a specified query. "
					+ "Mostly used to display a list of user names while typing it in a text box.")
	@RequestMapping(value="/autocomplete", params="q", method = RequestMethod.GET)
	public Response<RPage<AcDto>> autocompleteUsers(@RequestParam("q") @Parameter(description = "Query text") String q){
		log.info("Users autocomplete page. Query: {}", q);
		
		UserSearchRequest req = new UserSearchRequest().initAutocompleteReq(q);
		Page<User> uPage = userService.searchUsers(req);
		RPage<AcDto> page = RPage.of(uPage, AcDto::new);
		return Response.success(null, page);
	}
	
	@Operation(summary = "Get full info of one user.", description = "Get user record with full information.")
	@RequestMapping(value="/get", params="id", method = RequestMethod.GET)
	public Response<UserFullDto> getUser(
			@RequestParam("id") @Parameter(description = "User ID") Long id,
			@ModelAttribute IARProviderImpl iarp){
		log.info("Get user: {}", id);
		User u = userService.getUser(id, iarp.getIar()); 
		return Response.success(null, UserFullDto.of(u));
	}
}
