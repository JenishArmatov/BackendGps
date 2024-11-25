package com.example.backend_for_gps_tracker_android.controller;

import com.example.backend_for_gps_tracker_android.dto.Response;
import com.example.backend_for_gps_tracker_android.dto.UserDto;
import com.example.backend_for_gps_tracker_android.service.impl.UserServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users")
@Slf4j
public class UserController {

    private final UserServiceImpl userService;


    @GetMapping(value = "/{id}")
    public ResponseEntity<Response<UserDto>> getById(@PathVariable Long id) {
        log.info("[#getUserById] is calling");

        try {
            Response<UserDto> response = userService.getUserResponseById(id);
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response<>(e.getMessage(), null));
        }
    }

    @GetMapping
    public ResponseEntity<Response<List<UserDto>>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "username,asc") String[] sort) {
        log.info("[#getAllUsers] is calling");
        Response<List<UserDto>> response = userService.getAllUsersWithPagination(page, size, sort);
        return ResponseEntity.ok(response);
    }

/*
  //  @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/{id}/role")
    public ResponseEntity<Response<UserDto>> setRole(@RequestParam String role, @PathVariable Long id) {
        log.info("[#setRole] is calling");
        Response<UserDto> response = userService.setRole(role, id);
        return ResponseEntity.ok(response);
    }
*/

    @PutMapping(value = "/{id}")
    public ResponseEntity<Response<UserDto>> update(@RequestBody UserDto userDto, @PathVariable Long id) {
        log.info("[#updateEmployee] is calling");
        Response<UserDto> response = userService.updateUser(id, userDto);
        return ResponseEntity.ok(response);
    }


    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Response<String>> delete(@PathVariable Long id) {
        log.info("[#delete] is calling");
        userService.deleteUser(id);
        return ResponseEntity.ok(new Response<>("User deleted successfully!", "ID: " + id));
    }
}