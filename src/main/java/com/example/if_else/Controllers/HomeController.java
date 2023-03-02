package com.example.if_else.Controllers;


import com.example.if_else.Models.Account;
import com.example.if_else.Servises.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    private final AccountService accountService;

    public HomeController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("registration")
    public ResponseEntity<Account>  regisrate(@RequestBody Account account) {
        return accountService.addAccount(account);
    }

}
