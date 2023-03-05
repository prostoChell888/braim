package com.example.if_else.Controllers;


import com.example.if_else.Servises.AccountService;
import com.example.if_else.Models.Account;
import com.example.if_else.utils.SerchingParametrs.AcountSerchParametrs;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("search")
    public ResponseEntity<List<Account>> findAccounts(AcountSerchParametrs param) {

        return accountService.findAccounts(param);
    }

    @GetMapping("{accountId}")
    public ResponseEntity<Account> show(@PathVariable("accountId") Integer accountId) {
        return accountService.getUserById(accountId);
    }

    @PutMapping("{accountId}")
    public ResponseEntity<Account> update(@PathVariable("accountId") Integer accountId,
                                          @RequestBody Account account,
                                          Principal principal) {

        return accountService.updateUserById(accountId, account, principal.getName());
    }

}
