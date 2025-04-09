package com.practice.BankingApplication.controller;

import com.practice.BankingApplication.entity.dto.AccountRequest;
import com.practice.BankingApplication.entity.dto.AccountResponse;
import com.practice.BankingApplication.service.AccountService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/banking")
@Log4j2
public class AccountController {
    private static final Logger logger = Logger.getLogger(AccountController.class.getName());

    @Autowired
    private AccountService accountService;

    @PostMapping("/addAccount")
    public ResponseEntity<List<AccountResponse>> addAccount(
            @RequestPart(required = false) MultipartFile file,
            @RequestBody(required = false) AccountRequest inputAccountRequest) throws IOException {
        logger.info("Start add account");
        List<AccountResponse> accountResponseList = new ArrayList<>();

        if (file != null && !file.isEmpty()) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
                br.readLine();
                String line;
                while ((line = br.readLine()) != null && !line.isEmpty()) {
                    String[] values = line.split(",");
                    AccountRequest accountRequest = new AccountRequest();
                    accountRequest.setAccountHolderName(values[0]);
                    accountRequest.setAccountBalance(new BigDecimal(values[1]));
                    AccountResponse accountResponse = accountService.addAccount(accountRequest);
                    accountResponseList.add(accountResponse);
                }
            } catch (IOException e) {
                //TODO : Exception handling
            }
        }
        if (inputAccountRequest != null) {
            AccountResponse accountResponse = accountService.addAccount(inputAccountRequest);
            accountResponseList.add(accountResponse);
        }


        return new ResponseEntity<>(accountResponseList, HttpStatus.OK);
    }

    @GetMapping("/getAccounts")
    public ResponseEntity<List<AccountResponse>> getAllAccounts(@RequestParam int pageSize, @RequestParam int pageNumber) {
        logger.info("start get all accounts");
        List<AccountResponse> allAccounts = accountService.getAllAccounts(pageSize, pageNumber);
        return new ResponseEntity<>(allAccounts, HttpStatus.OK);

    }

    @GetMapping("/getAccountById")
    public ResponseEntity<AccountResponse> getAccountById(@RequestParam @NotBlank String id) {
        AccountResponse accountById = accountService.getAccountById(id);
        return new ResponseEntity<>(accountById, HttpStatus.OK);

    }

    @DeleteMapping("/deleteAccount")
    public String deleteAccount(@RequestParam String id) {
        accountService.deleteAccount(id);
        return "Account successfully deleted with id -> " + id;

    }

    @PutMapping("/deposit")
    public ResponseEntity<AccountResponse> depositAmount(@NotBlank String id, @NotNull double amount) {

        AccountResponse accountResponse = accountService.depositAmount(id, amount);
        return new ResponseEntity<>(accountResponse, HttpStatus.OK);
    }
}
