package com.yoterra.hippo.controllers;


import com.yoterra.hippo.exception.InvalidEventArguments;
import com.yoterra.hippo.measurements.entities.ScreenMeasurement;
import com.yoterra.hippo.measurements.load.EventScreenEntity;
import com.yoterra.hippo.measurements.requests.form.EventRequest;
import com.yoterra.hippo.res.RPage;
import com.yoterra.hippo.res.Response;
import com.yoterra.hippo.services.EventScreenService;
import com.yoterra.hippo.services.RedisService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class EventScreenTrack {

    @Autowired
    EventScreenService screensService;

    @Autowired
    RedisService redisService;

    @Operation(summary = "Tracking screen's event", description = "Tracking screen's events from the given events (JSON data)")
    @PostMapping("/screens")
    public Response trackSceenEvent(@Validated @RequestBody EventScreenEntity screenEvent) throws InvalidEventArguments {
       screensService.saveScreenEvent(screenEvent);
        try {
            redisService.saveMap(screenEvent);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return Response.success("Event screen saved successfully");
    }


    @Operation(summary = "Get screens events", description = "Get the list of screens events.  Optionaly, apply filters (see the parameters).")
    @GetMapping("/screens")
    public Response getSreensEvents(EventRequest request){
        List<ScreenMeasurement> results = screensService.findAllScreensEvents(request);
        RPage<ScreenMeasurement> page = RPage.of(results,request.getPageable());
        return Response.success(null, page);

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
