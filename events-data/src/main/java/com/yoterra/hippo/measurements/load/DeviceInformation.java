package com.yoterra.hippo.measurements.load;


import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeviceInformation {
    @NotNull(message = "appVersion is mandatory")
    private long appInstalledTime;
    @NotBlank(message = "deviceModel is mandatory")
    private String deviceModel;
    @NotBlank(message = "manufacturer is mandatory")
    private String manufacturer;
    @NotBlank(message = "appVersion is mandatory")
    private String systemName;
    @NotBlank(message = "systemVersion is mandatory")
    private String systemVersion;
}
