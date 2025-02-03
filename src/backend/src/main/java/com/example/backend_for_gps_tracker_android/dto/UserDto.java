package com.example.backend_for_gps_tracker_android.dto;

import com.example.backend_for_gps_tracker_android.entity.Location;
import lombok.Data;
import java.util.List;

@Data
public class UserDto {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String middleName;
    private String phone;
    private String email;
    private Long workTime;
    private List<LocationDto> locations;
    private Long avatarId;
    private boolean isActive;
}