package com.yoterra.hippo.controllers;


import com.yoterra.hippo.measurements.entities.DeviceMeasurement;
import com.yoterra.hippo.req.PageParams;
import com.yoterra.hippo.res.RPage;
import com.yoterra.hippo.res.Response;
import com.yoterra.hippo.services.EventScreenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class DeviceTrack {

    @Autowired
    EventScreenService screenService;

    @Operation(summary = "Get list of devices", description = "get all the list of devices")
    @GetMapping("/devices")
    public Response getDevices(@RequestParam(required = true, name = "userid") @Parameter(description = "User's id") String userId, PageParams pageParams){
        List<DeviceMeasurement> results = screenService.findAllDeviceEntity(userId);
        RPage<DeviceMeasurement> page = RPage.of(results,pageParams.getPageable());
        return Response.success(null, page);

    }

}
