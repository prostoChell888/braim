package com.example.if_else.Servises;

import com.example.if_else.Models.Animal;
import com.example.if_else.Reposiories.AccountRepository;
import com.example.if_else.Models.Account;
import com.example.if_else.Reposiories.AnimalRepository;
import com.example.if_else.security.UserDetailsPrincipal;
import com.example.if_else.utils.SerchingParametrs.AcountSerchParametrs;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {
//    private final EntityManager entityManager;
    private final AccountRepository accountRepository;
    private final AnimalRepository animalRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Account user = accountRepository.findByEmail(userName).get();

        return new UserDetailsPrincipal(user);
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

    public ResponseEntity<Account> updateUserById(@Valid @Min(1) @NotNull Integer accountId,
                                                  @Valid Account newAccountData,
                                                  String login) {
        Optional<Account> optionalAccount = accountRepository.findById(accountId);

        if (optionalAccount.isEmpty() || !Objects.equals(login, optionalAccount.get().getEmail())) {
            return ResponseEntity.status(403).body(null);
        }

//         optionalAccount = accountRepository.findByEmail(newAccountData.getEmail());
//        if (optionalAccount.isPresent()){
//            return ResponseEntity.status(409).body(null);
//        }

        Account account = optionalAccount.get();
        account.setFirstName(newAccountData.getFirstName());
        account.setLastName(newAccountData.getLastName());
        account.setEmail(newAccountData.getEmail());
        account.setPassword(new BCryptPasswordEncoder().encode(newAccountData.getPassword()));

        Account updatingAccountData = accountRepository.save(account);

        return ResponseEntity.status(200).body(updatingAccountData);
    }


    public ResponseEntity<Account> deleteUserById(@Valid @Min(1) @NotNull Integer accountId, String login) {
        Optional<Account> optionalAccount = accountRepository.findById(accountId);

        if (optionalAccount.isEmpty() || !Objects.equals(login, optionalAccount.get().getEmail())) {
            return ResponseEntity.status(403).body(null);
        }

        List<Animal> animalsList = animalRepository.findByChipperId(optionalAccount.get());
        if (animalsList.size() > 0) {
            return ResponseEntity.status(400).body(null);
        }
        accountRepository.deleteById(accountId);

        return ResponseEntity.ok().body(null);
    }
}
