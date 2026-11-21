package com.yoterra.hippo.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/signup")
public class SignupFlowController {
/*
    @Autowired
    private SignupFlowService signupFlowService;

    @Operation(summary = "Get signup flow list",
            description = "Get signup flow list")

    @RequestMapping(method = RequestMethod.GET)
    public List<SignupFlow> findAll()
    {
        return signupFlowService.findAll();
    }

    @Operation(summary = "Save signup flow",
            description = "Save signup flow")

    @RequestMapping(method = RequestMethod.POST)
    public ResponseMessage save() {
        return signupFlowService.save();
    }

    @Operation(summary = "Delete an existing signup flow record.", description = "Delete an existing signup flow record.")
    @RequestMapping(value = "/deleteOne", method = RequestMethod.DELETE)
    public ResponseMessage delete(@RequestParam("id") @Parameter(description = "id") long id) {

        return signupFlowService.delete(id);
    }


    @Operation(summary = "Delete signup flow list.", description = "Delete signup flow list.")
    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseMessage delete() {

        return signupFlowService.deleteAll();
    }

 */
}
