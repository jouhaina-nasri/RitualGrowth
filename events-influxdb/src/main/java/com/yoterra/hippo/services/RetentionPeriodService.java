package com.yoterra.hippo.services;

import com.yoterra.hippo.measurements.entities.EventScreenMeasurement;

import java.util.List;

public interface RetentionPeriodService {

    public Long timePassed(Long date1, Long date2);

    public Long activeSession(List<EventScreenMeasurement> events, String appVersion);

    public Long disabledSession(List<EventScreenMeasurement> events, String appVersion);

    public Long peopleEntered(List<EventScreenMeasurement> events, String appVersion);

    public Long peopleExited(List<EventScreenMeasurement> events, String appVersion);
}
