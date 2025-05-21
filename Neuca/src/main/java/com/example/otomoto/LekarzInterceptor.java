package com.example.otomoto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LekarzInterceptor implements HandlerInterceptor {

    @Autowired
    private SerwisMyUser serwisMyUser;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            MyUser myUser = serwisMyUser.zwrocUser(username);
            Lekarz lekarz = myUser.getLekarz();

            String uri = request.getRequestURI();

            // wyjątki: strony dostępne bez gabinetu/potwierdzenia
            if (uri.equals("/Neuca/strefaLekarza/UtworzGabinet") ||
                    uri.equals("/Neuca/strefaLekarza/potwierdzLekarza") ||
                    uri.equals("/Neuca/strefaLekarza/utworzonoGabinet")) {
                return true;
            }

            if (lekarz == null) {
                response.sendRedirect("/Neuca/strefaLekarza/UtworzGabinet");
                return false;
            }

            if (!lekarz.isPotwierdzenie()) {
                response.sendRedirect("/Neuca/strefaLekarza/potwierdzLekarza");
                return false;
            }

            return true;
        }

        response.sendRedirect("/Neuca/login");
        return false;
    }
}