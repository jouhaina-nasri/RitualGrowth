package com.yoterra.hippo.configInfluxdb;

import org.influxdb.InfluxDB;
import org.influxdb.dto.Pong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InfluxDbUtils {

    @Autowired
    public InfluxDB influxDB;


    public boolean ping() {
        boolean isConnected = false;
        Pong pong;
        try {
            pong = influxDB.ping();
            if (pong != null) {
                isConnected = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isConnected;
    }
}