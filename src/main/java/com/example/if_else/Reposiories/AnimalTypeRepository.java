package com.example.if_else.Reposiories;


import com.example.if_else.Models.Account;
import com.example.if_else.Models.AnimalType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalTypeRepository
        extends JpaRepository<AnimalType, Integer> {


}
