package com.kocheng.demo1.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class UnprocessableEntity extends Exception {
    public UnprocessableEntity (String msg) {
        super(msg);
    }
    
}
