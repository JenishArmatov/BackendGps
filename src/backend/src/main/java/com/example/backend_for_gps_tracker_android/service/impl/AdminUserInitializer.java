package com.example.backend_for_gps_tracker_android.service.impl;

import com.example.backend_for_gps_tracker_android.entity.Role;
import com.example.backend_for_gps_tracker_android.entity.User;
import com.example.backend_for_gps_tracker_android.repository.RoleRepository;
import com.example.backend_for_gps_tracker_android.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class AdminUserInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) {
        System.out.println("=== AdminUserInitializer ЗАПУСКАЕТСЯ ===");

        createRoleIfNotExists("ROLE_ADMIN");
        createRoleIfNotExists("ROLE_USER");

        Optional<Role> adminRoleOpt = roleRepository.findByRoleName("ROLE_ADMIN");
        if (adminRoleOpt.isEmpty()) {
            System.out.println("=== ОШИБКА: Роль ROLE_ADMIN не найдена ===");
            return;
        }

        Role adminRole = adminRoleOpt.get();
        System.out.println("=== ROLE_ADMIN найдена ===");

        if (userRepository.findByUsername("admin").isEmpty()) {
            System.out.println("=== Администратор отсутствует, создаем... ===");

            User admin = User.builder()
                    .username("admin")
                    .firstName("Admin")
                    .lastName("User")
                    .phone("+1234567890")
                    .email("admin@example.com")
                    .password(passwordEncoder.encode("admin123"))
                    .isActive(true)
                    .roles(Set.of(adminRole))
                    .build();

            userRepository.save(admin);
            System.out.println("=== Администратор СОЗДАН ===");
        } else {
            System.out.println("=== Администратор УЖЕ СУЩЕСТВУЕТ ===");
        }
    }

    private void createRoleIfNotExists(String roleName) {
        Optional<Role> existingRole = roleRepository.findByRoleName(roleName);
        if (existingRole.isEmpty()) {
            Role newRole = new Role();
            newRole.setRoleName(roleName);
            roleRepository.save(newRole);
        }
    }
}
