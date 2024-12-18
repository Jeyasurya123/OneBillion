package com.example.OneBillion.Service;

import com.example.OneBillion.Model.Customer;
import com.example.OneBillion.Repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private CustomerRepo customerRepo;

    public List<Customer> getAllUsers() {
        return customerRepo.findAll();
    }

    public Long getLoggedInUserId() {
        // Get the authentication object from Spring Security
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // If the authentication is null (not authenticated), return null or throw an exception
        if (authentication == null) {
            throw new IllegalStateException("No authenticated user found");
        }

        // Extract the principal (user details)
        Object principal = authentication.getPrincipal();

        // Check if the principal is of type UserDetails (the Spring Security user object)
        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            String username = userDetails.getUsername(); // Get the username (or email)

            // Find the user in the database using the username
            Customer user = customerRepo.findByEmail(username);

            if (user != null) {
                return (long) user.getId();  // Return the ID of the logged-in user
            }
        }

        // If no user was found, return null or handle the error appropriately
        throw new IllegalStateException("User not found in the database");
    }

}
