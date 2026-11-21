package com.yoterra.hippo.services;



import com.yoterra.hippo.exception.InvalidEventArguments;
import com.yoterra.hippo.measurements.entities.DeviceMeasurement;
import com.yoterra.hippo.measurements.entities.ScreenMeasurement;
import com.yoterra.hippo.measurements.load.EventScreenEntity;
import com.yoterra.hippo.measurements.requests.form.EventRequest;

import java.util.List;

public interface EventScreenService {
    public void saveScreenEvent(EventScreenEntity entity) throws InvalidEventArguments;
    public List<ScreenMeasurement> findAllScreensEvents(EventRequest request);

    public List<DeviceMeasurement> findAllDeviceEntity(String userId);

    public void deleteAllEvents();

}
