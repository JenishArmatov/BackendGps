package com.example.backend_for_gps_tracker_android.entity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double latitude; // Широта
    private Double longitude; // Долгота
    private LocalDateTime timestamp; // Время фиксации
    @Column(name = "distance")
    private Long distance;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // Связь с пользователем
    private User user;
}