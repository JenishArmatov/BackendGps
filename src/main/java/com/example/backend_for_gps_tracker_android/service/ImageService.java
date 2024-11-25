package com.example.backend_for_gps_tracker_android.service;

import com.example.backend_for_gps_tracker_android.entity.Image;
import org.springframework.web.multipart.MultipartFile;


public interface ImageService {
    Image saveImage(MultipartFile file, Long userId) throws Exception;
    byte[] getImage(Long userId) throws Exception;
    void deleteImage(Long userId) throws Exception;
}