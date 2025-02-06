package com.example.backend_for_gps_tracker_android.service.impl;

import com.example.backend_for_gps_tracker_android.dto.LocationDto;
import com.example.backend_for_gps_tracker_android.dto.LocationRequest;
import com.example.backend_for_gps_tracker_android.entity.Location;
import com.example.backend_for_gps_tracker_android.entity.User;
import com.example.backend_for_gps_tracker_android.mapper.LocationMapper;
import com.example.backend_for_gps_tracker_android.repository.LocationRepository;
import com.example.backend_for_gps_tracker_android.repository.UserRepository;
import com.example.backend_for_gps_tracker_android.service.LocationService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final UserRepository userRepository;
    private final LocationMapper locationMapper;

    public LocationServiceImpl(LocationRepository locationRepository, UserRepository userRepository, LocationMapper locationMapper) {
        this.locationRepository = locationRepository;
        this.userRepository = userRepository;
        this.locationMapper = locationMapper;
    }

    @Override
    public LocationDto saveLocation(LocationDto locationDto) {
        User user = userRepository.findById(locationDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + locationDto.getUserId()));

        Location location = locationMapper.toEntity(locationDto);
        location.setUser(user);
        Location savedLocation = locationRepository.save(location);
        return locationMapper.toDto(savedLocation);
    }

    @Override
    public List<LocationDto> getLocationsByUserIdAndDate(LocationRequest request) {
        List<Location> locations = locationRepository.findByUserIdAndDate(request.getUserId(), request.getDate());
        return locations.stream()
                .map(locationMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteOldLocationsByUserId(Long userId) {
        LocalDateTime threshold = LocalDateTime.now().minusDays(30);
        locationRepository.deleteOldLocations(userId, threshold);
    }

    // Автоматическая очистка старых данных раз в месяц
    @Scheduled(cron = "0 0 0 1 * ?") // Запускается 1-го числа каждого месяца в 00:00
    public void cleanupOldLocations() {
        LocalDateTime threshold = LocalDateTime.now().minusDays(30);
        locationRepository.deleteOldLocations(threshold);
        System.out.println("Старые локации удалены.");
    }
}
