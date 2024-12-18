package com.example.OneBillion.Controller;


import com.example.OneBillion.Model.Customer;;
//import com.example.OneBillion.Service.RegistrationService;
import com.example.OneBillion.Model.Round;
import com.example.OneBillion.Model.RoundStatus;
import com.example.OneBillion.Repository.CustomerRepo;
import com.example.OneBillion.Repository.RoundRepository;
import com.example.OneBillion.Service.ContentService;
import com.example.OneBillion.Service.OtpService;
import com.example.OneBillion.Service.RoundService;
import com.example.OneBillion.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private ContentService contentService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoundService roundService;

    @Autowired
    private RoundRepository roundRepository;


    @GetMapping("/usr")
    public String userDashboard(Model model, @RequestParam(required = false) String email) {
        List<Round> rounds = roundService.getAllRounds();

        // Iterate through rounds and set status based on close time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for (Round round : rounds) {
            // Assuming 'getRoundCloseTime' returns a LocalDateTime
            LocalDateTime closeTime = round.getRoundCloseTime();  // This should be a method that returns LocalDateTime

            if (closeTime != null) {
                // If close time is in the future
                if (closeTime.isAfter(LocalDateTime.now())) {
                    round.setStatus(RoundStatus.UPCOMING);  // Round is upcoming
                }
                // If close time is in the past
                else if (closeTime.isBefore(LocalDateTime.now())) {
                    // If time has passed but we are within the active round window (3 minutes after close time)
                    if (closeTime.plusMinutes(3).isAfter(LocalDateTime.now())) {  // Active for 3 minutes after the close time
                        round.setStatus(RoundStatus.ACTIVE);  // Round is active
                    } else {
                        round.setStatus(RoundStatus.CLOSED);  // Round has ended, more than 3 minutes after close time
                    }
                }
                // If close time is now (this case can overlap with the active round scenario)
                else {
                    round.setStatus(RoundStatus.ACTIVE);  // Round is considered active at the moment
                }

                // Format the close time for display
                String formattedCloseTime = closeTime.format(formatter);
                round.setFormattedCloseTime(formattedCloseTime); // Assuming 'setFormattedCloseTime' exists
            } else {
                // Handle case where closeTime is null, if necessary
                round.setStatus(RoundStatus.UPCOMING);  // Default to "UPCOMING" or adjust based on requirements
            }
        }

        // Add rounds to the model for Thymeleaf rendering
        model.addAttribute("rounds", rounds);

        // Optionally, you can add the formatted close time of a specific round to the model
        if (!rounds.isEmpty()) {
            model.addAttribute("formattedCloseTime", rounds.get(0).getFormattedCloseTime());
        }

        return "userdashboard1";
    }

    @Autowired
    private OtpService otpService;

    // Show OTP Page
    @GetMapping("/otp")
    public String showOtpPage(Model model) {
        model.addAttribute("userIdentifier", ""); // Initialize empty field for the userIdentifier
        return "otp";
    }

    @PostMapping("/send")
    public ResponseEntity<Map<String, Object>> sendOtp(@RequestBody Map<String, String> request) {
        String userIdentifier = request.get("userIdentifier");

        // Generate OTP and send it to the user (your business logic)
        boolean otpSent = otpService.sendOtp(userIdentifier); // Implement your OTP sending logic here

        Map<String, Object> response = new HashMap<>();
        if (otpSent) {
            response.put("success", true);
        } else {
            response.put("success", false);
        }
        return ResponseEntity.ok(response);
    }

    // Resend OTP
    @PostMapping("/resend")
    public String resendOtp(@RequestParam String userIdentifier, Model model) {
        otpService.resendOtp(userIdentifier);
        model.addAttribute("message", "OTP resent successfully to " + userIdentifier);
        model.addAttribute("userIdentifier", userIdentifier); // Pass the userIdentifier back to the page
        return "otp";
    }

    // Validate OTP
    @PostMapping("/validate")
    public String validateOtp(@RequestParam String userIdentifier, @RequestParam String inputOtp, Model model) {
        boolean isValid = otpService.validateOtp(userIdentifier, inputOtp);

        if (isValid) {
            model.addAttribute("message", "OTP verified successfully!");
        } else {
            model.addAttribute("error", "Invalid Otp. Please try again.");
            model.addAttribute("userIdentifier", userIdentifier);
            return "otp";// Pass userIdentifier to enable resend
        }
        model.addAttribute("isVerified", true);
        return "userdashboard";
    }


    //participate round

    @PostMapping("/participateRound")
    public String participateInRound(@RequestParam Long roundId, Model model) {
        // Business logic to handle participation
        boolean success = roundService.participateInRound(roundId, userService.getLoggedInUserId());

        if (success) {
            model.addAttribute("message", "Successfully participated in the round!");
        } else {
            model.addAttribute("error", "Participation failed. Check the round status.");
        }

        return "redirect:/user/usr";
    }

    @GetMapping("/buy")
    public String buy()
    {
        return "buy";
    }


}


