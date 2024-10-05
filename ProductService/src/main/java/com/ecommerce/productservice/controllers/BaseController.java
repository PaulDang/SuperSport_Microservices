package com.ecommerce.productservice.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.function.Supplier;

@Slf4j
public abstract class BaseController {
    protected <T> ResponseEntity<T> handleRequest(Supplier<T> supplier, String errorMessage) {
        try {
            T result = supplier.get();
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            log.error(errorMessage, e);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return logAndRespond(errorMessage, e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    protected <T> ResponseEntity<T> logAndRespond(String message, Exception e, HttpStatus status) {
        log.error(message, e);
        return new ResponseEntity<>(null, status);
    }
}
