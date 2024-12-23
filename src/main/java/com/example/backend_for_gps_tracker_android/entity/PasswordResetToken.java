package com.example.backend_for_gps_tracker_android.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Класс, представляющий токен сброса пароля.
 * <p>
 * Этот класс хранит информацию о токене, который используется для
 * сброса пароля пользователя. Он связывает токен с конкретным пользователем
 * и содержит информацию о времени действия токена.
 * </p>
 *
 * @see User
 */
@Entity
@Table(name = "tokens")
@Data
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Строковое представление токена сброса пароля.
     * <p>
     * Используется для идентификации запроса на сброс пароля.
     * </p>
     */
    private String token;

    /**
     * Связанный пользователь.
     * <p>
     * Связь "один к одному" с сущностью {@link User}.
     * Указывает на пользователя, для которого предназначен токен сброса пароля.
     * </p>
     */
    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    /**
     * Время истечения действия токена.
     * <p>
     * Указывает, когда токен становится недействительным.
     * </p>
     */
    private LocalDateTime expiryDate;
}