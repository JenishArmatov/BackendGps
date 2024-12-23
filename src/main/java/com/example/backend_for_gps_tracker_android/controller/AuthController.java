package com.example.backend_for_gps_tracker_android.controller;


import com.example.backend_for_gps_tracker_android.dto.JwtAuthenticationResponse;
import com.example.backend_for_gps_tracker_android.dto.SignInRequest;
import com.example.backend_for_gps_tracker_android.dto.SignUpRequest;
import com.example.backend_for_gps_tracker_android.service.PasswordResetService;
import com.example.backend_for_gps_tracker_android.service.impl.AuthenticationServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * AuthController - это REST-контроллер, который управляет процессами аутентификации
 * и регистрации пользователей в системе. Он предоставляет конечные точки для регистрации,
 * входа в систему, запроса восстановления пароля и сброса пароля.
 *
 * <p>Контроллер использует {@link AuthenticationServiceImpl} для выполнения
 * бизнес-логики аутентификации и {@link PasswordResetService} для управления
 * восстановлением пароля.</p>
 */

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/auth")
@Slf4j
public class AuthController {
    private final AuthenticationServiceImpl authenticationService;
    private final PasswordResetService passwordResetService;

    @PostMapping(value = "/sign-up")
    public ResponseEntity<JwtAuthenticationResponse> signUp(@RequestBody SignUpRequest request) {
        log.info("[#signUp] is calling");
        try {
            JwtAuthenticationResponse response = authenticationService.signUp(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    @PostMapping(value = "/sign-in")
    public ResponseEntity<JwtAuthenticationResponse> signIn(@RequestBody @Valid SignInRequest request) {
        log.info("[#signIn] is calling");
        try {
            JwtAuthenticationResponse response = authenticationService.signIn(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        try {
            passwordResetService.sendResetPasswordEmail(email);
            return ResponseEntity.ok("Ссылка для сброса пароля была отправлена на вашу электронную почту.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        try {
            passwordResetService.resetPassword(token, newPassword);
            return ResponseEntity.ok("Пароль успешно сброшен.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}