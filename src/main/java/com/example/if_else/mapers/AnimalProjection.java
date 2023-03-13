package com.example.if_else.mapers;

import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.List;

public interface AnimalProjection {

    Long getId();
    Float getWeight();
    Float getLength();
    Float getHeight();
    String getGender();
    String getLifeStatus();

    @Value("#{target.getChipperId().getId()}")
    Integer getChipperId();


    Date getChippingDateTime();

    @Value("#{target.getChippingLocationId().getId()}")
    Long getChippingLocationId();
    //
    @Value("#{@pojectionConverter.ListAnimalTypesInModelToListInDto(target.getAnimalTypes())}")
    List<Long> getAnimalTypes();
    //
    @Value("#{@pojectionConverter.ListVisitedLocationsInModelToListInDto(target.getVisitedLocations())}")
    List<Long> getVisitedLocations();
    //
    @Value("#{target.getDeathDateTime()}")
    Date getDeathDateTime();
}