package com.example.if_else.Servises;


import com.example.if_else.Models.Account;
import com.example.if_else.Models.Location;
import com.example.if_else.Reposiories.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Service
@Validated
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;


    public ResponseEntity<Location> getLacattionById(@Valid @Min(1) @NotNull Long pointId) {

        Optional<Location> location = locationRepository.findById(pointId);

        if (location.isPresent()) {
            return ResponseEntity.status(200).body(location.get());
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }


    public ResponseEntity<Location> addLocation(@Valid Location location) {
        if (isPresentOfLongitudeAndLatitudeOnDB(location)) {
            return ResponseEntity.status(409).body(null);
        }

        location = locationRepository.save(location);
        return ResponseEntity.status(201).body(location);
    }

    public ResponseEntity<Location> updateUserById(@Valid @Min(1) @NotNull Long accountId,
                                                  @Valid Location location) {
        if (isPresentOfLongitudeAndLatitudeOnDB(location)) {
            return ResponseEntity.status(409).body(null);
        }

        Optional<Location> optionalLocation = locationRepository.findById(accountId);

        if (optionalLocation.isEmpty() ) {
            return ResponseEntity.status(404).body(null);
        }

        Location newLocation = optionalLocation.get();
        newLocation.setLatitude(location.getLatitude());
        newLocation.setLongitude(location.getLongitude());

        newLocation = locationRepository.save(newLocation);

        return ResponseEntity.status(201).body(newLocation);
    }

    private boolean isPresentOfLongitudeAndLatitudeOnDB(Location location) {

        Optional<Location> optionalLocation2 = locationRepository
                .findByLatitudeAndLongitude(location.getLatitude(), location.getLongitude());

        return optionalLocation2.isPresent();

    }
}
