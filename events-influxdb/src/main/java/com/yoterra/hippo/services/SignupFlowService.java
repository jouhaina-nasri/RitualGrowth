package com.yoterra.hippo.services;

import com.yoterra.hippo.jpa.entities.SignupFlow;
import com.yoterra.hippo.response.ResponseMessage;

import java.util.List;

public interface SignupFlowService {

    public List<SignupFlow> findAll();

    public ResponseMessage save();

    public ResponseMessage delete(Long id);

    public ResponseMessage deleteAll();
}
