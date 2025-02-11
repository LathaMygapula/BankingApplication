package com.practice.BankingApplication.repository;

import com.practice.BankingApplication.entity.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountRepository extends MongoRepository<Account, String> {
}
