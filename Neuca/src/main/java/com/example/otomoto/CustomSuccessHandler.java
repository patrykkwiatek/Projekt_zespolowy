package com.example.otomoto;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    private final SerwisLog serwisLog;

    public CustomSuccessHandler(SerwisLog serwisLog) {
        this.serwisLog = serwisLog;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        String redirectUrl = serwisLog.strefa(authentication);
        response.sendRedirect(redirectUrl);
    }
}
