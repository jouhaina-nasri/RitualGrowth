package com.yoterra.hippo.controllers;


import com.yoterra.hippo.res.Response;
import com.yoterra.hippo.services.EventScreenService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class InfluxDb {

    @Autowired
    EventScreenService screensService;

    @Operation(summary = "delete all screens events stored in the influxDB", description = "Initialize the influxdb by deleting all screens events in the database.")
    @DeleteMapping("/screens/initialize")
    public Response initEventsScreens() {
        screensService.deleteAllEvents();
        return Response.success("screens events are deleted successfully");
    }

}
