package com.practice.BankingApplication.exception;

import com.practice.BankingApplication.enums.BankingStatusEnum;
import lombok.Getter;

@Getter
public class BankingApplicationException extends RuntimeException {
    private final String statusCode;

    public BankingApplicationException(BankingStatusEnum bankingStatusEnum) {
        super(bankingStatusEnum.getStatusMessage());
        this.statusCode = bankingStatusEnum.getStatusCode();
    }
}
