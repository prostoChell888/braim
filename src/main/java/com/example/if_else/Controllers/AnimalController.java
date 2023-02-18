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

    @GetMapping("{animalId}")
    public ResponseEntity<Animal> show(@PathVariable("animalId") Long animalId) {
        return animalService.getAnimalById(animalId);
    }

    @GetMapping("search")
    public ResponseEntity<List<Animal>> findAnimals(
            @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSSX") Date startDateTime,
            @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSSX") Date endDateTime,
            @RequestParam(required = false) Long chippingLocationId,
            @RequestParam(required = false) String lifeStatus,
            @RequestParam(required = false) String gender,
            @RequestParam(defaultValue = "0") Integer from,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        AmimalSerchParameters parameters = AmimalSerchParameters.builder().
                        startDateTime(startDateTime).
                        endDateTime(endDateTime).
                        chippingLocationId(chippingLocationId).
                        lifeStatus(lifeStatus).
                        gender(gender).
                        from(from).
                        size(size).
                        build();


        return animalService.findAnimals(parameters);
    }


    @PutMapping("{animalId}/locations")
    public ResponseEntity<List<VisitsLocation>> getLocation(@PathVariable("animalId") Long animalId,
                                                            @RequestParam(required = false)
                                             @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSSX") Date startDateTime,
                                                            @RequestParam(required = false)
                                             @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSSX") Date endDateTime,
                                                            @RequestParam(defaultValue = "0") Integer from,
                                                            @RequestParam(defaultValue = "10") Integer size) {

        return animalService.getLocation(animalId, startDateTime, endDateTime, from, size);
    }

}
