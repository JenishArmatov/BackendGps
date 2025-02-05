package com.example.backend_for_gps_tracker_android.entity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String routName; // Название адреса
    private String description; // Описание
    private LocalDateTime timestamp; // Время фиксации
    private Long distance; // Дистанция маршрута

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // Связь с пользователем
    private User user;
}