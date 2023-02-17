package com.example.if_else.Servises;


import com.example.if_else.Models.Account;
import com.example.if_else.Models.AnimalType;
import com.example.if_else.Reposiories.AnimalTypeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AnimalTypeService {

    private final AnimalTypeRepository animalTypeRepository;

    public AnimalTypeService(AnimalTypeRepository animalTypeRepository) {
        this.animalTypeRepository = animalTypeRepository;
    }


    public ResponseEntity<AnimalType> getAnimalTypeById(Long typeId) {

        if (typeId == null || typeId < 0) {
            return ResponseEntity.status(400).body(null);
        }

        Optional<AnimalType> animalType = animalTypeRepository.findById(typeId);

        if (animalType.isPresent()) {
            return ResponseEntity.status(200).body(animalType.get());
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }
}
