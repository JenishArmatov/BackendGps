package com.example.backend_for_gps_tracker_android.service.impl;

import com.example.backend_for_gps_tracker_android.dto.TaskDto;
import com.example.backend_for_gps_tracker_android.entity.Task;
import com.example.backend_for_gps_tracker_android.mapper.TaskMapper;
import com.example.backend_for_gps_tracker_android.repository.TaskRepository;
import com.example.backend_for_gps_tracker_android.service.TaskService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    @Override
    public TaskDto createTask(TaskDto taskDto) {
        Task task = taskMapper.toEntity(taskDto);
        Task savedTask = taskRepository.save(task);
        return taskMapper.toDto(savedTask);
    }

    @Override
    public TaskDto getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with ID: " + id));
        return taskMapper.toDto(task);
    }

    @Override
    public List<TaskDto> getTasksByUserId(Long userId) {
        return taskRepository.findByUserId(userId)
                .stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public TaskDto updateTask(Long id, TaskDto taskDto) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with ID: " + id));

        task.setRoutName(taskDto.getRoutName());
        task.setDescription(taskDto.getDescription());
        task.setTimestamp(taskDto.getTimestamp());
        task.setDistance(taskDto.getDistance());

        Task updatedTask = taskRepository.save(task);
        return taskMapper.toDto(updatedTask);
    }

    @Override
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new RuntimeException("Task not found with ID: " + id);
        }
        taskRepository.deleteById(id);
    }
}
