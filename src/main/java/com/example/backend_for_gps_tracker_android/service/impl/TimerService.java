package com.example.backend_for_gps_tracker_android.service.impl;

import com.example.backend_for_gps_tracker_android.entity.User;
import com.example.backend_for_gps_tracker_android.repository.UserRepository;
import com.example.backend_for_gps_tracker_android.service.UserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Data
@Service
public class TimerService {
    private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private Long seconds = 0l;
    private boolean running = false;
    private final UserService userService;
    private final UserRepository userRepository;

    private Runnable timerTask = () -> {
        if (running) {
            seconds++;
            Long hours = seconds / 3600;
            Long minutes = (seconds % 3600) / 60;
            Long secs = seconds % 60;
            System.out.printf("Timer: %02d:%02d:%02d%n", hours, minutes, secs);
        }
    };

    public TimerService(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    public void startTimer() {
        if (!running) {
            running = true;
            scheduler.scheduleAtFixedRate(timerTask, 0, 1, TimeUnit.SECONDS);
        }
    }

    public void stopTimer() {
        running = false;
    }

    public void resetTimer() {
        stopTimer();
        seconds = 0l;
        System.out.println("Timer reset to 00:00:00");
    }

    public void shutdown() {
        User user = userService.getCurrentUser();
        Long lastSeconds = user.getWorkTime();
        user.setWorkTime(lastSeconds + seconds);
        userRepository.save(user);
        System.out.println("Total time: " + userService.getCurrentUser().getWorkTime());

        scheduler.shutdown();
        resetTimer();
        scheduler = Executors.newSingleThreadScheduledExecutor();
        System.out.println("TimerService stopped.");
    }


}