package com.practice.BankingApplication.entity;

import lombok.Data;
import lombok.Generated;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.math.BigDecimal;

@Document
@Data
public class Account {
    @Generated
    private String id;

    @Field(value = "account_holder_name")
    private String accountHolderName;

    @Field(value = " account_balance", targetType = FieldType.DECIMAL128)
    private BigDecimal accountBalance;
}
