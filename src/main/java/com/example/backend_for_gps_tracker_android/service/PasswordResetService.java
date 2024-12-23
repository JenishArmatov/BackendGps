package com.example.backend_for_gps_tracker_android.service;

public interface PasswordResetService {
    void sendResetPasswordEmail(String email);
    void resetPassword(String token, String newPassword);
}