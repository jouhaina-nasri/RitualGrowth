package com.yoterra.hippo.measurements.repository;


import com.yoterra.hippo.measurements.entities.DeviceMeasurement;
import com.yoterra.hippo.measurements.entities.EventScreenMeasurement;
import com.yoterra.hippo.measurements.entities.ScreenMeasurement;
import com.yoterra.hippo.measurements.requests.form.EventRequest;
import com.yoterra.hippo.measurements.requests.influx.InfluxdbRequest;
import org.influxdb.InfluxDB;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.impl.InfluxDBResultMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class EventScreenRepositoryImpl implements EventScreenRepository<EventScreenMeasurement> {
    @Autowired
    public InfluxDB influxDB;

    @Override
    public void save(EventScreenMeasurement screen,Long project_id,Long cohort_id,Long user_id) {
        Point point = Point
                .measurement("events_screens")
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .tag("eventId", screen.getEventId())
                .tag("testId", screen.getTestId())
                .tag("apiVersion", screen.getApiVersion())
                .tag("appVersion", screen.getAppVersion())
                // device info
                .addField("appInstalledTime", screen.getAppInstalledTime())
                .tag("deviceModel", screen.getDeviceModel())
                .tag("manufacturer", screen.getManufacturer())
                .tag("systemName", screen.getSystemName())
                .tag("systemVersion", screen.getSystemVersion())
                //
                .tag("eventType", screen.getEventType())
                //
                .tag("projectName", screen.getProjectName())
                .tag("projectId", screen.getProjectId())
                .tag("previousScreenName", screen.getPreviousScreenName())
                .tag("screenName", screen.getScreenName())
                .tag("screenSubtype", screen.getScreenType())
                .tag("screenType", screen.getScreenType())

                //
                .tag("navParamId", screen.getNavParamId())
                .tag("navParamType", screen.getNavParamType())

                //
                .addField("screenStartTime", screen.getScreenStartTime())
                .addField("screenEndTime", screen.getScreenEndTime())
                .addField("timestamp", screen.getTimestamp())
                .addField("totalScreenTime", screen.getTotalScreenTime())
                .tag("sessionId", screen.getSessionId())
                .tag("userId", screen.getUserId())
                .addField("userIdHash",String.valueOf(user_id))
                .addField("projectIdHash",String.valueOf(project_id))
                .addField("cohortIdHash",String.valueOf(cohort_id))
                .build();
        influxDB.write(point);
    }

    @Override
    public List<ScreenMeasurement> findAll(EventRequest request) {
        System.out.println(request);
        String command = InfluxdbRequest.toInfluxdbQuery(request);
        Query query = new Query(command);
        QueryResult queryResult = influxDB.query(query);
        InfluxDBResultMapper resultMapper = new InfluxDBResultMapper(); // thread-safe - can be reused
        List<ScreenMeasurement> influxResults = resultMapper.toPOJO(queryResult, ScreenMeasurement.class);
      /*  try {
            influxResults.forEach(entity->{
                ScreenEntity screen = new ScreenEntity();
                BeanUtils.copyProperties(entity,screen);
                results.add(screen);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        return influxResults;
    }

    @Override
    public List<DeviceMeasurement> findAllDevices(String userId) {
        String command = InfluxdbRequest.toInfluxdbQuery(userId);
        Query query = new Query(command);
        QueryResult queryResult = influxDB.query(query);
        InfluxDBResultMapper resultMapper = new InfluxDBResultMapper(); // thread-safe - can be reused
        List<DeviceMeasurement> influxResults = resultMapper.toPOJO(queryResult, DeviceMeasurement.class);
       /* try {
            influxResults.forEach(entity->{
                DeviceEntity device = new DeviceEntity();
                BeanUtils.copyProperties(entity,device);
                results.add(device);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        return influxResults;
    }

    @Override
    public void deleteAllEvents() {
        String command = "drop measurement events_screens";
        Query query = new Query(command);
        QueryResult queryResult = influxDB.query(query);
    }

}