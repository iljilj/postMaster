package com.example.hop.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        boolean isAdmin = false;
        boolean isUser = false;

        for (var authority : authentication.getAuthorities()) {
            if (authority.equals(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
                isAdmin = true;
            }
            if (authority.equals(new SimpleGrantedAuthority("ROLE_USER"))) {
                isUser = true;
            }
        }

        if (isAdmin) {
            response.sendRedirect("/admin");
        } else if (isUser) {
            response.sendRedirect("/user");
        } else {
            response.sendRedirect("/default");
        }
    }
}
