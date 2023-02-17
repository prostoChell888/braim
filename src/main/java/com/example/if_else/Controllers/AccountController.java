package com.example.if_else.Controllers;


import com.example.if_else.Servises.AccountService;
import com.example.if_else.Models.Account;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("accounts")

public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }


    @GetMapping("{accountId}")
    public ResponseEntity<Account> show(@PathVariable("accountId") Integer accountId) {
        return accountService.getUserById(accountId);
    }

    @GetMapping("search")
    public ResponseEntity<List<Account>> findAccounts
            (@RequestParam(required = false) String firstName,
             @RequestParam(required = false) String lastName,
             @RequestParam(required = false) String email,
             @RequestParam(defaultValue = "0") Integer from,
             @RequestParam(defaultValue = "10") Integer size) {

        return accountService.findAccounts(firstName, lastName, email, from, size);

    }
}
