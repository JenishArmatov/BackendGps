package com.example.backend_for_gps_tracker_android.dto;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class LocationDto {
    private Long id;

    private Double latitude; // Широта
    private Double longitude; // Долгота
    private LocalDateTime timestamp; // Время фиксации
    private Long distance;
    private Long userId;
}
