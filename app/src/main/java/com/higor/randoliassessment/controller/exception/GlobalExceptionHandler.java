package com.higor.randoliassessment.controller.exception;


import com.higor.randoliassessment.exceptions.ResourceNotFound;
import com.higor.randoliassessment.model.StandardError;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFound.class)
    @ApiResponse(responseCode = "404", description = "Resource not Found")
    public ResponseEntity<StandardError> resourceNotFound(ResourceNotFound ex, HttpServletRequest request) {
        StandardError error = StandardError.builder()
            .message(ex.getLocalizedMessage())
            .path(request.getRequestURI())
            .timestamp(Instant.now())
            .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);

    }
}
