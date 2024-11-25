package com.example.backend_for_gps_tracker_android.repository;

import com.example.backend_for_gps_tracker_android.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}