package com.example.otomoto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AptekaInterceptor implements HandlerInterceptor {

    @Autowired
    private SerwisMyUser serwisMyUser;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            MyUser myUser = serwisMyUser.zwrocUser(username);
            Apteka apteka = myUser.getApteka();

            String uri = request.getRequestURI();

            // wyjątki, które mają być dostępne nawet bez apteki
            if (uri.equals("/Neuca/strefaAptekarza/utworzApteke") ||
                    uri.equals("/Neuca/strefaAptekarza/potwierdzApteka")||
                    uri.equals("/Neuca/strefaAptekarza/utworzonoApteke")) {
                return true;
            }

            if (apteka == null) {
                response.sendRedirect("/Neuca/strefaAptekarza/utworzApteke");
                return false;
            }

            if (!apteka.isPotwierdzenie()) {
                response.sendRedirect("/Neuca/strefaAptekarza/potwierdzApteka");
                return false;
            }

            return true; // OK – apteka istnieje i jest potwierdzona
        }

        response.sendRedirect("/login");
        return false;
    }
}
