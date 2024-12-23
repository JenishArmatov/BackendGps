package com.example.backend_for_gps_tracker_android.controller;

import com.example.backend_for_gps_tracker_android.dto.Response;
import com.example.backend_for_gps_tracker_android.dto.RoleDto;
import com.example.backend_for_gps_tracker_android.entity.Role;
import com.example.backend_for_gps_tracker_android.entity.User;
import com.example.backend_for_gps_tracker_android.service.impl.RoleServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Контроллер для управления ролями.
 * Предоставляет RESTful API для создания, обновления, получения и удаления ролей.
 */
@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleServiceImpl roleService;


    @PostMapping
    public ResponseEntity<Response<String>> create(@RequestBody RoleDto role) {

        roleService.create(role);
        return ResponseEntity.ok(new Response<>("Role is created successfully", "Success"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<String>> update(@PathVariable Long id, @RequestBody Role role) {
        Role roleToUpdate = roleService.getById(id);
        if (roleToUpdate == null) {
            return ResponseEntity.badRequest().body(new Response<>("Invalid Role Data", "Error"));
        }
        Response<String> response = roleService.update(role);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Response<List<Role>>> getAll() {
        Response<List<Role>> response = roleService.getAll();
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<String>> delete(@PathVariable Long id) {
        Role role = roleService.getById(id);
        roleService.delete(role);
        return ResponseEntity.ok(new Response<>("Role deleted successfully!", "Success"));
    }
}