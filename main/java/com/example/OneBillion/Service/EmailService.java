package com.example.OneBillion.Service;


import org.springframework.stereotype.Service;

@Service
public class EmailService {

    public void sendOtp(String email, String otp) {
        // Mock email sending
        System.out.println("Sending OTP to " + email + ": " + otp);
    }
}

