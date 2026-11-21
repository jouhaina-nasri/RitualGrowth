package com.yoterra.hippo.forms;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import org.springdoc.api.annotations.ParameterObject;

import com.yoterra.hippo.jpa.entities.users.User;
import com.yoterra.hippo.req.Form;

import io.swagger.v3.oas.annotations.Parameter;

@Getter
@Setter
@ParameterObject
public class UserForm implements Form {

	@Parameter(required = true, description = "Full name field. Min-max length: (" + User.FULL_NAME_MIN_LEN + "-" + User.FULL_NAME_MAX_LEN + ")")
	@NotBlank(groups = {Create.class, Edit.class}, message = "Full name is mandatory")
	@Size(groups = {Create.class, Edit.class}, min = User.FULL_NAME_MIN_LEN, message = "Full name minimum length is " + User.FULL_NAME_MIN_LEN)
	@Size(groups = {Create.class, Edit.class}, max = User.FULL_NAME_MAX_LEN, message = "Full name maximum length is " + User.FULL_NAME_MAX_LEN)
	@Pattern(groups = Create.class, regexp = "[A-Za-z]{1,}([\\s][A-Za-z]{1,}){1,}",message = "Full name is not valid. Only characters A-Z,a-z and spaces are authorized")
	private String fullName;
	
	@Parameter(required = true, description = "User email.")
	@Email(message = "The user email does not have expected format.")
	private String email;


	@Override
	public String toString() {
		return "UserForm [fullName=" + fullName + ", email=" + email + "]";
	}


}