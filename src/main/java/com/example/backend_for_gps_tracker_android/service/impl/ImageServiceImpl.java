package com.example.backend_for_gps_tracker_android.service.impl;

import com.example.backend_for_gps_tracker_android.entity.Image;
import com.example.backend_for_gps_tracker_android.entity.User;
import com.example.backend_for_gps_tracker_android.repository.ImageRepository;
import com.example.backend_for_gps_tracker_android.repository.UserRepository;
import com.example.backend_for_gps_tracker_android.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
@RequiredArgsConstructor
@Slf4j
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final UserRepository userRepository; // Assumes UserRepository exists

    @Override
    public Image saveImage(MultipartFile file, Long userId) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Image image = Image.builder()
                .fileName(file.getOriginalFilename())
                .contentType(file.getContentType())
                .data(file.getBytes())
                .build();

        if (user.getAvatar() != null) {
            imageRepository.delete(user.getAvatar());
        }

        user.setAvatar(image);
        image.setUser(user);

        userRepository.save(user); // Persist both user and image
        return image;
    }

    public byte[] getImage(Long userId) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (user.getAvatar() == null || user.getAvatar().getData() == null) {
            throw new IllegalArgumentException("User does not have an avatar");
        }

        // Создайте копию данных
        return user.getAvatar().getData();
    }
    @Override
    public void deleteImage(Long userId) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (user.getAvatar() == null) {
            throw new IllegalArgumentException("User does not have an avatar");
        }

        imageRepository.delete(user.getAvatar());
        user.setAvatar(null);
        userRepository.save(user);
    }
}