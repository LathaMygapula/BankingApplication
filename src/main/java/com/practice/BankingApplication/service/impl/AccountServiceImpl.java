package com.practice.BankingApplication.service.impl;

import com.practice.BankingApplication.entity.Account;
import com.practice.BankingApplication.entity.dto.AccountRequest;
import com.practice.BankingApplication.entity.dto.AccountResponse;
import com.practice.BankingApplication.repository.AccountRepository;
import com.practice.BankingApplication.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired private AccountRepository accountRepository;


    @Override
    public AccountResponse addAccount(AccountRequest accountRequest) {
        Account account = convertAccountRequestToAccountEntity(accountRequest);
        Account savedAccount = accountRepository.save(account);
        return convertAccountEntityToAccountResponse(savedAccount);

    }

    @Override
    public AccountResponse getAccountById(String id) {
        //TODO : handle error scenario also with custom exception
        Account accountEntity = accountRepository.findById(id).get();
        return convertAccountEntityToAccountResponse(accountEntity);
    }

    @Override
    public void deleteAccount(String id) {
        accountRepository.deleteById(id);
    }

    @Override
    public AccountResponse depositAmount(String id, double amount) {
        //TODO: Handle Exceptions
        Account account = accountRepository.findById(id).get();
        BigDecimal newAmount = account.getAccountBalance().add(new BigDecimal(amount));
        account.setAccountBalance(newAmount);
        Account updatedAccount = accountRepository.save(account);
        return convertAccountEntityToAccountResponse(updatedAccount);

    }

    @Override
    public List<AccountResponse> getAllAccounts(int pageSize, int pageNumber) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<Account> allAccounts = accountRepository.findAll(pageRequest);
        return allAccounts.getContent()
                .stream()
                .map(this::convertAccountEntityToAccountResponse).toList();

    }

    private AccountResponse convertAccountEntityToAccountResponse(Account savedAccount) {
        AccountResponse accountResponse = new AccountResponse();
        accountResponse.setId(savedAccount.getId());
        accountResponse.setAccountHolderName(savedAccount.getAccountHolderName());
        accountResponse.setAccountBalance(savedAccount.getAccountBalance());
        return accountResponse;
    }

    private Account convertAccountRequestToAccountEntity(AccountRequest accountRequest) {
        Account account = new Account();
        account.setAccountHolderName(accountRequest.getAccountHolderName());
        account.setAccountBalance(accountRequest.getAccountBalance());
        return account;
    }
}
