package com.yoterra.hippo.controllers;

import org.springframework.validation.BindingResult;

public interface IBaseController {
	String getOneErrorMessage(BindingResult errors);
}    
