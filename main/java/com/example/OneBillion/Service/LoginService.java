package com.example.OneBillion.Service;

import com.example.OneBillion.Model.Customer;
import com.example.OneBillion.Model.LotteryTicketGenerator;
import com.example.OneBillion.Model.Role;
import com.example.OneBillion.Model.User;
import com.example.OneBillion.Repository.CustomerRepo;
import com.example.OneBillion.Repository.RoleRepo;
import com.example.OneBillion.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private LotteryTicketGenerator lotteryTicketGenerator;

    @Autowired
    private PasswordEncoder passwordEncoder;
    public boolean SaveCustomer(Customer customer) {

        Customer customerCheck=customerRepo.findByEmail(customer.getEmail());
        User userCheck=userRepo.findByEmail(customer.getEmail());

        if(customerCheck == null && userCheck == null)
        {
            Customer customer1=new Customer();
            Role role=new Role();
            role.setUserName("USER");
            roleRepo.save(role);
            User user=new User();
            user.setEmail(customer.getEmail());
            user.setPassword(passwordEncoder.encode(customer.getPassword()));
            user.getRoles().add(role);
            userRepo.save(user);
            customer1.setEmail(customer.getEmail());
            customer1.setPassword(passwordEncoder.encode(customer.getPassword()));
            customer1.setConfirmPassword(passwordEncoder.encode(customer.getConfirmPassword()));
            customer1.setFirstName(customer.getFirstName());
            customer1.setLastName(customer.getLastName());
            customer1.setVerified(false);
            customer1.setReferralCode(lotteryTicketGenerator.toString());
            customerRepo.save(customer1);
            return true;

        }
        else {
            return false;
        }
    }
}
