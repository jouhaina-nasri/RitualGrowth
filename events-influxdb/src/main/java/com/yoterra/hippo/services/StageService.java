package com.yoterra.hippo.services;


import com.yoterra.hippo.jpa.entities.Stage;
import com.yoterra.hippo.response.ResponseMessage;

import java.util.List;

public interface StageService {

    public ResponseMessage save();

    public ResponseMessage delete(Long id);

    public ResponseMessage deleteAll();

    public List<Stage> findAll();
}
