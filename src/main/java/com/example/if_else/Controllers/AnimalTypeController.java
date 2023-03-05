package com.example.if_else.Controllers;


import com.example.if_else.Models.AnimalType;
import com.example.if_else.Servises.AnimalTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("animals/types")
public class AnimalTypeController {

    private final AnimalTypeService animalTypeService;

    @GetMapping("{typeId}")
    public ResponseEntity<AnimalType> show(@PathVariable("typeId") Long typeId) {
        return animalTypeService.getAnimalTypeById(typeId);
    }

}
