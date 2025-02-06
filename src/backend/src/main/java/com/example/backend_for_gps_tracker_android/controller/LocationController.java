package com.example.backend_for_gps_tracker_android.controller;


import com.example.backend_for_gps_tracker_android.dto.LocationDto;
import com.example.backend_for_gps_tracker_android.dto.LocationRequest;
import com.example.backend_for_gps_tracker_android.entity.Location;
import com.example.backend_for_gps_tracker_android.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping
    public ResponseEntity<LocationDto> saveLocation(@RequestBody LocationDto locationDto) {
        LocationDto createdLocation = locationService.saveLocation(locationDto);
        return ResponseEntity.ok(createdLocation);
    }

    @GetMapping
    public ResponseEntity<List<LocationDto>> getLocationsByUserId(@RequestBody LocationRequest request) {
        List<LocationDto> locations = locationService.getLocationsByUserIdAndDate(request);
        return ResponseEntity.ok(locations);
    }
}