package com.yoterra.hippo.controllers;

import com.yoterra.hippo.dto.CompanyDto;
import com.yoterra.hippo.dto.RequestAccessDto;
import com.yoterra.hippo.exceptions.DuplicateElementException;
import com.yoterra.hippo.exceptions.LimitExceededException;
import com.yoterra.hippo.forms.CompanyAddForm;
import com.yoterra.hippo.forms.CompanyCreateForm;
import com.yoterra.hippo.forms.CompanyEditForm;
import com.yoterra.hippo.jpa.entities.Company;
import com.yoterra.hippo.jpa.entities.RequestAccess;
import com.yoterra.hippo.req.Form;
import com.yoterra.hippo.req.PageParams;
import com.yoterra.hippo.res.RPage;
import com.yoterra.hippo.res.Response;
import com.yoterra.hippo.search.requests.CompanySearchRequest;
import com.yoterra.hippo.search.requests.iar.IARProviderImpl;
import com.yoterra.hippo.search.requests.sort.CompanySort;
import com.yoterra.hippo.services.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.enums.ParameterStyle;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Encoding;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/company")
public class CompanyController extends BaseController implements ILongEntityController {

    private final static Logger log = LoggerFactory.getLogger(CompanyController.class);

    @Autowired
    CompanyService companyService;

    @Operation(summary = "Get available companies",
            description = "Get a page of companies available for the current user. This endpoint returns  companies allowed for the user as well as those created by this user."
                    + "Returns records with limited information (full info records should be only used for single companies info screens). "
                    + "It should be used on screens for displaying multiple companies info."
    )

    @RequestMapping(method = RequestMethod.GET)
    public Response<RPage<CompanyDto>> getCompanies(PageParams pageParams){
        log.info("get page of availables compagnies");
        Page<Company> cPage = companyService.getAllowedCompanies(pageParams.getPageable());
        RPage<CompanyDto> page = RPage.of(cPage, CompanyDto::new);
        return Response.success(null, page);
    }

    @Operation(summary = "Get owned companies",
            description = "Get a page of companies created by the current user."
                    + "Returns records with limited information (full info records should be only used for single companies info screens). "
                    + "It should be used on screens for displaying multiple companies info."
    )

    @RequestMapping(value="/owned",method = RequestMethod.GET)
    public Response<RPage<CompanyDto>> getOwnedCompanies(PageParams pageParams){
        log.info("Companies search page");
        Page<Company> cPage = companyService.getCompanies(pageParams.getPageable());
        RPage<CompanyDto> page = RPage.of(cPage, CompanyDto::new);
        return Response.success(null, page);
    }

    @Operation(summary = "Get full info of one company.",
            description = "Get full info of one company (excluding company details). User can only get details of a company for which he has access.")
    @RequestMapping(value = "/get", params = "id", method = RequestMethod.GET)
    public Response<CompanyDto> getCompany(
            @RequestParam("id") @Parameter(description = "Company id") Long id){
        log.info("Get company full info {}", id);
        Company c = companyService.getCompany(id);
        return Response.success("Collection found", CompanyDto.of(c));
    }



    @Operation(
            summary = "Add a new company.",
            description = "After creating the first company. User can add another company."
    )
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Response<CompanyDto> createCompany(@ModelAttribute @Validated(value = Form.Create.class) CompanyAddForm form, BindingResult errors)
            throws DuplicateElementException, LimitExceededException {
        log.info("Add a new company: {}", form);

        if(errors.hasErrors())
            Response.throwError(getOneErrorMessage(errors));

        Company c = companyService.addCompany(form);
        return Response.success("Company successfully added", CompanyDto.of(c));
    }

    @Operation(summary = "Delete an existing company.", description = "Delete an existing company.")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public Response<?> deleteCollection(@RequestParam("id") @Parameter(description = "Company id") long companyId){
        log.info("Delete company: {}", companyId);

        companyService.deleteCompany(companyId);

        return Response.success("Company successfully deleted");
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public Response<CompanyDto> editCompany(@ModelAttribute @Validated(value = Form.Edit.class) CompanyEditForm form, BindingResult errors)
            throws DuplicateElementException, LimitExceededException {
        log.info("Edit company: {}", form);

        if(errors.hasErrors()) {
            Response.throwError(getOneErrorMessage(errors));
        }

        Company c = companyService.editCompany(form);
        return Response.success("Collection successfully updated", CompanyDto.of(c));
    }




}
