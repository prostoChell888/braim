package com.example.if_else.utils;


import com.example.if_else.Models.AnimalType;
import com.example.if_else.Models.VisitsLocation;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PojectionConverter {




    public List<Long> listVisitedLocationsInModelToListInDto(List< IdInterface> animalTypeList) {

        return animalTypeList.stream()
                .map( IdInterface::getId)
                .collect(Collectors.toList());
    }
}
