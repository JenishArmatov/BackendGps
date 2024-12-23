package com.example.backend_for_gps_tracker_android.service;

import com.example.backend_for_gps_tracker_android.entity.Image;
import org.springframework.web.multipart.MultipartFile;


public interface ImageService {
    Image saveImage(MultipartFile file) throws Exception;
    byte[] getImage() throws Exception;
    void deleteImage() throws Exception;
}