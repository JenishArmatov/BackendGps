package com.example.backend_for_gps_tracker_android.service.impl;

import com.example.backend_for_gps_tracker_android.entity.User;
import com.example.backend_for_gps_tracker_android.repository.UserRepository;
import com.example.backend_for_gps_tracker_android.service.UserService;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
public class TimerService {
    private boolean running = false;
    private long startTime = 0;
    private long accumulatedTime = 0; // Время, накопленное до последнего остановки

    private final UserService userService;
    private final UserRepository userRepository;

    public TimerService(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    /**
     * Запускает таймер. Если таймер уже запущен, ничего не делает.
     */
    public void startTimer() {
        if (!running) {
            running = true;
            startTime = System.currentTimeMillis();
        }
    }

    /**
     * Останавливает таймер и сохраняет накопленное время.
     */
    public void stopTimer() {
        if (running) {
            accumulatedTime += System.currentTimeMillis() - startTime;
            running = false;
        }
    }

    /**
     * Сбрасывает таймер до нуля.
     */
    public void resetTimer() {
        stopTimer();
        accumulatedTime = 0;
        System.out.println("Timer reset to 00:00:00");
    }

    /**
     * Вычисляет текущее время таймера в секундах.
     *
     * @return Общее время в секундах.
     */
    public long getSeconds() {
        if (running) {
            return (accumulatedTime + (System.currentTimeMillis() - startTime)) / 1000;
        }
        return accumulatedTime / 1000;
    }

    /**
     * Возвращает текущее время таймера в формате HH:mm:ss.
     *
     * @return Форматированное строковое представление времени.
     */
    public String getFormattedTime() {
        long totalSeconds = getSeconds();
        long hours = totalSeconds / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    /**
     * Завершает работу таймера, сохраняет общее время пользователя и сбрасывает таймер.
     */
    public void shutdown() {
        stopTimer();

        // Сохранение времени в базе данных
        User user = userService.getCurrentUser();
        Long lastSeconds = user.getWorkTime();
        user.setWorkTime(lastSeconds + getSeconds());
        userRepository.save(user);

        System.out.println("Total time saved: " + user.getWorkTime());
        resetTimer();
        System.out.println("TimerService stopped.");
    }


}
