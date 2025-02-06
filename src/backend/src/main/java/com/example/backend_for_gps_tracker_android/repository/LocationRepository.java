package com.example.backend_for_gps_tracker_android.repository;

import com.example.backend_for_gps_tracker_android.entity.Location;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    // Найти локации пользователя за определённый день
    @Query("SELECT l FROM Location l WHERE l.user.id = :userId AND DATE(l.timestamp) = :date")
    List<Location> findByUserIdAndDate(@Param("userId") Long userId, @Param("date") LocalDate date);

    // Удалить локации пользователя старше 30 дней
    @Modifying
    @Transactional
    @Query("DELETE FROM Location l WHERE l.user.id = :userId AND l.timestamp < :threshold")
    void deleteOldLocations(@Param("userId") Long userId, @Param("threshold") LocalDateTime threshold);
    @Modifying
    @Transactional
    @Query("DELETE FROM Location l WHERE l.timestamp < :threshold")
    void deleteOldLocations(@Param("threshold") LocalDateTime threshold);
}