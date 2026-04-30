package com.electroshop.middleware;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RoleInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

    	// PRIMERO: dejar pasar preflight CORS
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }
    	
    	String role = request.getHeader("role");

        // Ejemplo: proteger rutas de eliminación
        if (request.getMethod().equals("DELETE") && !"ADMIN".equals(role)) {
            response.setStatus(403);
            response.getWriter().write("Acceso denegado: se requiere rol ADMIN");
            return false;
        }

        return true;
    }
}
