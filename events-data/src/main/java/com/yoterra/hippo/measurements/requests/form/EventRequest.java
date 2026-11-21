package com.yoterra.hippo.measurements.requests.form;


import com.yoterra.hippo.req.PageParams;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springdoc.api.annotations.ParameterObject;

@Getter
@Setter
@NoArgsConstructor
@ParameterObject
public class EventRequest extends PageParams {

    @Parameter( required = false, description = "Filter by event's id")
    private String eventId;

    @Parameter( required = false, description = "Filter by user's id")
    private String userId;

    @Parameter( required = false, description = "Filter by screen name")
    private String screenName;
}
