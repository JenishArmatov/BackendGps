package com.example.backend_for_gps_tracker_android.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class LocationRequest {
    private Long userId;
    private LocalDate date; // Дата, за которую запрашиваем локацию
}
