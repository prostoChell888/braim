package com.example.if_else.Controllers;


import com.example.if_else.Models.Account;
import com.example.if_else.Models.Location;
import com.example.if_else.Servises.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("locations")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationServic;


    @GetMapping("{pointId}")
    public ResponseEntity<Location> show(@PathVariable("pointId") Long pointId) {
        return locationServic.getLacattionById(pointId);
    }

    @PostMapping()
    public ResponseEntity<Location> add(@RequestBody Location location) {
        return locationServic.addLocation(location);
    }

    @PutMapping("{accountId}")
    public ResponseEntity<Location> update(@PathVariable("accountId") Long accountId,
                                          @RequestBody Location location) {

        return locationServic.updateUserById(accountId, location);
    }



}
