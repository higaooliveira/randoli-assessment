package com.higor.randoliassessment.controller.exception;


import com.higor.randoliassessment.exceptions.InternalServerError;
import com.higor.randoliassessment.exceptions.ResourceNotFound;
import com.higor.randoliassessment.model.StandardError;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(ResourceNotFound.class)
    @ApiResponse(responseCode = "404", description = "Resource not Found")
    public ResponseEntity<StandardError> resourceNotFound(ResourceNotFound ex, HttpServletRequest request) {
        logger.error(ex.getLocalizedMessage());
        StandardError error = StandardError.builder()
            .message(ex.getLocalizedMessage())
            .path(request.getRequestURI())
            .timestamp(Instant.now())
            .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);

    }

    @ExceptionHandler(InternalServerError.class)
    @ApiResponse(responseCode = "500", description = "Internal Server Error")
    public ResponseEntity<StandardError> internalServerError(InternalServerError ex, HttpServletRequest request) {
        logger.error(ex.getLocalizedMessage());
        StandardError error = StandardError.builder()
                .message(ex.getOriginalMessage())
                .path(request.getRequestURI())
                .timestamp(Instant.now())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);

    }
}
