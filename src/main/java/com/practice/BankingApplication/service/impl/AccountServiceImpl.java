package com.practice.BankingApplication.service.impl;

import com.practice.BankingApplication.entity.Account;
import com.practice.BankingApplication.entity.dto.AccountRequest;
import com.practice.BankingApplication.entity.dto.AccountResponse;
import com.practice.BankingApplication.enums.BankingStatusEnum;
import com.practice.BankingApplication.exception.BankingApplicationException;
import com.practice.BankingApplication.repository.AccountRepository;
import com.practice.BankingApplication.service.AccountService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
@Log4j2
public class AccountServiceImpl implements AccountService {
    private static final Logger logger = Logger.getLogger(AccountServiceImpl.class.getName());

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public AccountResponse addAccount(AccountRequest accountRequest) {
        logger.info("account request - " + accountRequest);
        Account account = convertAccountRequestToAccountEntity(accountRequest);
        Account savedAccount = accountRepository.save(account);
        return convertAccountEntityToAccountResponse(savedAccount);

    }

    @Cacheable(value = "account", key = "#id")
    @Override
    public AccountResponse getAccountById(String id) {
        logger.info("get account by id -> " + id);
        Account accountEntity = accountRepository.findById(id)
                .orElseThrow(() -> new BankingApplicationException(BankingStatusEnum.NO_ACCOUNT_FOUND));
        return convertAccountEntityToAccountResponse(accountEntity);
    }

    @CacheEvict(value = "account", key = "#id")
    @Override
    public void deleteAccount(String id) {
        logger.info("delete account by id -> " + id);
        accountRepository.findById(id)
                .orElseThrow(() -> new BankingApplicationException(BankingStatusEnum.NO_ACCOUNT_FOUND));
        accountRepository.deleteById(id);
    }

    @CachePut(value = "account", key = "#id")
    @Override
    public AccountResponse depositAmount(String id, double amount) {
        logger.info("deposit amount");
        Optional<Account> optionalAccount = accountRepository.findById(id);
        if (optionalAccount.isEmpty()) {
            throw new BankingApplicationException(BankingStatusEnum.NO_ACCOUNT_FOUND);
        }
        Account account = optionalAccount.get();
        BigDecimal newAmount = account.getAccountBalance().add(new BigDecimal(amount));
        account.setAccountBalance(newAmount);
        Account updatedAccount = accountRepository.save(account);
        return convertAccountEntityToAccountResponse(updatedAccount);

    }

    @Override
    public List<AccountResponse> getAllAccounts(int pageSize, int pageNumber) {
        logger.info("Get all accounts. Page size - " + pageSize + ", Page Number - " + pageNumber);
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
