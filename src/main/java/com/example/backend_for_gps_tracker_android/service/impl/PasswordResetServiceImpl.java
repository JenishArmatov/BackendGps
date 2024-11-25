package com.example.backend_for_gps_tracker_android.service.impl;

import com.example.backend_for_gps_tracker_android.entity.PasswordResetToken;
import com.example.backend_for_gps_tracker_android.entity.User;
import com.example.backend_for_gps_tracker_android.repository.PasswordResetTokenRepository;
import com.example.backend_for_gps_tracker_android.repository.UserRepository;
import com.example.backend_for_gps_tracker_android.service.EmailService;
import com.example.backend_for_gps_tracker_android.service.PasswordResetService;
import com.example.backend_for_gps_tracker_android.utils.EmailDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Реализация сервиса для сброса пароля.
 *
 * Этот класс предоставляет функциональность для отправки писем с временными паролями,
 * управления токенами сброса пароля и обновления паролей пользователей.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PasswordResetServiceImpl implements PasswordResetService {

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final EmailService emailService;

    /**
     * Отправка письма для сброса пароля на указанный адрес электронной почты.
     *
     * @param email адрес электронной почты пользователя.
     * @throws RuntimeException если пользователь с указанным адресом электронной почты не найден.
     */
    @Transactional
    public void sendResetPasswordEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Сначала удаляем старые токены в отдельной транзакции
        removeOldTokens(user);

        // Теперь создаем новый токен
        createNewTokenAndSendEmail(user);
    }

    /**
     * Удаление старых токенов для пользователя.
     *
     * @param user объект пользователя, для которого нужно удалить токены.
     */
    @Transactional
    public void removeOldTokens(User user) {
        log.info("delete user by id: {}", user.getId());
        passwordResetTokenRepository.deleteByUser(user);
        passwordResetTokenRepository.flush(); // Принудительно выполняем удаление до создания нового токена
    }

    /**
     * Создание нового токена и отправка письма пользователю.
     *
     * @param user объект пользователя, для которого нужно создать токен и отправить письмо.
     */
    @Transactional
    public void createNewTokenAndSendEmail(User user) {
        // Генерация нового токена
        String token = UUID.randomUUID().toString();

        // Сохранение токена в базе данных
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setUser(user);
        resetToken.setExpiryDate(LocalDateTime.now().plusHours(1)); // Токен действителен 1 час
        passwordResetTokenRepository.save(resetToken);

        // Отправка email
        String message = String.format(
                """
                        Hello, %s! This is your temporary password for logging into the application. \s
                        It is valid for 1 hour.\s
                        Please copy this password and use it when logging in. \s
                        Your temporary password:\s
                        %s""", user.getUsername(), resetToken.getToken());

        EmailDetails details = new EmailDetails();
        details.setRecipient(user.getEmail());
        details.setSubject("Password Reset Request");
        details.setMessageBody(message);

        emailService.sendSimpleMail(details);
    }

    /**
     * Проверка токена и сброс пароля пользователя.
     *
     * @param token токен сброса пароля.
     * @param newPassword новый пароль для пользователя.
     * @throws RuntimeException если токен недействителен или срок его действия истек.
     */
    @Transactional
    public void resetPassword(String token, String newPassword) {
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid token"));

        // Проверка срока действия токена
        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token has expired");
        }

        // Обновление пароля пользователя
        User user = resetToken.getUser();
        user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
        userRepository.save(user);

        // Удаление использованного токена
        passwordResetTokenRepository.delete(resetToken);
    }
}