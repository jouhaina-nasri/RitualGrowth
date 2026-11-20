package com.yoterra.hippo.exception;


import com.yoterra.hippo.res.Response;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InvalidEventArguments.class)
    public Response resourcenotfound(InvalidEventArguments ex, WebRequest request) {
        ex.printStackTrace();
        return Response.error(ex.getMessage());
    }

    @ExceptionHandler(CheckException.class)
    public Response checkExceptionHandler(CheckException ex, WebRequest request) {
        ex.printStackTrace();
        return Response.error(ex.getMessage());
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public Response checkExceptionHandler(IllegalArgumentException ex, WebRequest request) {
        ex.printStackTrace();
        return Response.error(ex.getMessage());
    }

  @ExceptionHandler(Exception.class)
    public Response gobalexception(Exception ex, WebRequest request) {
        ex.printStackTrace();
        return Response.error("Internal Server Error");
    }



}
