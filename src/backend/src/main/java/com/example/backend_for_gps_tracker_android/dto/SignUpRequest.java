package com.example.backend_for_gps_tracker_android.dto;

import com.example.backend_for_gps_tracker_android.entity.Role;
import lombok.Data;

import java.util.Set;

/**
 * DTO (Data Transfer Object) для запроса на регистрацию пользователя.
 */
@Data
public class SignUpRequest {

    private String username;

    private String firstName;

    private String lastName;

    private String middleName;

    private String phone;

    private String email;

    private String password;

    private Set<Role> roles;
}