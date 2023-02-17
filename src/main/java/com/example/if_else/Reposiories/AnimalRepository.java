package com.example.if_else.Reposiories;

import com.example.if_else.Models.Account;
import com.example.if_else.Models.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalRepository extends JpaRepository<Animal, Long> {
}
