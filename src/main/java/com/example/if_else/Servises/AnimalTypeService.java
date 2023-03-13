package com.example.if_else.Servises;


import com.example.if_else.Models.AnimalType;
import com.example.if_else.Reposiories.AnimalTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Service
@Validated
@RequiredArgsConstructor
public class AnimalTypeService {

    private final AnimalTypeRepository animalTypeRepository;


    public ResponseEntity<AnimalType> getAnimalTypeById(@Valid @Min(1) @NotNull Long typeId) {

         Optional<AnimalType> animalType = animalTypeRepository.findById(typeId);

        if (animalType.isPresent()) {
            return ResponseEntity.status(200).body(animalType.get());
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    public ResponseEntity<AnimalType> createAnimalType(@Valid AnimalType animalType) {
        Optional<AnimalType> animalTypeOptional = animalTypeRepository.findByType(animalType.getType());

        if (animalTypeOptional.isPresent()) {
            return ResponseEntity.status(409).body(null);
        }

        animalType = animalTypeRepository.save(animalType);

        return ResponseEntity.status(201).body(animalType);

    }

    public ResponseEntity<AnimalType> updateAnimalType(Long animalTypeId, AnimalType animalType) {
        Optional<AnimalType> optionalAnimalType = animalTypeRepository.findById(animalTypeId);

        if (optionalAnimalType.isEmpty()){
            return ResponseEntity.status(404).body(null);
        }
        optionalAnimalType = animalTypeRepository.findByType(animalType.getType());

        if (optionalAnimalType.isPresent()){
            return ResponseEntity.status(409).body(null);
        }

        animalType.setId(animalTypeId);
        animalType = animalTypeRepository.save(animalType);

        return ResponseEntity.status(200).body(animalType);

    }

    public ResponseEntity<AnimalType> delete(@Valid @Min(1) @NotNull Long animalTypeId) {
        Optional<AnimalType> optionalAnimalType = animalTypeRepository.findById(animalTypeId);

        if (optionalAnimalType.isEmpty()) {
            return ResponseEntity.status(404).body(null);
        }

        animalTypeRepository.deleteById(animalTypeId);

        return ResponseEntity.ok().body(null);
    }
}
