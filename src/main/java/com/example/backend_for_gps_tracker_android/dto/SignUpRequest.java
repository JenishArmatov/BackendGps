package com.example.backend_for_gps_tracker_android.dto;

import com.example.backend_for_gps_tracker_android.entity.Role;
import lombok.Data;

import java.util.Set;

/**
 * DTO (Data Transfer Object) для запроса на регистрацию пользователя.
 */
@Data
public class SignUpRequest {

    /**
     * Имя пользователя, используемое для аутентификации.
     */
    private String username;

    /**
     * Имя пользователя.
     */
    private String firstName;

    /**
     * Фамилия пользователя.
     */
    private String lastName;

    /**
     * Отчество пользователя.
     */
    private String middleName;

    /**
     * Номер телефона пользователя.
     */
    private String phone;

    /**
     * Электронная почта пользователя.
     */
    private String email;

    /**
     * Пароль пользователя для аутентификации.
     */
    private String password;
    /**
     * Множество ролей, присваиваемых пользователю.
     */
    private Set<Role> roles;
}