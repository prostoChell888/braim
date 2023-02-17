package com.example.if_else.Servises;

import com.example.if_else.Reposiories.AccountRepository;
import com.example.if_else.Models.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {


    private final EntityManager entityManager;
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(EntityManager entityManager, AccountRepository accountRepository) {
        this.entityManager = entityManager;
        this.accountRepository = accountRepository;
    }

    public ResponseEntity<Account> getUserById(Integer accountId) {

        if (accountId == null || accountId < 0) {
            return ResponseEntity.status(400).body(null);
        }

        Optional<Account> account = accountRepository.findById(accountId);

        if (account.isPresent()) {
            return ResponseEntity.status(200).body(account.get());
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    public ResponseEntity<List<Account>> findAccounts(String firstName,
                                                      String lastName,
                                                      String email,
                                                      Integer from,
                                                      Integer size) {

        if (size <= 0 || from < 0) {
            return ResponseEntity.status(400).body(null);
        }

        Query query = entityManager.createQuery("SELECT accounts " +
                "FROM Account accounts " +
                "WHERE (:firstName is null or accounts.firstName LIKE CONCAT('%',:firstName,'%') ) " +
                "AND (:lastName is null or accounts.lastName LIKE CONCAT('%',:lastName,'%') ) " +
                "AND (:email is null or accounts.email LIKE CONCAT('%',:email,'%')) ");

        query.setParameter("firstName", firstName);
        query.setParameter("lastName", lastName);
        query.setParameter("email", email);
        query.setFirstResult(10);
        query.setMaxResults(10);
        List<Account> accounts = query.getResultList();

        return ResponseEntity.status(200).body(accounts);


    }
}
