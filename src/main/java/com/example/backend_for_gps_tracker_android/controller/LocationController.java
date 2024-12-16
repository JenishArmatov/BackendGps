package com.example.backend_for_gps_tracker_android.controller;


import com.example.backend_for_gps_tracker_android.dto.LocationDto;
import com.example.backend_for_gps_tracker_android.entity.Location;
import com.example.backend_for_gps_tracker_android.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
@RequiredArgsConstructor
public class LocationController {
    private final LocationService locationService;

    @PostMapping
    public ResponseEntity<LocationDto> saveLocation(
            @RequestBody LocationDto location) {
        LocationDto savedLocation = locationService.saveLocation(location);
        return ResponseEntity.ok(savedLocation);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Location>> getLocationsByUserId(@PathVariable Long userId) {
        List<Location> locations = locationService.getLocationsByUserId(userId);
        return ResponseEntity.ok(locations);
    }
    @GetMapping
    public ResponseEntity<List<Location>> getLocations() {
        List<Location> locations = locationService.getLocations();
        return ResponseEntity.ok(locations);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteLocation(@PathVariable Long userId) {
        try{
            locationService.deleteAllLocationsByUserId(userId);
            return ResponseEntity.ok("Deleted all locations");
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Can't delete location: " + e.getMessage());
        }
    }
}