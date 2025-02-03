package com.example.backend_for_gps_tracker_android.repository;

import com.example.backend_for_gps_tracker_android.entity.PasswordResetToken;
import com.example.backend_for_gps_tracker_android.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByToken(String token);
    void deleteByUser(User user);
}