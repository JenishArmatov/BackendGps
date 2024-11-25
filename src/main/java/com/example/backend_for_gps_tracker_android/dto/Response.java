package com.example.backend_for_gps_tracker_android.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO (Data Transfer Object) для представления ответа на запрос.
 *
 * @param <T> тип данных, который будет возвращен в ответе.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response<T> {

    /**
     * Сообщение, описывающее результат запроса.
     */
    private String message;

    /**
     * Данные, возвращаемые в ответе.
     */
    private T data;
}