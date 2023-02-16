package com.example.if_else.Servises;


import com.example.if_else.Models.Account;
import com.example.if_else.Models.Location;
import com.example.if_else.Reposiories.LocationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LocationServic {

    private final LocationRepository locationRepository;

    public LocationServic(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }


    public ResponseEntity<Location> getLacateById(Integer pointId) {

        if (pointId == null || pointId < 0) {
            return ResponseEntity.status(400).body(null);
        }

        Optional<Location> location = locationRepository.findById(pointId);

        if (location.isPresent()) {
            return ResponseEntity.status(200).body(location.get());
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }
}
