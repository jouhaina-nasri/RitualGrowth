package com.yoterra.hippo.controllers;

import com.yoterra.hippo.res.Response;
import com.yoterra.hippo.services.CheckServer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class CheckController {

    @Autowired
    CheckServer checkServer;



    @Operation(summary = "Checking if server is online", description = "Check if server application is available. Check if server is running and the server accepts the connection")
    @GetMapping("/check/server")
    public Response serverIsAvailable(@RequestParam(required = true, name = "serverAddress") @Parameter(description = "The server address") String serverAddress,
                                      @RequestParam(required = true, name = "serverPort") @Parameter(description = "The TCP server port") int serverPort){
        Boolean response = checkServer.checkServer(serverAddress,serverPort);
        if(response)
            return Response.success("The server is available");
        else
            return Response.error("The server is not available");

    }
}
