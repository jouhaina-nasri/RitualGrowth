package com.yoterra.hippo.measurements.repository;

import com.yoterra.hippo.measurements.entities.DeviceMeasurement;
import com.yoterra.hippo.measurements.entities.ScreenMeasurement;
import com.yoterra.hippo.measurements.requests.form.EventRequest;

import java.util.List;

public interface EventScreenRepository<T> {

    /**
     * Save the screen to the storage repository.
     *
     */
    void save(T screen,Long project_id,Long cohort_id,Long user_id);

    List<ScreenMeasurement> findAll(EventRequest request);

    List<DeviceMeasurement> findAllDevices(String userId);

    void deleteAllEvents();

}