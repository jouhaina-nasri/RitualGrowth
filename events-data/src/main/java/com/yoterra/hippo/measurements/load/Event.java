package com.yoterra.hippo.measurements.load;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    public String appVersion;
    public DeviceInformation deviceInformation;
    public String eventType;
    public String inputFieldId;
    public String screenName;
    public long timestamp;
    public String userId;
}
