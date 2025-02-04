package com.example.backend_for_gps_tracker_android.mapper;

import com.example.backend_for_gps_tracker_android.dto.LocationDto;
import com.example.backend_for_gps_tracker_android.dto.UserDto;
import com.example.backend_for_gps_tracker_android.entity.Location;
import com.example.backend_for_gps_tracker_android.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
/**
 * Mapper для преобразования между сущностью {@link User} и DTO {@link UserDto}.
 * <p>
 * Этот класс предоставляет методы для конвертации объектов {@link User} в {@link UserDto},
 * преобразования списков пользователей и обновления существующего пользователя на основе {@link UserDto}.
 * </p>
 */
@Component
@AllArgsConstructor
public class UserMapper {


    /**
     * Преобразует объект {@link User} в объект {@link UserDto}.
     *
     * @param user Сущность, которую нужно преобразовать. Может быть null.
     * @return Преобразованный объект {@link UserDto} или null, если входной объект user равен null.
     */
    public UserDto toUserDto(User user) {
        if (user == null) {
            return null;
        }

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());

        if (user.getFirstName() != null) {
            userDto.setFirstName(user.getFirstName());
        }
        if (user.getLastName() != null) {
            userDto.setLastName(user.getLastName());
        }
        if (user.getMiddleName() != null) {
            userDto.setMiddleName(user.getMiddleName());
        }
        if (user.getEmail() != null) {
            userDto.setEmail(user.getEmail());
        }
        if (user.getPhone() != null) {
            userDto.setPhone(user.getPhone());
        }
        if (user.getAvatar() != null && user.getAvatar().getId() != null) {
            userDto.setAvatarId(user.getAvatar().getId());
        }
        if (user.getWorkTime() != null) {
            userDto.setWorkTime(user.getWorkTime());
        }

        // Преобразование списка locations
        if (user.getLocations() != null) {
            List<LocationDto> locationDtos = user.getLocations().stream()
                    .map(location -> {
                        LocationDto locationDto = new LocationDto();
                        locationDto.setId(location.getId());
                        locationDto.setLatitude(location.getLatitude());
                        locationDto.setLongitude(location.getLongitude());
                        locationDto.setTimestamp(location.getTimestamp());
                        locationDto.setDistance(location.getDistance());
                        locationDto.setUserId(location.getUser().getId());
                        return locationDto;
                    })
                    .collect(Collectors.toList());
            userDto.setLocations(locationDtos);
        }
        userDto.setRole(user.getRoles().get(0).getRoleName());

        userDto.setActive(user.isActive());
        return userDto;
    }


    /**
     * Преобразует список объектов {@link User} в список объектов {@link UserDto}.
     *
     * @param users Список сущностей, которые нужно преобразовать. Может содержать null значения.
     * @return Список преобразованных объектов {@link UserDto}, игнорируя null значения.
     */
    public List<UserDto> toUserDtoList(List<User> users) {

        return users.stream()
                .map(this::toUserDto)
                .filter(Objects::nonNull) // Игнорировать null значения
                .collect(Collectors.toList());

    }

    /**
     * Обновляет сущность пользователя на основе {@link UserDto}.
     *
     * @param user   Сущность пользователя, которую нужно обновить.
     * @param userDto DTO, содержащий новые значения для обновления пользователя.
     * @return Обновленная сущность {@link User}.
     * @throws IllegalArgumentException Если user или userDto равны null.
     */
    public User updateUser(User user, UserDto userDto) {
        if (user == null || userDto == null) {
            throw new IllegalArgumentException("User or UserDto cannot be null");
        }

        // Обновляем простые поля
        if (userDto.getUsername() != null) {
            user.setUsername(userDto.getUsername());
        }
        if (userDto.getFirstName() != null) {
            user.setFirstName(userDto.getFirstName());
        }
        if (userDto.getLastName() != null) {
            user.setLastName(userDto.getLastName());
        }
        if (userDto.getMiddleName() != null) {
            user.setMiddleName(userDto.getMiddleName());
        }
        if (userDto.getPhone() != null) {
            user.setPhone(userDto.getPhone());
        }
        if (userDto.getEmail() != null) {
            user.setEmail(userDto.getEmail());
        }
        if (userDto.getWorkTime() != null) {
            user.setWorkTime(userDto.getWorkTime());
        }

        // Обновление списка locations
        if (userDto.getLocations() != null) {
            List<Location> updatedLocations = userDto.getLocations().stream()
                    .map(locationDto -> {
                        Location location = new Location();
                        location.setId(locationDto.getId());
                        location.setLatitude(locationDto.getLatitude());
                        location.setLongitude(locationDto.getLongitude());
                        location.setTimestamp(locationDto.getTimestamp());
                        location.setDistance(locationDto.getDistance());
                        location.setUser(user); // Устанавливаем связь с пользователем
                        return location;
                    })
                    .collect(Collectors.toList());
            user.setLocations(updatedLocations);
        }

        return user;
    }


}