package com.example.if_else.Controllers;


import com.example.if_else.Models.Account;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("locations")
public class LocationController {

    @GetMapping("{pointId}")
    public ResponseEntity<Account> show(@PathVariable("pointId") Integer pointId) {
        return locationService.getLacateById(pointId);
    }
}
