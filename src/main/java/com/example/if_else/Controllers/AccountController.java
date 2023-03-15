package com.example.if_else.Controllers;


import com.example.if_else.Servises.AccountService;
import com.example.if_else.Models.Account;
import com.example.if_else.utils.SerchingParametrs.AcountSerchParametrs;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("accounts")
public class AccountController {

    private final AccountService accountService;

    @GetMapping("{accountId}")
    public ResponseEntity<Account> show(@PathVariable("accountId") Integer accountId) {
        return accountService.getUserById(accountId);
    }

    @GetMapping("search")
    public ResponseEntity<List<Account>> findAccounts(AcountSerchParametrs param) {

        return accountService.findAccounts(param);
    }

    @PutMapping("{accountId}")
    public ResponseEntity<Account> update(@PathVariable("accountId") Integer accountId,
                                          @RequestBody Account account,
                                          Principal principal) {

        return accountService.updateUserById(accountId, account, principal.getName());
    }

    @DeleteMapping("{accountId}")
    public ResponseEntity<Account> delete(@PathVariable("accountId") Integer accountId,
                                          Principal principal) {


        return accountService.deleteUserById(accountId,  principal.getName());
    }

}
