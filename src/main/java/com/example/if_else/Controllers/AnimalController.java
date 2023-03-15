package com.example.if_else.Controllers;


import com.example.if_else.Models.Animal;
import com.example.if_else.Models.VisitsLocation;
import com.example.if_else.Servises.AnimalService;
import com.example.if_else.mapers.AnimalProjection;
import com.example.if_else.mapers.ChangeTypeDto;
import com.example.if_else.mapers.VisitsLocationProjection;
import com.example.if_else.request.LocationUpdateRequest;
import com.example.if_else.utils.PojectionConverter;
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
    public ResponseEntity<AnimalProjection> show(@PathVariable("animalId") Long animalId) {
        return animalService.getAnimalById(animalId);
    }


    @GetMapping("search")
    public ResponseEntity<List<AnimalProjection>> findAnimals(AmimalSerchParameters parameters) {

        return animalService.findAnimals(parameters);
    }

    @GetMapping("{animalId}/locations")
    public ResponseEntity<List<VisitsLocationProjection>> getLocation(@PathVariable("animalId") Long animalId,
                                                            AmimalSerchParameters param) {
        try{
            return animalService.getLocation(animalId, param);

        } catch (Exception e) {
            System.out.println("tre");
            throw e;
        }
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


    @DeleteMapping("{animalId}/types/{typeId}")
    public ResponseEntity<AnimalProjection> deleteTypeFromAnimal(@PathVariable("animalId") Long animalId,
                                                                 @PathVariable("typeId") Long typeId) {
        return animalService.deleteTypeFromAnimal(animalId, typeId);
    }


    @PutMapping("/{animalId}/types")
    public ResponseEntity<AnimalProjection> changeTypeFromAnimal(@PathVariable("animalId") Long animalId,
                                                                 @RequestBody ChangeTypeDto changeTypeDto) {
        return animalService.changeTypeFromAnimal(animalId, changeTypeDto);
    }


    @PostMapping("{animalId}/locations/{pointId}")
    public ResponseEntity<VisitsLocationProjection> addLocateToAnimal(@PathVariable Long animalId,
                                                                      @PathVariable Long pointId) {
        return animalService.addLocateToAnimal(animalId, pointId);
    }


    @PutMapping("{animalId}/locations/{pointId}")
    public ResponseEntity<AnimalProjection> changeLocateToAnimal(@PathVariable Long animalId,
                                                                 @RequestBody LocationUpdateRequest locationUpdateRequest) {
        return animalService.changeLocateToAnimal(animalId, locationUpdateRequest);
    }


    @DeleteMapping("/{animalId}/locations/{visitedPointId}")
    public ResponseEntity<Object> deleteLocateFromAnimal(@PathVariable Long animalId,
                                                         @PathVariable Long visitedPointId) {

        return animalService.deleteLocateFromAnimal(animalId, visitedPointId);

    }
}
