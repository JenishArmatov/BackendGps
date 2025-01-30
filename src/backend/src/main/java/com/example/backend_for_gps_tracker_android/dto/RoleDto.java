package com.example.backend_for_gps_tracker_android.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO (Data Transfer Object) для представления роли пользователя.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {

    private Long id;

    private List<String> roleNames;

    private Long userId;
}