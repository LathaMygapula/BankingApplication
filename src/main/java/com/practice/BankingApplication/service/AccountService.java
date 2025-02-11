package com.practice.BankingApplication.service;

import com.practice.BankingApplication.entity.dto.AccountRequest;
import com.practice.BankingApplication.entity.dto.AccountResponse;

public interface AccountService {
    AccountResponse addAccount(AccountRequest accountRequest);
    AccountResponse getAccountById(String id);
    void deleteAccount(String id);
    AccountResponse depositAmount(String id, double amount);
}
