package com.example.OneBillion.Controller;

import com.example.OneBillion.Model.Customer;
import com.example.OneBillion.Model.User;
import com.example.OneBillion.Repository.UserRepo;
import com.example.OneBillion.Service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/home")
    public String home()
    {
        return "home";
    }

    @GetMapping("/userloginpage")
    public String login(RedirectAttributes redirectAttributes)
    {

        return "userloginpage";
    }

    @GetMapping("/adminloginpage")
    public String adminLogin()
    {
        return "adminloginpage";
    }

    @GetMapping("/userregister")
    public String reg()
    {
        return "register";
    }

    @PostMapping("/savereg")
    public String savecus(@ModelAttribute Customer customer, RedirectAttributes redirectAttributes)
    {
        if(loginService.SaveCustomer(customer))
        {
            redirectAttributes.addFlashAttribute("successMessage", "You have successfully registered. Please log in.");
            return "redirect:/userloginpage";
        }
        else{
            return "register";
        }

    }
}