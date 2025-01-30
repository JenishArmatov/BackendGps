package com.example.backend_for_gps_tracker_android.service.impl;

import com.example.backend_for_gps_tracker_android.dto.Response;
import com.example.backend_for_gps_tracker_android.dto.RoleDto;
import com.example.backend_for_gps_tracker_android.entity.Role;
import com.example.backend_for_gps_tracker_android.entity.User;
import com.example.backend_for_gps_tracker_android.repository.RoleRepository;
import com.example.backend_for_gps_tracker_android.service.RoleService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * Реализация сервиса для работы с ролями.
 *
 * Этот класс предоставляет функциональность для создания, обновления, удаления и получения ролей.
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public void create(RoleDto roleDto) {
        if (roleDto.getRoleNames() == null || roleDto.getRoleNames().isEmpty()) {
            throw new IllegalArgumentException("Role names cannot be null or empty");
        }

        for (String roleName : roleDto.getRoleNames()) {
            String normalizedRoleName = roleName.trim().toUpperCase();

            // Проверяем, существует ли уже такая роль
            if (roleRepository.findByRoleName(normalizedRoleName).isPresent()) {
                continue; // Пропускаем существующую роль
            }

            // Создаем новую роль
            Role role = Role.builder()
                    .roleName(normalizedRoleName)
                    .build();

            roleRepository.save(role);
        }
    }


    @Override
    public Response<String> update(Role role) {
        Role existingRole = roleRepository.findById(role.getId())
                .orElseThrow(() -> new RuntimeException("Role not found with ID: " + role.getId()));
        if (roleRepository.findByRoleName(role.getRoleName()).isPresent() &&
                !existingRole.getRoleName().equalsIgnoreCase(role.getRoleName())) {
            throw new RuntimeException("Role already exists with name: " + role.getRoleName());
        }
        existingRole.setRoleName(role.getRoleName().toUpperCase());
        roleRepository.save(existingRole);
        return new Response<>("Role updated successfully", "Success");
    }


    @Override
    public Role getRoleByName(String roleName) {
        return roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new EntityNotFoundException("Role not found"));
    }


    @Override
    public Response<List<Role>> getAll() {
        List<Role> roles = roleRepository.findAll();
        return new Response<>("All roles retrieved successfully", roles);
    }


    @Override
    public void delete(Role role) {
        roleRepository.delete(role);
    }


    public Role getById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with ID: " + id));
    }
}