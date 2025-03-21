package com.practice.BankingApplication.config;

import com.practice.BankingApplication.entity.BankingApplicationErrorResponse;
import com.practice.BankingApplication.exception.BankingApplicationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BankingApplicationException.class)
    public ResponseEntity<BankingApplicationErrorResponse> handleBankingApplicationException(BankingApplicationException ex) {
        BankingApplicationErrorResponse incentiveErrorResponse = new BankingApplicationErrorResponse(ex.getStatusCode(), ex.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(incentiveErrorResponse, HttpStatus.BAD_REQUEST);
    }

}
