package com.example.backend_for_gps_tracker_android.service;

import com.example.backend_for_gps_tracker_android.dto.TaskDto;

import java.util.List;

public interface TaskService {
    TaskDto createTask(TaskDto taskDto);
    TaskDto getTaskById(Long id);
    List<TaskDto> getTasksByUserId(Long userId);
    TaskDto updateTask(Long id, TaskDto taskDto);
    void deleteTask(Long id);
}