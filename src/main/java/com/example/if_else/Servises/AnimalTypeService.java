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
}
