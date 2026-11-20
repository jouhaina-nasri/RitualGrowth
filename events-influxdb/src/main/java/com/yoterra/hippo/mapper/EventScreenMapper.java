package com.yoterra.hippo.mapper;


import com.yoterra.hippo.measurements.entities.EventScreenMeasurement;
import com.yoterra.hippo.measurements.load.EventScreenEntity;
import com.yoterra.hippo.measurements.load.NavigationParams;
import com.yoterra.hippo.measurements.load.ScreenInformation;
import org.springframework.stereotype.Component;

@Component
public class EventScreenMapper {


    public EventScreenMeasurement toMeasurement(EventScreenEntity entity){
        EventScreenMeasurement measurement = new EventScreenMeasurement();
        //
        measurement.setEventId(entity.getEventId());
        measurement.setTestId(entity.getTestId());
        measurement.setApiVersion(entity.getApiVersion());
        measurement.setAppVersion(entity.getAppVersion());
        measurement.setProjectName(entity.getProjectName());
        measurement.setProjectId(entity.getProjectId());
        measurement.setEventType(entity.getEventType());
        // device information
        measurement.setAppInstalledTime(entity.getDeviceInformation().getAppInstalledTime());
        measurement.setDeviceModel(entity.getDeviceInformation().getDeviceModel());
        measurement.setManufacturer(entity.getDeviceInformation().getManufacturer());
        measurement.setSystemName(entity.getDeviceInformation().getSystemName());
        measurement.setSystemVersion(entity.getDeviceInformation().getSystemVersion());

        // screen event
        measurement.setPreviousScreenName(entity.getPreviousScreenName());

        // screen info
        if(entity.getScreenInformation()instanceof ScreenInformation) {
            measurement.setScreenName(entity.getScreenInformation().getName());
            measurement.setScreenSubtype(entity.getScreenInformation().getSubtype());
            measurement.setScreenType(entity.getScreenInformation().getType());
        } else {
            measurement.setScreenName("null");
            measurement.setScreenSubtype("null");
            measurement.setScreenType("null");
        }

        //set navigation param
        measurement.setNavParamId(entity.getNavigationParams().getId());
        measurement.setNavParamType(entity.getNavigationParams().getType());

        //
        measurement.setScreenStartTime(entity.getScreenStartTime());
        measurement.setScreenEndTime(entity.getScreenEndTime());
        measurement.setTimestamp(entity.getTimestamp());
        measurement.setTotalScreenTime(entity.getTotalScreenTime());
        measurement.setSessionId(entity.getSessionId());
        measurement.setUserId(entity.getUserId());
        return measurement;

    }
}
