package com.example.backend_for_gps_tracker_android.service.impl;

import com.example.backend_for_gps_tracker_android.dto.LocationDto;
import com.example.backend_for_gps_tracker_android.entity.Location;
import com.example.backend_for_gps_tracker_android.entity.User;
import com.example.backend_for_gps_tracker_android.repository.LocationRepository;
import com.example.backend_for_gps_tracker_android.service.LocationService;
import com.example.backend_for_gps_tracker_android.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;
    private final UserService userService;

    @Override
    public LocationDto saveLocation(LocationDto locationDto) {
        Location location = new Location();
        location.setLatitude(locationDto.getLatitude());
        location.setLongitude(locationDto.getLongitude());
        location.setDistance(locationDto.getDistance());
        location.setUser(userService.getCurrentUser());
        location.setTimestamp(LocalDateTime.now());
        locationRepository.save(location);
        return locationDto;
    }

    @Override
    public List<Location> getLocations() {
        User user = userService.getCurrentUser();
        return user.getLocations();
    }

    @Override
    public List<Location> getLocationsByUserId(Long userId) {
        User user = userService.getById(userId).orElseThrow(() ->
                new IllegalArgumentException("User not found with id: " + userId));
        return user.getLocations();
    }

    @Override
    public void deleteAllLocationsByUserId(Long userId) {
        User user = userService.getById(userId).orElseThrow(() ->
                new IllegalArgumentException("User not found with id: " + userId));
        user.setLocations(new ArrayList<>());
        locationRepository.deleteById(userId);
    }
}