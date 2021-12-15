package com.higor.randoliassessment.exceptions;

public class CustomException extends Exception {

    private int statusCode;
    public CustomException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return this.statusCode;
    }
}
