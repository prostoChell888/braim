package com.example.if_else.Controllers;


import com.example.if_else.Models.Animal;
import com.example.if_else.Models.VisitsLocation;
import com.example.if_else.Servises.AnimalService;
import com.example.if_else.mapers.AnimalProjection;
import com.example.if_else.utils.SerchingParametrs.AmimalSerchParameters;
import com.example.if_else.utils.SerchingParametrs.AnimalCreateParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("animals")
public class AnimalController {


    private final AnimalService animalService;


    @GetMapping("{animalId}")
    public ResponseEntity<Animal> show(@PathVariable("animalId") Long animalId) {
        return animalService.getAnimalById(animalId);
    }



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

    @PostMapping
    public ResponseEntity<AnimalProjection> create(@RequestBody AnimalCreateParam animalCreateParam) {
        return animalService.createAnimal(animalCreateParam);
    }


@PutMapping("{animalId}")
public ResponseEntity<AnimalProjection> update(@PathVariable("animalId") Long animalId,
                                               @RequestBody AnimalCreateParam animalCreateParam) {
    return animalService.updateAnimal(animalId, animalCreateParam);
}

//    todo  DELETE - /animals/{animalId}

//    todo  POST - /animals/{animalId}/types/{typeId}

//    todo PUT - /animals/{animalId}/types

//    todo DELETE - /animals/{animalId}/types/{typeId}

//    todo POST - /animals/{animalId}/locations/{pointId}

//    todo PUT - /animals/{animalId}/locations

//    todo DELETE - /animals/{animalId}/locations/{visitedPointId}
}
