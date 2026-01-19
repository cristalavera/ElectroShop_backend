package com.electroshop.middleware;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoggingInterceptor implements HandlerInterceptor {

    // Se ejecuta ANTES de que el controlador maneje la petición
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        long startTime = System.currentTimeMillis();

        // Guardamos el tiempo de inicio para calcular la duración
        request.setAttribute("startTime", startTime);

        System.out.println("   Nueva solicitud:");
        System.out.println("   Método: " + request.getMethod());
        System.out.println("   URL: " + request.getRequestURI());

        return true; // Continuar con la petición
    }

    // Se ejecuta DESPUÉS de que el controlador haya respondido
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        long startTime = (long) request.getAttribute("startTime");
        long duration = System.currentTimeMillis() - startTime;

        System.out.println("   Respuesta enviada");
        System.out.println("   Código de estado: " + response.getStatus());
        System.out.println("   Tiempo de respuesta: " + duration + " ms");
    }
}
