package com.electroshop.middleware;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RoleInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

	    // Permitir preflight CORS
	    if (request.getMethod().equals("OPTIONS")) {
	        return true;
	    }

	    String authHeader = request.getHeader("Authorization");

	    // Seguridad básica
	    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
	        return true; // AuthInterceptor ya gestiona el 401
	    }

	    // Extraer token
	    String token = authHeader.replace("Bearer ", "");
	    String[] parts = token.split("-");

	    // Extraer rol
	    String role = parts.length > 2 ? parts[2] : "UNKNOWN";

	    /*
	    // CONTROL DE ACCESO POR ROL (PREGUNTA 7)

	    if (request.getRequestURI().contains("/productos")) {
	        if (!role.equals("ADMIN")) {
	            response.setStatus(403);
	            response.getWriter().write("Acceso solo ADMIN");
	            return false;
	        }
	    }
	    */

	    return true;
	}
}