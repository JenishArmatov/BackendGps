package com.example.backend_for_gps_tracker_android.repository;

import com.example.backend_for_gps_tracker_android.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUserId(Long userId); // Найти все задачи по ID пользователя
}
