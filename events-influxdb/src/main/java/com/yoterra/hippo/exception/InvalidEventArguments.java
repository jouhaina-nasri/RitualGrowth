package com.yoterra.hippo.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InvalidEventArguments extends Exception {

    public InvalidEventArguments(String message){
        super(message);
    }
}
