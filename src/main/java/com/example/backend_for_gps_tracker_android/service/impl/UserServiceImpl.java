package com.example.backend_for_gps_tracker_android.service.impl;

import com.example.backend_for_gps_tracker_android.dto.Response;
import com.example.backend_for_gps_tracker_android.dto.UserDto;
import com.example.backend_for_gps_tracker_android.entity.Role;
import com.example.backend_for_gps_tracker_android.entity.User;
import com.example.backend_for_gps_tracker_android.mapper.UserMapper;
import com.example.backend_for_gps_tracker_android.repository.UserRepository;
import com.example.backend_for_gps_tracker_android.service.RoleService;
import com.example.backend_for_gps_tracker_android.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;
import java.util.Set;
/**
 * Реализация сервиса для работы с пользователями.
 * Этот класс предоставляет функциональность для создания, обновления, удаления и получения пользователей,
 * а также управления их ролями.
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleService roleService;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, RoleService roleService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.roleService = roleService;
    }


    @Override
    public User create(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("A user with this name already exists");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("A user with this email already exists");
        }
        log.info("Create user with username: {}", user.getUsername());
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public UserDto getUserResponseById(Long id) {
        User user = getById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        UserDto userDto = userMapper.toUserDto(user);
        return userDto;
    }

    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public User getCurrentUser() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username);
    }


    @Deprecated
    public User getAdmin() {
        User user = getCurrentUser();
        Set<Role> roles = user.getRoles();
        Role adminRole = roleService.getRoleByName("ADMIN");
        roles.add(adminRole);
        user.setRoles(roles);
        userRepository.save(user);
        return user;
    }

    @Override
    public Response<List<UserDto>> getAllUsersWithPagination(int firstPage, int pageSize, String[] sort) {
        Sort.Direction direction = Sort.Direction.fromString(sort[1]);
        Pageable pageable = PageRequest.of(firstPage, pageSize, Sort.by(direction, sort[0]));
        Page<User> pages = userRepository.findAll(pageable);
        List<User> users = pages.getContent();
        List<UserDto> usersDto = userMapper.toUserDtoList(users);
        return new Response<>("All users retrieved successfully", usersDto);
    }


    @Override
    public Response<UserDto> setRole(String roleName, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Role role = roleService.getRoleByName(roleName);
        if (role == null) {
            throw new RuntimeException("Role not found: " + roleName);
        }
        Set<Role> currentRoles = user.getRoles();
        currentRoles.add(role);
        user.setRoles(currentRoles);
        userRepository.save(user);
        return new Response<>("User", userMapper.toUserDto(user));
    }


    @Override
    public Response<UserDto> updateUser(Long id, UserDto userDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        User newUser = userMapper.updateUser(user, userDto);
        userRepository.save(newUser);
        UserDto newUserDto = userMapper.toUserDto(newUser);
        return new Response<>("User updated successfully", newUserDto);
    }


    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        userRepository.delete(user);
    }


    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}