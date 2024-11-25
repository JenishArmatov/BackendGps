package com.example.backend_for_gps_tracker_android.service;

import com.example.backend_for_gps_tracker_android.dto.Response;
import com.example.backend_for_gps_tracker_android.dto.UserDto;
import com.example.backend_for_gps_tracker_android.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User create (User user);
    User getCurrentUser();
    Optional<User> getById(Long id);
    User getByUsername(String username);
    Response<UserDto> getUserResponseById(Long id);
    Response<List<UserDto>> getAllUsersWithPagination(int firstPage, int pageSize, String[] sort);
    Response<UserDto> setRole(String role, Long id);
    Response<UserDto> updateUser(Long id, UserDto userDto);
    void deleteUser(Long id);
}