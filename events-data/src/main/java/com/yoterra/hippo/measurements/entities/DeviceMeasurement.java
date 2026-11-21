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
public class DeviceMeasurement {
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
   /* @Column(name = "userId")
    private Long userId;*/
}
