package com.practice.BankingApplication.entity.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String id;
    private String accountHolderName;
    private BigDecimal accountBalance;
}
