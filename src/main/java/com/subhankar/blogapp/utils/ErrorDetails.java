package com.subhankar.blogapp.utils;

import org.springframework.http.HttpStatus;

import java.util.Date;

public class ErrorDetails {
    private Date timeStamp;
    private String message;
    private String details;
    private int errorCode;

    public ErrorDetails(Date timeStamp, String message, int errorCode, String details) {
        this.timeStamp = timeStamp;
        this.message = message;
        this.details = details;
        this.errorCode=errorCode;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
