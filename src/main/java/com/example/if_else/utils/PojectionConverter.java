package com.example.if_else.utils;


import com.example.if_else.Models.AnimalType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PojectionConverter {

    public List<Long> ListAnimalTypesInModelToListInDto(List<AnimalType> animalTypeList) {
        List<Long> idOfAnimalTypes = new ArrayList<>();
        for (var animalType:animalTypeList) {
            idOfAnimalTypes.add(animalType.getId());
        }

        return idOfAnimalTypes;
    }

    public List<Long> ListVisitedLocationsInModelToListInDto(List<AnimalType> animalTypeList) {
        List<Long> idOfAnimalTypes = new ArrayList<>();
        for (var animalType:animalTypeList) {
            idOfAnimalTypes.add(animalType.getId());
        }

        return idOfAnimalTypes;
    }
}
