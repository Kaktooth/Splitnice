package com.example.splitwise.controller.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.naming.AuthenticationException;
import java.sql.SQLInvalidAuthorizationSpecException;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.ResponseEntity.status;

@ControllerAdvice
public class AdviceExceptionHandler {

    Logger log = LoggerFactory.getLogger(AdviceExceptionHandler.class);

    @ExceptionHandler(value = {AuthenticationException.class})
    public ResponseEntity<Object> invalidAuthentication(SQLInvalidAuthorizationSpecException ex, WebRequest request) {
        log.error("InvalidAuthenticationException...", ex);
        return status(UNAUTHORIZED).build();
    }

    @ExceptionHandler(value = {EmptyResultDataAccessException.class})
    public ResponseEntity<Object> invalidAuthentication(EmptyResultDataAccessException ex, WebRequest request) {
        log.error("Empty result...", ex);
        return status(HttpStatus.NO_CONTENT).build();
    }
}
