package com.example.backend_for_gps_tracker_android.mapper;

import com.example.backend_for_gps_tracker_android.dto.TaskDto;
import com.example.backend_for_gps_tracker_android.entity.Task;
import com.example.backend_for_gps_tracker_android.entity.User;
import com.example.backend_for_gps_tracker_android.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TaskMapper {
    private final UserService userService;

    public TaskDto toDto(Task task) {
        TaskDto dto = new TaskDto();
        dto.setId(task.getId());
        dto.setTaskName(task.getTaskName());
        dto.setCreatorName(task.getCreatorName());
        dto.setRoutName(task.getRoutName());
        dto.setDescription(task.getDescription());
        dto.setTimestamp(task.getTimestamp());
        dto.setDistance(task.getDistance());
        dto.setUserId(task.getUser().getId());
        return dto;
    }

    public Task toEntity(TaskDto dto) {
        Task task = new Task();
        task.setId(dto.getId());
        task.setTaskName(dto.getTaskName());
        task.setCreatorName(dto.getCreatorName());
        task.setRoutName(dto.getRoutName());
        task.setDescription(dto.getDescription());
        task.setTimestamp(dto.getTimestamp());
        task.setDistance(dto.getDistance());
        task.setUser(userService.getById(dto.getUserId()).orElseThrow());

        // User устанавливается в сервисе перед сохранением в БД
        return task;
    }
}
