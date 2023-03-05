package com.example.if_else.Servises;

import com.example.if_else.Reposiories.AccountRepository;
import com.example.if_else.Models.Account;
import com.example.if_else.security.UserDetailsPrincipal;
import com.example.if_else.utils.SerchingParametrs.AcountSerchParametrs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Validated
public class AccountService implements UserDetailsService {
    private final EntityManager entityManager;
    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Account user = accountRepository.findByEmail(userName).get();
        UserDetailsPrincipal userDetailsPrincipal = new UserDetailsPrincipal(user);

        return userDetailsPrincipal;
    }


    @Autowired
    public AccountService(EntityManager entityManager, AccountRepository accountRepository) {
        this.entityManager = entityManager;
        this.accountRepository = accountRepository;
    }

    public ResponseEntity<Account> getUserById(@Valid @Min(1) @NotNull Integer accountId) {

        Optional<Account> account = accountRepository.findById(accountId);

        if (account.isPresent()) {
            return ResponseEntity.status(200).body(account.get());
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    public ResponseEntity<List<Account>> findAccounts(@Valid AcountSerchParametrs param) {


        List<Account> accounts = accountRepository.
                findAccByParams(param.getFirstName(),
                        param.getLastName(),
                        param.getEmail(),
                        param.getSize(),
                        param.getFrom());


        return ResponseEntity.ok().body(accounts);
    }

    public ResponseEntity<Account> addAccount(@Valid Account account) {
        Optional<Account> acc = accountRepository.findByEmail(account.getEmail());

        if (acc.isPresent()) {
            return ResponseEntity.status(409).body(null);
        }
        account.setPassword(new BCryptPasswordEncoder().encode(account.getPassword()));
        accountRepository.save(account);
        return ResponseEntity.status(201).body(account);
    }

    public ResponseEntity<Account> updateUserById(Integer accountId, Account newAccountData, String login) {
        Optional<Account> optionalAccount = accountRepository.findById(accountId);

        if (optionalAccount.isEmpty() || !Objects.equals(login, optionalAccount.get().getEmail())) {
            return ResponseEntity.status(403).body(null);
        }
        Account account = optionalAccount.get();
        account.setFirstName(newAccountData.getFirstName());
        account.setLastName(newAccountData.getLastName());
        account.setEmail(newAccountData.getEmail());
        account.setPassword(newAccountData.getPassword());

        Account updatingAccountData = accountRepository.save(account);

        return ResponseEntity.status(200).body(updatingAccountData);
    }


}
