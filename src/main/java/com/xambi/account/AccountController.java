/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xambi.account;

import com.xambi.account.Account;
import com.xambi.account.AccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author User
 */
@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;

    @PostMapping(path = "/register")
    public @ResponseBody
    ResponseEntity<?> registerAccount(@RequestBody AccountDTO accountRegistration) {
        boolean isValid = true;
        if (isValid) {
            Account account = accountRegistration.prepareAccount();
            accountRepository.save(account);
            return ResponseEntity.ok("Account registered successfully!");
        }

        return ResponseEntity.badRequest().body("Error registering account.");
    }
}
