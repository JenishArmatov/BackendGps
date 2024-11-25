package com.example.backend_for_gps_tracker_android.service;
import com.example.backend_for_gps_tracker_android.dto.LocationDto;
import com.example.backend_for_gps_tracker_android.entity.Location;

import java.util.List;

public interface LocationService {
    LocationDto saveLocation(LocationDto locationDto);
    List<Location> getLocationsByUserId(Long userId);
    void deleteAllLocationsByUserId(Long userId);
}