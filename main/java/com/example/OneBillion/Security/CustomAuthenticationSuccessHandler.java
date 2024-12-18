package com.example.OneBillion.Security;

import com.example.OneBillion.Model.Admin;
import com.example.OneBillion.Repository.AdminRepo;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private AdminRepo adminRepo;

    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationSuccessHandler.class);


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        logger.info("User logged in with authorities: {}", authorities);
        String name=request.getParameter("username");
        String pattern = request.getParameter("pattern");
        Admin admin=adminRepo.findByEmail(name);
        for (GrantedAuthority authority : authorities) {
            logger.info("chceking authority:{}",authority.getAuthority());

                if (authority.getAuthority().equals("ROLE_ADMIN")) {
                    if (admin.getPattern().equals(pattern)) {
                    logger.info("redirecting to /admin/home");
                    response.sendRedirect("/admin/adm");
                    return;
                }
            }
            else if (authority.getAuthority().equals("ROLE_USER")) {
                response.sendRedirect("/user/usr");
                return;
            }
        }
        response.sendRedirect("/home"); // Default page if no role matched
    }

}
