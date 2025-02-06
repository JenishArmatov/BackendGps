package com.example.backend_for_gps_tracker_android.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TaskDto {
    private Long id;
    private String creatorName;
    private String routName;
    private String description;
    private LocalDateTime timestamp;
    private Long distance;
    private Long userId; // ID пользователя, а не объект User
}
