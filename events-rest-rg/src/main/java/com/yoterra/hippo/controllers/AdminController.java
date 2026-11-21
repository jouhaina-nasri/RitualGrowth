package com.yoterra.hippo.controllers;


import com.yoterra.hippo.dto.CompanyDto;
import com.yoterra.hippo.jpa.entities.Company;
import com.yoterra.hippo.res.RPage;
import com.yoterra.hippo.res.Response;
import com.yoterra.hippo.search.requests.CompanySearchRequest;
import com.yoterra.hippo.search.requests.sort.CompanySort;
import com.yoterra.hippo.services.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController extends BaseController implements ILongEntityController  {

    private final static Logger log = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    CompanyService companyService;

    @Operation(summary = "Search for (get a page of) companies",
            description = "Search for (get a page of) companies. Optionally, apply filters (see the parameters). "
                    + "Returns records with limited information (full info records should be only used for single companies info screens). "
                    + "It should be used on screens for displaying multiple companies info.",
            parameters = @Parameter(
                    name = "sortBy",
                    description = "Field to sort by",
                    schema = @Schema(implementation = CompanySort.class),
                    required = false,
                    in = ParameterIn.QUERY
            )
    )

    @RequestMapping(value = "/companies",method = RequestMethod.GET)
    public Response<RPage<CompanyDto>> searchCompanies(@ModelAttribute CompanySearchRequest req){
        log.info("Companies search page");
        Page<Company> cPage = companyService.searchCompanies(req);
        RPage<CompanyDto> page = RPage.of(cPage, CompanyDto::new);
        return Response.success(null, page);
    }
}
