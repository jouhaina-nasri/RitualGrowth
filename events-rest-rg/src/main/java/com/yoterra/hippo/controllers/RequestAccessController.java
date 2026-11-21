package com.yoterra.hippo.controllers;


import com.yoterra.hippo.dto.CompanyDto;
import com.yoterra.hippo.dto.RequestAccessDto;
import com.yoterra.hippo.jpa.entities.Company;
import com.yoterra.hippo.jpa.entities.RequestAccess;
import com.yoterra.hippo.req.PageParams;
import com.yoterra.hippo.res.RPage;
import com.yoterra.hippo.res.Response;
import com.yoterra.hippo.services.RequestAccessService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/request/access")
public class RequestAccessController extends BaseController implements ILongEntityController {

    private final static Logger log = LoggerFactory.getLogger(RequestAccessController.class);

    @Autowired
    RequestAccessService requestAccessService;

    @Operation(summary = "Get a page of request access",
            description = "Get a page of request access send to the active user. The company's owner can give access to those request access to the company details."
                    + "Returns records with limited information's (full info records should be only used for single companies info screens). "
                    + "It should be used on screens for displaying multiple request access info."
    )

    @RequestMapping(method = RequestMethod.GET)
    public Response<RPage<RequestAccessDto>> getRequestAccess(PageParams pageParams){
        log.info("get request access page");
        Page<RequestAccess> cPage = requestAccessService.getRequestAccess(pageParams.getPageable());
        RPage<RequestAccessDto> page = RPage.of(cPage, RequestAccessDto::new);
        return Response.success(null, page);
    }

    @Operation(summary = "Grant access",
            description = "The company's owner can give access to each user that request access to the company details."
                    + "It should be used on screens for accepting a request access."
    )

    @RequestMapping(value = "/grant", params = "id", method = RequestMethod.GET)
    public Response<RequestAccessDto> grantRequestAccess( @RequestParam("id") @Parameter(description = "RequestAccess id") Long id){
        log.info("Grant request access {}", id);
        RequestAccess requestAccess = requestAccessService.grantRequestAccess(id);
        return Response.success("The access is granted to the user.", RequestAccessDto.of(requestAccess));
    }

    @Operation(summary = "Revoke access",
            description = "The company's owner can revoke access from each user that have access to the company details."
                    + "It should be used on screens for denied request access from users."
    )
    @RequestMapping(value = "/revoke", params = "id", method = RequestMethod.GET)
    public Response<RequestAccessDto> revokeRequestAccess( @RequestParam("id") @Parameter(description = "RequestAccess id") Long id){
        log.info("revoke request access {}", id);
        RequestAccess requestAccess = requestAccessService.revokeRequestAccess(id);
        return Response.success("The access is not revoked to the user.", RequestAccessDto.of(requestAccess));
    }
}
