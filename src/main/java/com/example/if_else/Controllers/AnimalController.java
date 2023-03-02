package com.example.if_else.Controllers;


import com.example.if_else.Models.Animal;
import com.example.if_else.Models.VisitsLocation;
import com.example.if_else.Servises.AnimalService;
import com.example.if_else.utils.SerchingParametrs.AmimalSerchParameters;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("animals")
public class AnimalController {


    private final AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    //todo добавить валидацию
    @GetMapping("{animalId}")
    public ResponseEntity<Animal> show(@PathVariable("animalId") Long animalId) {
        return animalService.getAnimalById(animalId);
    }


    //todo добавить валидацию
    @GetMapping("search")
    public ResponseEntity<List<Animal>> findAnimals(AmimalSerchParameters parameters) {

        return animalService.findAnimals(parameters);
    }

    //todo добавить проекции(переделать классы)
    @GetMapping("{animalId}/locations")
    public ResponseEntity<List<VisitsLocation>> getLocation(@PathVariable("animalId") Long animalId,
                                                            AmimalSerchParameters param) {

        return animalService.getLocation(animalId, param);
    }

}
