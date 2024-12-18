package com.example.OneBillion.Security;

import com.example.OneBillion.Model.Admin;
import com.example.OneBillion.Model.Role;
import com.example.OneBillion.Model.User;
import com.example.OneBillion.Repository.AdminRepo;
import com.example.OneBillion.Repository.RoleRepo;
import com.example.OneBillion.Repository.UserRepo;
import com.example.OneBillion.Service.CustomUserDetailService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class MyConfig {
    private final CustomUserDetailService customUserDetailService;

    public MyConfig(CustomUserDetailService customUserDetailService) {
        this.customUserDetailService = customUserDetailService;
    }

    @Bean
    public CommandLineRunner initializers(AdminRepo adminRepo,UserRepo userRepo, RoleRepo roleRepo) {
        return args -> {
            if (adminRepo.findByEmail("Admin@gmail.com") == null) {
                Role adminRole=new Role();
                adminRole.setUserName("ADMIN");
                roleRepo.save(adminRole);
                User user=new User();
                user.setEmail("Admin@gmail.com");
                user.setPassword(passwordEncoder().encode("Admin123"));// Save role first
                Admin admin=new Admin();
                admin.setEmail("Admin@gmail.com");
                admin.setAdminPassword(passwordEncoder().encode("Admin123"));
                admin.setPattern("[0,1,2]");
                user.getRoles().add(adminRole);
                userRepo.save(user);
                // Assign role to user
                adminRepo.save(admin); // Save user
            }
        };
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())
                .authorizeHttpRequests(req -> req
                        .requestMatchers("/savereg","/userloginpage","/adminloginpage","/userregister","/home", "/css/**", "/js/**").permitAll()
                        .requestMatchers("/user/*").hasRole("USER")
                        .requestMatchers("/admin/*").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/loginpage")
                        .successHandler(customAuthSuccessHandler())
                        .permitAll()
                )
                .httpBasic(withDefaults())
                .logout(withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public CustomAuthenticationSuccessHandler customAuthSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }
}