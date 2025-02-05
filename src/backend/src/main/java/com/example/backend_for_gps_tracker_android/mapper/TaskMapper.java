package com.example.backend_for_gps_tracker_android.mapper;

import com.example.backend_for_gps_tracker_android.dto.TaskDto;
import com.example.backend_for_gps_tracker_android.entity.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public TaskDto toDto(Task task) {
        TaskDto dto = new TaskDto();
        dto.setId(task.getId());
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
        task.setRoutName(dto.getRoutName());
        task.setDescription(dto.getDescription());
        task.setTimestamp(dto.getTimestamp());
        task.setDistance(dto.getDistance());

        // User устанавливается в сервисе перед сохранением в БД
        return task;
    }
}
