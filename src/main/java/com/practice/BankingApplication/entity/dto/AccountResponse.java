package com.practice.BankingApplication.entity.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountResponse {
    private String id;
    private String accountHolderName;
    private BigDecimal accountBalance;
}
