package com.electroshop.controller;

import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.electroshop.dto.LoginRequest;
import com.electroshop.dto.LoginResponse;
import com.electroshop.dto.UsuarioResponse;
import com.electroshop.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;



@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Duration DURACION_COOKIE =
            Duration.ofHours(2);

    private final AuthService authService;

    @Value("${app.cookie.secure:false}")
    private boolean cookieSecure;

    @Value("${app.cookie.same-site:Lax}")
    private String cookieSameSite;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioResponse> login(
            @Valid @RequestBody LoginRequest datos,
            HttpServletResponse response) {

        LoginResponse resultado = authService.login(datos);

        ResponseCookie cookie = ResponseCookie
                .from("authToken", resultado.getToken())
                .httpOnly(true)
                .secure(cookieSecure)
                .path("/")
                .maxAge(DURACION_COOKIE)
                .sameSite(cookieSameSite)
                .build();

        response.addHeader(
                HttpHeaders.SET_COOKIE,
                cookie.toString()
        );

        return ResponseEntity.ok(resultado.getUsuario());
    }

    @GetMapping("/me")
    public ResponseEntity<UsuarioResponse> obtenerUsuarioAutenticado(
            Authentication authentication) {

        UsuarioResponse usuario =
                authService.obtenerUsuarioPorEmail(authentication.getName());

        return ResponseEntity.ok(usuario);
    }
    
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {

        ResponseCookie cookie = ResponseCookie
                .from("authToken", "")
                .httpOnly(true)
                .secure(cookieSecure)
                .path("/")
                .maxAge(0)
                .sameSite(cookieSameSite)
                .build();

        response.addHeader(
                HttpHeaders.SET_COOKIE,
                cookie.toString()
        );

        return ResponseEntity.noContent().build();
    }

}