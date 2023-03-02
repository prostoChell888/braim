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
import javax.persistence.Query;
import javax.validation.Valid;
import java.util.List;
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

    public ResponseEntity<Account> getUserById(Integer accountId) {

        if (accountId == null || accountId <= 0) {
            return ResponseEntity.status(400).body(null);
        }

        Optional<Account> account = accountRepository.findById(accountId);

        if (account.isPresent()) {
            return ResponseEntity.status(200).body(account.get());
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    public ResponseEntity<List<Account>> findAccounts(AcountSerchParametrs param) {
        if (param.getSize() <= 0 || param.getFrom() < 0) {
            return ResponseEntity.status(400).body(null);
        }
        if (param.getEmail() != null)
            param.setEmail(param.getEmail().toUpperCase());
        if (param.getFirstName() != null)
            param.setFirstName(param.getFirstName().toUpperCase());
        if (param.getLastName() != null)
            param.setLastName(param.getLastName().toUpperCase());

        Query query = entityManager.createQuery(
                "SELECT accounts " +
                        "FROM Account accounts " +
                        "WHERE (:firstName is null or accounts.firstName LIKE '%:firstName%') " +
                        "AND (:lastName is null or accounts.lastName LIKE '%:lastName%' ) " +
                        "AND (:email is null or accounts.email  LIKE '%:email%' )");

        query.setParameter("firstName", param.getFirstName());
        query.setParameter("lastName", param.getLastName());
        query.setParameter("email", param.getEmail());
        query.setFirstResult(param.getFrom());
        query.setMaxResults(param.getSize());
        List<Account> accounts = query.getResultList();

        return ResponseEntity.status(200).body(accounts);
    }

    //todo сделать обработку перед сохранением
    public ResponseEntity<Account> addAccount(@Valid Account account) {
        Optional<Account> acc = accountRepository.findByEmail(account.getEmail());

        if (acc.isPresent()) {
            return ResponseEntity.status(409).body(null);
        }
        account.setPassword(new BCryptPasswordEncoder().encode(account.getPassword()));
        accountRepository.save(account);
        return ResponseEntity.status(201).body(account);
    }
}
