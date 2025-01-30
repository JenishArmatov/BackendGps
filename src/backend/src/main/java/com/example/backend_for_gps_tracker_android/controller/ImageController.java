package com.example.backend_for_gps_tracker_android.controller;

import com.example.backend_for_gps_tracker_android.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<Void> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            imageService.saveImage(file);
        } catch (Exception e) {
            System.out.println(e.getMessage() + ": Cant save image");
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/user")
    public ResponseEntity<byte[]> getImage() {
        try {
            byte[] imageData = imageService.getImage();
            return ResponseEntity.ok(imageData);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage().getBytes());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<String> deleteImage(@PathVariable Long userId) {
        try {
            imageService.deleteImage();
            return ResponseEntity.ok("Image deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}