package com.yoterra.hippo.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CheckException extends Exception{

    public CheckException(String message){
        super(message);
    }
}
