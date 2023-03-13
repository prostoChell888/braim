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

    @DeleteMapping("{animalId}")
    public ResponseEntity<Object> delete(@PathVariable("animalId") Long animalId) {
        return animalService.deleteAnimal(animalId);
    }

    @PostMapping("{animalId}/types/{typeId}")
    public ResponseEntity<AnimalProjection> addTypeToAnimal(@PathVariable("animalId") Long animalId,
                                                            @PathVariable("typeId") Long typeId) {
        return animalService.addTypeToAnimal(animalId, typeId);
    }
//  todo  DELETE - /animals/{animalId}/types/{typeId}

    @DeleteMapping("{animalId}/types/{typeId}")
    public ResponseEntity<AnimalProjection> deleteTypeFromAnimal(@PathVariable("animalId") Long animalId,
                                                            @PathVariable("typeId") Long typeId) {
        return animalService.deleteTypeFromAnimal(animalId, typeId);
    }

//    todo PUT - /animals/{animalId}/types

//    todo DELETE - /animals/{animalId}/types/{typeId}

//    todo POST - /animals/{animalId}/locations/{pointId}

//    todo PUT - /animals/{animalId}/locations

//    todo DELETE - /animals/{animalId}/locations/{visitedPointId}
}
