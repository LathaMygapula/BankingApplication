package com.practice.BankingApplication.controller;

import com.practice.BankingApplication.entity.dto.AccountRequest;
import com.practice.BankingApplication.entity.dto.AccountResponse;
import com.practice.BankingApplication.service.AccountService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

@RestController
@RequestMapping("/api/banking")
public class AccountController {

    @Autowired private AccountService accountService;

    @PostMapping("/addAccount")
    public ResponseEntity<List<AccountResponse>> addAccount(
            @RequestPart(required = false) MultipartFile file,
            @RequestBody(required = false) AccountRequest inputAccountRequest) throws IOException {
        List<AccountResponse> accountResponseList = new ArrayList<>();

        if(file != null && !file.isEmpty()) {
            try(BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
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
            }
            catch (IOException e){
                //TODO : Exception handling
            }
        }
        if(inputAccountRequest != null) {
            AccountResponse accountResponse = accountService.addAccount(inputAccountRequest);
            accountResponseList.add(accountResponse);
        }


        return new ResponseEntity<>(accountResponseList, HttpStatus.OK);
    }

    @GetMapping("/getAccounts")
    public ResponseEntity<List<AccountResponse>> getAllAccounts(@RequestParam int pageSize, @RequestParam int pageNumber) {
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
        return "Account successfully deleted with id -> "+id;

    }

    @PutMapping("/deposit")
    public ResponseEntity<AccountResponse> depositAmount(@NotBlank String id, @NotNull double amount) {

        AccountResponse accountResponse = accountService.depositAmount(id, amount);
        return new ResponseEntity<>(accountResponse, HttpStatus.OK);
    }
}
