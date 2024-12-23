package com.example.backend_for_gps_tracker_android.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO (Data Transfer Object) для представления ответа аутентификации с использованием JWT.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthenticationResponse {

    /**
     * Токен JWT, выданный при успешной аутентификации.
     */
    private String token;

    /**
     * Уникальный идентификатор пользователя, которому принадлежит токен.
     */
    private Long userId;
}