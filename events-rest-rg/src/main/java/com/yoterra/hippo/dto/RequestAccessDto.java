package com.yoterra.hippo.dto;

import com.yoterra.hippo.jpa.entities.Company;
import com.yoterra.hippo.jpa.entities.RequestAccess;
import com.yoterra.utils.Opt;
import lombok.Getter;

import java.util.Calendar;

@Getter
public class RequestAccessDto {

    private long id;
    private UserDto admin;
    private UserDto sender;

    private String userRole;
    private String status;
    private Calendar time;

    public static RequestAccessDto of (RequestAccess requestAccess) {
        return Opt.get(requestAccess, RequestAccessDto::new);
    }

    public RequestAccessDto(RequestAccess request) {
        this.id = request.getId();
        this.admin = UserDto.of(request.getAdmin());
        this.sender = UserDto.of(request.getUser());
        this.userRole = request.getUserRole().name();
        this.status = request.getStatus().name();
        this.time = request.getCreationTime();
    }


}
