package com.yoterra.hippo.measurements.load;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventScreenEntity {

    private String eventId;

    private String testId;

    @NotBlank(message = "apiVersion is mandatory")
    private String apiVersion;

    @NotBlank(message = "appVersion is mandatory")
    private String appVersion;

    @Valid
    private DeviceInformation deviceInformation;

    @NotBlank(message = "eventType is mandatory")
    private String eventType;

    @NotBlank(message = "projectName is mandatory")
    private String projectId;

    @NotBlank(message = "projectName is mandatory")
    private String projectName;

    private String previousScreenName;

    @Valid
    private ScreenInformation screenInformation;

    @Valid
    private NavigationParams navigationParams;


    @NotNull(message = "screenStartTime is mandatory")
    private long screenStartTime;

    @NotNull(message = "screenEndTime is mandatory")
    private long screenEndTime;

    @Positive(message = "timestamp should be a positive value")
    private long timestamp;

    @NotNull(message = "totalScreenTime is mandatory")
    private int totalScreenTime;

    @NotBlank(message = "sessionId is mandatory")
    private String sessionId;

    @NotNull(message = "userId is mandatory")
    private String userId;

}
