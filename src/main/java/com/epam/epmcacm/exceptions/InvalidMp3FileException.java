package com.epam.epmcacm.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidMp3FileException extends  RuntimeException {

    public InvalidMp3FileException(String message)
    {
        super(message);
    }
}
