package com.example.backend_for_gps_tracker_android.dto;

import lombok.Data;

/**
 * DTO (Data Transfer Object) для запроса на вход в систему.
 */
@Data
public class SignInRequest {

    /**
     * Имя пользователя, используемое для аутентификации.
     */
    private String username;

    /**
     * Пароль пользователя для аутентификации.
     */
    private String password;

    @Override
    public String toString() {
        return "SignInRequest{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}