package com.example.backend_for_gps_tracker_android.service.impl;

import com.example.backend_for_gps_tracker_android.service.EmailService;
import com.example.backend_for_gps_tracker_android.utils.EmailDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.stereotype.Service;
/**
 * Реализация сервиса для отправки электронных писем.
 *
 * Этот класс предоставляет функциональность для отправки простых электронных писем
 * с использованием SMTP-сервера.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    /**
     * Отправка простого электронного письма.
     *
     * @param details объект, содержащий информацию о получателе, теме и содержимом письма.
     * @return строка с сообщением об успехе или ошибке.
     */
    public String sendSimpleMail(EmailDetails details) {
        log.info("[#sendSimpleMail] to: {}", details.getRecipient());
        // Попытка отправить электронное письмо
        try {
            // Создание простого сообщения электронной почты
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            // Установка необходимых данных
            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getRecipient());
            mailMessage.setText(details.getMessageBody());
            mailMessage.setSubject(details.getSubject());

            // Отправка письма
            javaMailSender.send(mailMessage);
            return "Mail Sent Successfully...";
        }

        // Обработка исключений
        catch (Exception e) {
            log.error("Error while sending mail: {}", e.getMessage());
            return "Error while Sending Mail";
        }
    }
}