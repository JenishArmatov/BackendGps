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

    /**
     * Регистрация нового пользователя.
     *
     * @param request объект запроса на регистрацию, содержащий данные пользователя
     * @return ResponseEntity с JWT-ответом, содержащим информацию об аутентификации
     */
//    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/sign-up")
    public ResponseEntity<JwtAuthenticationResponse> signUp(@RequestBody SignUpRequest request) {
        log.info("[#signUp] is calling");
        JwtAuthenticationResponse response = authenticationService.signUp(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Вход пользователя в систему.
     *
     * @param request объект запроса на вход, содержащий данные для аутентификации
     * @return ResponseEntity с JWT-ответом, содержащим информацию об аутентификации
     */

    @PostMapping(value = "/sign-in")
    public ResponseEntity<JwtAuthenticationResponse> signIn(@RequestBody @Valid SignInRequest request) {
        log.info("[#signIn] is calling");
        JwtAuthenticationResponse response = authenticationService.signIn(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Запрос на восстановление пароля.
     *
     * @param email адрес электронной почты пользователя, которому будет отправлена ссылка
     *              для сброса пароля
     * @return ResponseEntity с сообщением о статусе запроса
     */

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        passwordResetService.sendResetPasswordEmail(email);
        return ResponseEntity.ok("Ссылка для сброса пароля была отправлена на вашу электронную почту.");
    }

    /**
     * Сброс пароля пользователя.
     *
     * @param token токен для сброса пароля
     * @param newPassword новый пароль, который необходимо установить
     * @return ResponseEntity с сообщением о статусе сброса пароля
     */


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