package com.electroshop.middleware;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

    	String authHeader = request.getHeader("Authorization");

        // Permitir preflight CORS
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }

        // Validar token
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(401);
            response.getWriter().write("Token no válido o ausente");
            return false;
        }

        return true;
    }
}
