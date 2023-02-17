package com.example.if_else.Servises;


import com.example.if_else.Models.Animal;
import com.example.if_else.Reposiories.AnimalRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AnimalService {

    private final AnimalRepository animalRepository;

    public AnimalService(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    public ResponseEntity<Animal> getAnimalById(Long animalId) {

        if (animalId == null || animalId < 0) {
            return ResponseEntity.status(400).body(null);
        }

        //todo вернуть проекцию, а не объект
        Optional<Animal> account = animalRepository.findById(animalId);

        if (account.isPresent()) {
            return ResponseEntity.status(200).body(account.get());
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }
}
