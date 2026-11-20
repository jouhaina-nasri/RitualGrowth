package com.yoterra.hippo.forms;

import com.yoterra.hippo.jpa.entities.Company;
import com.yoterra.hippo.req.Form;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.Getter;
import lombok.Setter;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@ParameterObject

public class CompanyEditForm{

    @Parameter(required = true, description = "Company Id")
    private Long companyId;

    @Parameter(required = true, description = "Company name. Min-max length: ("+ Company.NAME_MIN_LEN+"-"+Company.NAME_MAX_LEN+")")
    @NotBlank(groups = {Form.Create.class, Form.Edit.class}, message = "Company name is mandatory")
    @Size(groups = {Form.Create.class, Form.Edit.class}, min = Company.NAME_MIN_LEN, message = "Company name minimum length is " + Company.NAME_MIN_LEN)
    @Size(groups = {Form.Create.class, Form.Edit.class}, max = Company.NAME_MAX_LEN, message = "Company name maximum length is " + Company.NAME_MAX_LEN)
    private String name;

    @Parameter(required = true, description = "Company website. Min-max length: ("+ Company.WEBSITE_MIN_LEN+"-"+Company.WEBSITE_MAX_LEN+")")
    @NotBlank(groups = {Form.Create.class, Form.Edit.class}, message = "Company website is mandatory")
    @Size(groups = {Form.Create.class, Form.Edit.class}, min = Company.WEBSITE_MIN_LEN, message = "Company website minimum length is " + Company.WEBSITE_MIN_LEN)
    @Size(groups = {Form.Create.class, Form.Edit.class}, max = Company.WEBSITE_MAX_LEN, message = "Company website maximum length is " + Company.WEBSITE_MAX_LEN)
    private String website;
}
