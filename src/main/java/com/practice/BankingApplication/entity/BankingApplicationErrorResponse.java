package com.practice.BankingApplication.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class BankingApplicationErrorResponse {
    private String statusCode;
    private String statusMessage;
    private LocalDateTime statusDateTime;
}
