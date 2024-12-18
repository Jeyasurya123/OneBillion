package com.example.OneBillion.Controller;

import com.example.OneBillion.Model.Content;
import com.example.OneBillion.Model.Customer;
import com.example.OneBillion.Model.Round;
import com.example.OneBillion.Model.RoundStatus;
import com.example.OneBillion.Service.ContentService;
import com.example.OneBillion.Service.RoundService;
import com.example.OneBillion.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoundService roundService;

    // Display the admin dashboard with all rounds
    @GetMapping("/adm")
    public String adminDashboard(Model model) {
        // Fetch all rounds from the service
        List<Round> rounds = roundService.getAllRounds();
        model.addAttribute("rounds", rounds);

//        cms
        model.addAttribute("contents", contentService.getAllContent());
        return "admindashboard"; // Return the admin dashboard template
    }

    @GetMapping("/button")
    public String btn(Model model){
        List<Round> rounds = roundService.getAllRounds();
        model.addAttribute("rounds", rounds);
        return "admin_dashboard";
    }

    // Update the status of a round
    @PostMapping("/updateStatus")
    public String updateStatus(@RequestParam Long roundId, Model model, @RequestParam String status) {
        List<Round> rounds = roundService.getAllRounds();
        model.addAttribute("rounds", rounds);

        Round round = roundService.getRoundById(roundId);
        round.setStatus(RoundStatus.valueOf(status));
        roundService.save(round); // Save the updated round

        return "admin_dashboard"; // Refresh the page with updated rounds
    }

    @PostMapping("/chooseWinningNumbers")
    public String chooseWinningNumbers(@RequestParam Long roundId,
                                       @RequestParam String winningNumbers,
                                       Model model) {
        // Split and validate the winning numbers
        String[] numbersArray = winningNumbers.split(",");
        if (numbersArray.length != 7) {
            model.addAttribute("error", "Please provide exactly 7 winning numbers.");
            return "admin_dashboard"; // Return back to the admin page with an error
        }

        // Convert to a list of integers and validate the range
        List<Integer> numbers;
        try {
            numbers = Arrays.stream(numbersArray)
                    .map(String::trim)
                    .map(Integer::parseInt)
                    .toList();
        } catch (NumberFormatException e) {
            model.addAttribute("error", "All numbers must be integers.");
            return "admin_dashboard";
        }

        // Check if all numbers are between 0 and 49
        if (numbers.stream().anyMatch(num -> num < 0 || num > 49)) {
            model.addAttribute("error", "Each number must be between 0 and 49.");
            return "admin_dashboard";
        }

        // Check for duplicate numbers
        long uniqueCount = numbers.stream().distinct().count();
        if (uniqueCount != 7) {
            model.addAttribute("error", "Duplicate numbers are not allowed.");
            return "admin_dashboard";
        }

        // Save the winning numbers
        Round round = roundService.getRoundById(roundId);
        round.setWinningNumbers(winningNumbers);
        roundService.save(round);
        List<Round> rounds = roundService.getAllRounds();
        model.addAttribute("rounds", rounds);

        // Redirect back to the admin dashboard with success
        model.addAttribute("message", "Winning numbers set successfully.");
        return "admin_dashboard";
    }



    // Handle form submission for creating a new round
    @PostMapping("/createRound")
    public String createRound(Model model,@RequestParam double roundAmount, @RequestParam String roundCloseTime, @RequestParam String status) {
        // Create a new round with the given parameters
        Round round = new Round();
        round.setRoundAmount(roundAmount);
        round.setRoundCloseTime(LocalDateTime.parse(roundCloseTime));
        round.setStatus(RoundStatus.valueOf(status));
        List<Round> rounds = roundService.getAllRounds();
        model.addAttribute("rounds", rounds);

        roundService.save(round); // Save the new round to the database
        return "admindashboard"; // Redirect with success message
    }


    ///CMS

    @Autowired
    private ContentService contentService;

    //Referal management

    @GetMapping("/users")
    public String showUsers(Model model) {
        List<Customer> customers = userService.getAllUsers();
        model.addAttribute("users", customers);
        return "refferalmanagement"; // Name of the Thymeleaf template
    }
}
