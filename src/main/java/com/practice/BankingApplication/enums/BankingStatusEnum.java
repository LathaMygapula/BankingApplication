package com.practice.BankingApplication.enums;

import lombok.Getter;

@Getter
public enum BankingStatusEnum {
    NO_ACCOUNT_FOUND("BAN_001", "Account id not found");

    private final String statusCode;
    private final String statusMessage;

    BankingStatusEnum(String statusCode, String statusMessage) {
        this.statusCode=statusCode;
        this.statusMessage=statusMessage;
    }
}
