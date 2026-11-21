package com.yoterra.hippo.services;

import com.yoterra.hippo.measurements.entities.EventScreenMeasurement;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class RetentionPeriodServiceImpl implements RetentionPeriodService {


    @Override
    public Long timePassed(Long date1, Long date2) {
        long difference_In_Time = date2 - date1;
        return difference_In_Time;
    }

    @Override
    public Long activeSession(List<EventScreenMeasurement> events, String appVersion) {
        Long active = 0L;
        Map<String, Long> map = listSession(events, appVersion);
        for (Map.Entry m : map.entrySet()) {
            if ((Long) m.getValue() > 1) {
                active++;
            }
        }
        return active;
    }

    @Override
    public Long disabledSession(List<EventScreenMeasurement> events, String appVersion) {
        Long disabled = 0L;
        Map<String, Long> map = listSession(events, appVersion);
        for (Map.Entry m : map.entrySet()) {
            if ((Long) m.getValue() <= 1) {
                disabled++;
            }
        }
        return disabled;
    }

    @Override
    public Long peopleEntered(List<EventScreenMeasurement> events, String appVersion) {
        Long entered = 0L;
        Map<Long, Long> map = this.listPeople(events, appVersion);
        for (Map.Entry m : map.entrySet()) {
            if ((Long) m.getValue() > 1) {
                entered++;
            }
        }
        return entered;
    }

    @Override
    public Long peopleExited(List<EventScreenMeasurement> events, String appVersion) {
        Long exited = 0L;
        Map<Long, Long> map = this.listPeople(events, appVersion);
        for (Map.Entry m : map.entrySet()) {
            if ((Long) m.getValue() <= 1) {
                exited++;
            }
        }
        return exited;
    }


    public Map<String, Long> listSession(List<EventScreenMeasurement> events, String appVersion) {
        List<String> sessions = new ArrayList<String>();
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).getAppVersion().equals(appVersion)) {
                sessions.add(events.get(i).getSessionId());
            }
        }
        Map<String, Long> counts = sessions.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        return counts;
    }

    public Map<Long,Long> listPeople(List<EventScreenMeasurement> events, String appVersion) {
        List<Long> users = new ArrayList<Long>();
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).getAppVersion().equals(appVersion)) {
              //  users.add(events.get(i).getUserId());
            }
        }
        Map<Long, Long> counts = users.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        return counts;
    }
}
