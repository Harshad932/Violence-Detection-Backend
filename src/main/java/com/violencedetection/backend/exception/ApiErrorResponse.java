package com.violencedetection.backend.exception;

import java.time.LocalDateTime;

public class ApiErrorResponse {

    private int status;
    private String message;
    private LocalDateTime timeStamp;

    public ApiErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
        this.timeStamp = LocalDateTime.now();
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }
}
