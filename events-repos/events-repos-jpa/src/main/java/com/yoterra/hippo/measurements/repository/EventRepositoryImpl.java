package com.yoterra.hippo.measurements.repository;

import com.yoterra.hippo.measurements.entities.EventScreenMeasurement;
import org.influxdb.InfluxDB;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.impl.InfluxDBResultMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class EventRepositoryImpl implements EventRepository<EventScreenMeasurement> {
    @Autowired
    public InfluxDB influxDB;

    @Override
    public List<EventScreenMeasurement> findAll() {
        List<EventScreenMeasurement> result = new ArrayList<EventScreenMeasurement>();
        Query query = new Query("SELECT * FROM events_screens");
        QueryResult queryResult = influxDB.query(query);
        InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();
        result = resultMapper.toPOJO(queryResult, EventScreenMeasurement.class);

        return result;

    }

    @Override
    public List<EventScreenMeasurement> findByVersion(String version) {
        List<EventScreenMeasurement> result = new ArrayList<EventScreenMeasurement>();
        Query query = new Query("SELECT * FROM events_screens where appVersion ='"+version+"'");
        QueryResult queryResult = influxDB.query(query);
        InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();
        result = resultMapper.toPOJO(queryResult, EventScreenMeasurement.class);

        return result;
    }

    @Override
    public void save(EventScreenMeasurement screen) {
        Point point = Point
                .measurement("events_screens")
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .tag("eventId", screen.getEventId())
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
                .tag("previousScreenName", screen.getPreviousScreenName())
                .tag("screenName", screen.getPreviousScreenName())
                .tag("screenSubtype", screen.getPreviousScreenName())
                .tag("screenType", screen.getPreviousScreenName())

                //
                .addField("screenStartTime", screen.getScreenStartTime())
                .addField("screenEndTime", screen.getScreenEndTime())
                .addField("timestamp", screen.getTimestamp())
                .addField("totalScreenTime", screen.getTotalScreenTime())
                .tag("sessionId", screen.getSessionId())
                .tag("userId", screen.getUserId())
                .build();
        influxDB.write(point);
    }


}
