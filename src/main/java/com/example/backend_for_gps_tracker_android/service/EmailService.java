package com.example.backend_for_gps_tracker_android.service;


import com.example.backend_for_gps_tracker_android.utils.EmailDetails;

public interface EmailService {

    String sendSimpleMail(EmailDetails details);

}