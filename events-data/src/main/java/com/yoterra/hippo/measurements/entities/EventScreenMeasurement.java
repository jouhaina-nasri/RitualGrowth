package com.yoterra.hippo.measurements.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Measurement(name = "events_screens")
public class EventScreenMeasurement {

    @Column(name="eventId",tag = true)
    String eventId;

    @Column(name="testId",tag = true)
    String testId;

    @Column(name="apiVersion")
    private String apiVersion;

    @Column(name="appVersion")
    private String appVersion;

    // device info
    @Column(name="appInstalledTime")
    private long appInstalledTime;

    @Column(name="deviceModel")
    private String deviceModel;

    @Column(name="manufacturer")
    private String manufacturer;

    @Column(name="systemName")
    private String systemName;

    @Column(name="systemVersion")
    private String systemVersion;
    //

    @Column(name = "eventType")
    private String eventType;

    @Column(name = "projectId")
    private String projectId;

    @Column(name = "projectName")
    private String projectName;

    @Column(name = "previousScreenName")
    private String previousScreenName;

    // screen Information
    @Column(name = "screenName")
    private String screenName;

    @Column(name = "screenSubtype")
    private String screenSubtype;

    @Column(name = "screenType")
    private String screenType;

    //navigation params
    @Column(name = "navParamType")
    private String navParamType;

    @Column(name = "navParamId")
    private String navParamId;

    //
    @Column(name = "screenStartTime")
    private long screenStartTime;

    @Column(name = "screenEndTime")
    private long screenEndTime;

    @Column(name = "timestamp")
    private long timestamp;

    @Column(name = "totalScreenTime")
    private int totalScreenTime;

    @Column(name = "sessionId")
    private String sessionId;

    @Column(name = "userId")
    private String userId;

    @Column(name = "projectIdHash")
    private String projectIdHash;

    @Column(name = "cohortIdHash")
    private String cohortIdHash;

    @Column(name = "userIdHash")
    private String userIdHash;

    public EventScreenMeasurement(String eventId, String testId, String apiVersion, String appVersion, long appInstalledTime, String deviceModel, String manufacturer, String systemName, String systemVersion, String eventType, String projectId, String projectName, String previousScreenName, String screenName, String screenSubtype, String screenType, String navParamType, String navParamId, long screenStartTime, long screenEndTime, long timestamp, int totalScreenTime, String sessionId, String userId) {
        this.eventId = eventId;
        this.testId = testId;
        this.apiVersion = apiVersion;
        this.appVersion = appVersion;
        this.appInstalledTime = appInstalledTime;
        this.deviceModel = deviceModel;
        this.manufacturer = manufacturer;
        this.systemName = systemName;
        this.systemVersion = systemVersion;
        this.eventType = eventType;
        this.projectId = projectId;
        this.projectName = projectName;
        this.previousScreenName = previousScreenName;
        this.screenName = screenName;
        this.screenSubtype = screenSubtype;
        this.screenType = screenType;
        this.navParamType = navParamType;
        this.navParamId = navParamId;
        this.screenStartTime = screenStartTime;
        this.screenEndTime = screenEndTime;
        this.timestamp = timestamp;
        this.totalScreenTime = totalScreenTime;
        this.sessionId = sessionId;
        this.userId = userId;
    }
}
