package com.higor.randoliassessment.exceptions;

import lombok.Getter;

@Getter
public class InternalServerError extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String originalMessage;

    public InternalServerError(String originalMessage) {
        super("Internal Server Error");
        this.originalMessage = originalMessage;
    }

}
