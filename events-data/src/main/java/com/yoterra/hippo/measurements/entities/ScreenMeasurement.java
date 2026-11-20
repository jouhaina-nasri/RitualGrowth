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
public class ScreenMeasurement {
    @Column(name="eventId",tag = true)
    String eventId;

    @Column(name="testId",tag = true)
    String testId;

    @Column(name="apiVersion")
    private String apiVersion;

    @Column(name="appVersion")
    private String appVersion;

    @Column(name = "cohortIdHash")
    private String cohortIdHash;

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

    @Column(name = "projectIdHash")
    private String projectIdHash;

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

    @Column(name = "userIdHash")
    private String userIdHash;
}
