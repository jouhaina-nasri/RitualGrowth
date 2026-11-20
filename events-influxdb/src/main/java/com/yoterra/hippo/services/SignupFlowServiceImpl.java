package com.yoterra.hippo.services;

import com.yoterra.hippo.jpa.entities.Cohort;
import com.yoterra.hippo.jpa.entities.SignupFlow;
import com.yoterra.hippo.jpa.entities.users.User;
import com.yoterra.hippo.jpa.repositories.CohortRepository;
import com.yoterra.hippo.jpa.repositories.SignupFlowRepository;
import com.yoterra.hippo.measurements.repository.EventRepository;
import com.yoterra.hippo.measurements.entities.EventScreenMeasurement;
import com.yoterra.hippo.response.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SignupFlowServiceImpl implements SignupFlowService {

    public final static String ScreenOne = "PasswordScreen";
    public final static String ScreenTwo = "CategorySelection";
    public final static String ScreenThree = "BrandSelection";
    public final static String ScreenFour = "StoreSelection";
    @Autowired
    private SignupFlowRepository signupFlowRepository;

    @Autowired
    private CohortRepository cohortRepository;

    @Autowired
    private EventRepository eventRepository;

    @Override
    public List<SignupFlow> findAll() {
        Iterable<SignupFlow> signupFlowIterable = signupFlowRepository.findAll();
        List<SignupFlow> result = new ArrayList<SignupFlow>();
        for (SignupFlow str : signupFlowIterable) {
            result.add(str);
        }
        return result;
    }

    @Override
    public ResponseMessage save() {
        List<Cohort> cohorts = new ArrayList<Cohort>();
        for (Cohort cohort : cohorts) {
            SignupFlow signupFlow = this.calculated(cohort.getId());
            signupFlowRepository.save(signupFlow);
        }
        return new ResponseMessage("Congratulation");
    }

    @Override
    public ResponseMessage delete(Long id) {
        SignupFlow signupFlow = new SignupFlow();
        signupFlow = signupFlowRepository.findById(id).orElse(null);
        signupFlowRepository.delete(signupFlow);
        return new ResponseMessage("Congratulation");
    }

    @Override
    public ResponseMessage deleteAll() {
        signupFlowRepository.deleteAll();
        return new ResponseMessage("Congratulation");
    }

    public List<String> searchCompleted(String version, String screenName1, String screenName2, String screenName3, String screenName4) {
        List<String> list = new ArrayList<String>();
        List<EventScreenMeasurement> eventScreenMeasurements = eventRepository.findByVersion(version);
        for (int i = 0; i < eventScreenMeasurements.size(); i++) {
            if (!eventScreenMeasurements.get(i).getScreenName().equals(screenName1) ||
                    !eventScreenMeasurements.get(i).getScreenName().equals(screenName2) ||
                    !eventScreenMeasurements.get(i).getScreenName().equals(screenName3) ||
                    !eventScreenMeasurements.get(i).getScreenName().equals(screenName4))
                list.add(eventScreenMeasurements.get(i).getUserId());
        }
        return list;
    }

    public List<String> searchUserScreen(String version, String screenName) {
        List<String> list = new ArrayList<String>();
        List<EventScreenMeasurement> eventScreenMeasurements = eventRepository.findByVersion(version);
        for (int i = 0; i < eventScreenMeasurements.size(); i++) {
            if (eventScreenMeasurements.get(i).getScreenName().equals(screenName))
                list.add(eventScreenMeasurements.get(i).getUserId());
        }
        return list;
    }

    public SignupFlow calculated(Long cohort_id) {
        SignupFlow signupFlow = new SignupFlow();

        String version = "";

        Set<Long> set = new HashSet<>();

        List<String> list1 = this.searchUserScreen(version, ScreenOne);
        List<String> list2 = this.searchUserScreen(version, ScreenTwo);
        List<String> list3 = this.searchUserScreen(version, ScreenThree);
        List<String> list4 = this.searchUserScreen(version, ScreenFour);
        List<String> list5 = this.searchCompleted(version, ScreenOne, ScreenTwo, ScreenThree, ScreenFour);

        Long stoppedScreenOne = this.stoppedScreen(list1, list2);
        Long stoppedScreenTwo = this.stoppedScreen(list2, list3);
        Long stoppedScreenThree = this.stoppedScreen(list3, list4);
        Long stoppedScreenFour = this.stoppedScreen(list4, list5);

        Long entered = list5.size() - stoppedScreenFour;
        Long exit = stoppedScreenOne + stoppedScreenTwo + stoppedScreenThree + stoppedScreenFour;

        signupFlow.setStoppedScreenOne(stoppedScreenOne);
        signupFlow.setStoppedScreenTwo(stoppedScreenTwo);
        signupFlow.setStoppedScreenThree(stoppedScreenThree);
        signupFlow.setStoppedScreenFour(stoppedScreenFour);
        signupFlow.setPeopleEntered(entered);
        signupFlow.setPeopleExit(exit);
        signupFlow.setCohortID(cohort_id);

        /* Inactive */

        int size = this.userNumber(cohort_id);
        double completed = entered / size;
        double notCompleted = exit / size;

        signupFlow.setPercentCompleted(completed);
        signupFlow.setPercentNotCompleted(notCompleted);

        return signupFlow;

    }

    public Long stoppedScreen(List<String> list1, List<String> list2) {
        Long number = 0L;

        for (String temp : list1) {

            // Check if theis element is
            // present in list2 or not
            if (!list2.contains(temp)) {
                number++;
            }
        }
        return number;
    }

    public int userNumber(Long cohort_id) {
        Cohort cohort = cohortRepository.getById(cohort_id);
        Set<User> users = new HashSet<User>();
        users = cohort.getUsers();
        return users.size();
    }
}
