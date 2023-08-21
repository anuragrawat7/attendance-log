package com.latttice.attendancelog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class CommonExceptionHandling {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException rex, WebRequest req){
        ErrorResponse errorResponse = new ErrorResponse(new Date(), rex.getMessage(), req.getDescription(false));
        return new ResponseEntity<Object>(errorResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleCommonException(Exception exception, WebRequest req){
        ErrorResponse errorResponse = new ErrorResponse(new Date(), exception.getMessage(), req.getDescription(false));
        return new ResponseEntity<Object>(errorResponse,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
