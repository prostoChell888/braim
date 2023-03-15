package com.example.if_else.mapers;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.List;

@JsonPropertyOrder({"id", "weight", "length", "height", "gender", "lifeStatus", "chipperId",
        "chippingLocationId", "animalTypes", "visitedLocations", "deathDateTime"
})
public interface AnimalProjection {

    Long getId();

    @Value("#{@pojectionConverter.listVisitedLocationsInModelToListInDto(target.getAnimalTypes())}")
    List<Long> getAnimalTypes();

    Float getWeight();

    Float getLength();

    Float getHeight();

    String getGender();

    String getLifeStatus();

    @Value("#{target.getChippingDateTime()}")
    Date getChippingDateTime();


    @Value("#{target.getChipperId().getId()}")
    Integer getChipperId();


    @Value("#{target.getChippingLocationId().getId()}")
    Long getChippingLocationId();
    //

    //
    @Value("#{@pojectionConverter.listVisitedLocationsInModelToListInDto(target.getVisitedLocations())}")
    List<Long> getVisitedLocations();
    //

    @Value("#{target.getDeathDateTime()}")
    Date getDeathDateTime();

}