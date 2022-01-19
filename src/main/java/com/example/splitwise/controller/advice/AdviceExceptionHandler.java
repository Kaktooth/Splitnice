package com.example.splitwise.controller.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.naming.AuthenticationException;
import java.sql.SQLInvalidAuthorizationSpecException;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.ResponseEntity.status;

@ControllerAdvice
public class AdviceExceptionHandler {

    Logger log = LoggerFactory.getLogger(AdviceExceptionHandler.class);

    @ExceptionHandler(value = {AuthenticationException.class})
    public ResponseEntity<Object> invalidAuthentication(SQLInvalidAuthorizationSpecException ex) {
        log.error("InvalidAuthenticationException...", ex);
        return status(UNAUTHORIZED).build();
    }

    @ExceptionHandler(value = {EmptyResultDataAccessException.class})
    public ResponseEntity<Object> invalidAuthentication(EmptyResultDataAccessException ex) {
        log.error("Empty result...", ex);
        return status(HttpStatus.NO_CONTENT).build();
    }

    @ExceptionHandler(value = {MissingServletRequestParameterException.class})
    public ResponseEntity<Object> invalidAuthentication(MissingServletRequestParameterException ex) {
        log.error("Missing required parameter", ex.getMessage());
        return status(HttpStatus.BAD_REQUEST).build();
    }
}
