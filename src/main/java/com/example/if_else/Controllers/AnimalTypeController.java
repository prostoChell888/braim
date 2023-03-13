package com.example.if_else.Controllers;


import com.example.if_else.Models.Account;
import com.example.if_else.Models.AnimalType;
import com.example.if_else.Servises.AnimalTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("animals/types")
public class AnimalTypeController {

    private final AnimalTypeService animalTypeService;

    @GetMapping("{typeId}")
    public ResponseEntity<AnimalType> show(@PathVariable("typeId") Long typeId) {
        return animalTypeService.getAnimalTypeById(typeId);
    }

    @PostMapping()
    public ResponseEntity<AnimalType> create(@RequestBody AnimalType animalType) {
        return animalTypeService.createAnimalType(animalType);
    }

    @PutMapping("{accountId}")
    public ResponseEntity<AnimalType> update(@PathVariable("accountId") Long animalTypeId,
                                             @RequestBody AnimalType animalType) {

        return animalTypeService.updateAnimalType(animalTypeId, animalType);
    }

    @DeleteMapping("{accountId}")
    public ResponseEntity<AnimalType> delete(@PathVariable("accountId") Long animalTypeId) {

        return animalTypeService.delete(animalTypeId);
    }

}
