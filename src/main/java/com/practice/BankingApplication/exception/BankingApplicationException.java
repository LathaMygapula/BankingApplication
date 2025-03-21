package com.practice.BankingApplication.exception;

import com.practice.BankingApplication.enums.BankingStatusEnum;

public class BankingApplicationException extends RuntimeException {
    private final String statusCode;

    public String getStatusCode() {
        return statusCode;
    }

    public BankingApplicationException(BankingStatusEnum bankingStatusEnum) {
        super(bankingStatusEnum.getStatusMessage());
        this.statusCode = bankingStatusEnum.getStatusCode();
    }
}
