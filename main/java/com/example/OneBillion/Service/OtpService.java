package com.example.OneBillion.Service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class OtpService {
    private final Map<String, OtpDetails> otpStore = new HashMap<>();

    // Send OTP and store with timestamp
    public boolean sendOtp(String userIdentifier) {
        String otp = generateOtp();
        otpStore.put(userIdentifier, new OtpDetails(otp, LocalDateTime.now()));
        System.out.println("OTP sent to " + userIdentifier + ": " + otp);
        return true;
    }

    // Resend OTP
    public void resendOtp(String userIdentifier) {
        sendOtp(userIdentifier); // Reuse the sendOtp method
    }

    // Validate OTP
    public boolean validateOtp(String userIdentifier, String inputOtp) {
        if (!otpStore.containsKey(userIdentifier)) {
            return false; // No OTP found for this user
        }

        OtpDetails otpDetails = otpStore.get(userIdentifier);
        LocalDateTime now = LocalDateTime.now();

        // Check if OTP has expired
        if (otpDetails.getTimestamp().plusSeconds(30).isBefore(now)) {
            otpStore.remove(userIdentifier); // Remove expired OTP
            return false;
        }

        // Check if OTP matches
        boolean isValid = otpDetails.getOtp().equals(inputOtp);
        if (isValid) {
            otpStore.remove(userIdentifier); // Invalidate OTP after successful validation
        }
        return isValid;
    }

    // Generate a random 6-digit OTP
    private String generateOtp() {
        return String.valueOf((int) (Math.random() * 900000) + 100000);
    }

    // Internal class to store OTP details
    private static class OtpDetails {
        private final String otp;
        private final LocalDateTime timestamp;

        public OtpDetails(String otp, LocalDateTime timestamp) {
            this.otp = otp;
            this.timestamp = timestamp;
        }

        public String getOtp() {
            return otp;
        }

        public LocalDateTime getTimestamp() {
            return timestamp;
        }
    }
}
