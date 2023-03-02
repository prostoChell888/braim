package com.example.if_else.Controllers;


import com.example.if_else.Models.Account;
import com.example.if_else.Models.Location;
import com.example.if_else.Servises.LocationServic;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("locations")
public class LocationController {

    private final LocationServic locationServic;

    public LocationController(LocationServic locationServices) {
        this.locationServic = locationServices;
    }

    //todo проверить на корректность
    @GetMapping("{pointId}")
    public ResponseEntity<Location> show(@PathVariable("pointId") Long pointId) {
        return locationServic.getLacateById(pointId);
    }
}
