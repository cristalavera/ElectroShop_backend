package com.electroshop.middleware;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String user = request.getHeader("user");

        if (user == null || user.isEmpty()) {
            response.setStatus(401);
            response.getWriter().write("Usuario no autenticado");
            return false;
        }

        return true;
    }
}
