package com.example.backend_for_gps_tracker_android.service;


import com.example.backend_for_gps_tracker_android.dto.Response;
import com.example.backend_for_gps_tracker_android.dto.RoleDto;
import com.example.backend_for_gps_tracker_android.entity.Role;
import com.example.backend_for_gps_tracker_android.entity.User;

import java.util.List;

public interface RoleService {
    void create(RoleDto role);
    Response<String> update(Role role);
    Role getRoleByName(String roleName);
    Response<List<Role>> getAll();
    void delete(Role role);
}