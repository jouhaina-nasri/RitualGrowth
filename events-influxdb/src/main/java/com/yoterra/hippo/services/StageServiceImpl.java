package com.yoterra.hippo.services;

import com.yoterra.hippo.jpa.entities.*;
import com.yoterra.hippo.jpa.repositories.StageRepository;
import com.yoterra.hippo.response.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StageServiceImpl implements StageService {

    @Autowired
    private StageRepository stageRepository;

    @Autowired
    private SignupFlowService signupFlowService;

    @Autowired
    private CohortService cohortService;

    @Override
    public ResponseMessage save() {
        List<SignupFlow> signupFlows = new ArrayList<SignupFlow>();
        signupFlows=signupFlowService.findAll();
        for (SignupFlow signupFlow : signupFlows)
        {
            Cohort cohort = cohortService.findById(signupFlow.getCohortID());
            Stage stage = new Stage();
            stage.setType(StageType.valueOf("SignupFlow"));
            stage.setCohort(cohort);
            stage.setStageStats(signupFlow);
            //stage.setState();
            stageRepository.save(stage);
        }
        return new ResponseMessage("Saved successfully.");
    }

    @Override
    public ResponseMessage delete(Long id) {
        stageRepository.deleteById(id);
        return new ResponseMessage("Successfully deleted.");
    }

    @Override
    public ResponseMessage deleteAll() {
        stageRepository.deleteAll();
        return new ResponseMessage("Successfully deleted.");
    }

    @Override
    public List<Stage> findAll() {
        List<Stage> stages = new ArrayList<Stage>();
        stageRepository.findAll().iterator().forEachRemaining(stages::add);
        if (stages.isEmpty()){
            return null;
        }
        return stages;
    }
}
