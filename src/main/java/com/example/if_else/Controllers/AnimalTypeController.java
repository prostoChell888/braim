package com.example.if_else.Controllers;


import com.example.if_else.Models.Account;
import com.example.if_else.Models.AnimalType;
import com.example.if_else.Servises.AnimalTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("animals/types")
public class AnimalTypeController {

    private final AnimalTypeService animalTypeService;

    public AnimalTypeController(AnimalTypeService animalTypeService) {
        this.animalTypeService = animalTypeService;
    }

    @GetMapping("{typeId}")
    public ResponseEntity<AnimalType> show(@PathVariable("typeId") Integer typeId) {
        return animalTypeService.getAnimalTypeById(typeId);
    }

}
