package com.example.if_else.Servises;


import com.example.if_else.Models.Location;
import com.example.if_else.Reposiories.LocationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Service
@Validated
public class LocationServic {

    private final LocationRepository locationRepository;

    public LocationServic(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }


    public ResponseEntity<Location> getLacattionById(@Valid @Min(1) @NotNull Long pointId) {

        Optional<Location> location = locationRepository.findById(pointId);

        if (location.isPresent()) {
            return ResponseEntity.status(200).body(location.get());
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }
}
