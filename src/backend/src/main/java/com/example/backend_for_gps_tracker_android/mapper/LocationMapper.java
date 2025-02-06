package com.example.backend_for_gps_tracker_android.mapper;

import com.example.backend_for_gps_tracker_android.dto.LocationDto;
import com.example.backend_for_gps_tracker_android.entity.Location;
import org.springframework.stereotype.Component;

@Component
public class LocationMapper {

    public LocationDto toDto(Location location) {
        LocationDto dto = new LocationDto();
        dto.setId(location.getId());
        dto.setLatitude(location.getLatitude());
        dto.setLongitude(location.getLongitude());
        dto.setTimestamp(location.getTimestamp());
        dto.setDistance(location.getDistance());
        dto.setUserId(location.getUser().getId());
        return dto;
    }

    public Location toEntity(LocationDto dto) {
        Location location = new Location();
        location.setId(dto.getId());
        location.setLatitude(dto.getLatitude());
        location.setLongitude(dto.getLongitude());
        location.setTimestamp(dto.getTimestamp());
        location.setDistance(dto.getDistance());
        return location;
    }
}