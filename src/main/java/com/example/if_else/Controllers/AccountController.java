package com.example.if_else.Controllers;


import com.example.if_else.Servises.AccountService;
import com.example.if_else.Models.Account;
import com.example.if_else.utils.SerchingParametrs.AcountSerchParametrs;
import org.springframework.data.domain.Page;
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
    public ResponseEntity<List<Account>> findAccounts(AcountSerchParametrs param) {

        return accountService.findAccounts(param);
    }

    @PutMapping("{accountId}")
    public ResponseEntity<Account> update(@PathVariable("accountId") Integer accountId, Account account) {
        return accountService.updateUserById(accountId, account);
    }
}
