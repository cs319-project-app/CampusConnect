package com.fullstackfidelity.campusconnect.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.CONFLICT)
public class WrongCodeException extends RuntimeException {
    public WrongCodeException(String s)  {
        super(s);
    }
}
