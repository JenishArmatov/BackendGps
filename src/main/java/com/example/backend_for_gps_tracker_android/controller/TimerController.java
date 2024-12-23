package com.example.backend_for_gps_tracker_android.controller;


import com.example.backend_for_gps_tracker_android.service.impl.TimerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/timer")
@RequiredArgsConstructor
public class TimerController {
    private final TimerService timerService;

    @PostMapping(value = "/start")
    public ResponseEntity<Void> startTimer() {
        timerService.startTimer();
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }
    @PutMapping(value = "/reset")
    public ResponseEntity<Void> resetTimer() {
        timerService.resetTimer();
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @GetMapping("/is-runnig")
    public ResponseEntity<Boolean> isRunning() {
        return ResponseEntity.ok(timerService.isRunning());
    }

    @GetMapping("/seconds")
    public ResponseEntity<Long> getSeconds() {
        return ResponseEntity.ok(timerService.getSeconds());
    }

    @DeleteMapping
    public ResponseEntity<Void> stopTimer() {
       timerService.stopTimer();
       timerService.shutdown();
       return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}