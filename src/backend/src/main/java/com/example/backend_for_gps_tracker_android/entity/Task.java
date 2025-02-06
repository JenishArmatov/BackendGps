package com.example.backend_for_gps_tracker_android.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;

@Entity
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotBlank(message = "Creator name is required")
    private String creatorName; // Имя создателя задачи

    @NotBlank(message = "Task name is required")
    private String taskName; // Название задачи

    @NotBlank(message = "Route name is required")
    private String routName; // Название адреса
    private String description; // Описание
    private LocalDateTime timestamp; // Время фиксации
    private Long distance; // Дистанция маршрута

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // Связь с пользователем
    private User user;

    @PrePersist
    public void setTimestamp() {
        this.timestamp = LocalDateTime.now();
    }
}