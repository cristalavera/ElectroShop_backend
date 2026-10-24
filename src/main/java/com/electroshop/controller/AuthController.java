package com.electroshop.controller;

import org.springframework.web.bind.annotation.*;

import com.electroshop.dto.LoginRequest;
import com.electroshop.dto.UsuarioResponse;
import com.electroshop.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public UsuarioResponse login(@Valid @RequestBody LoginRequest datos) {
        return authService.login(datos);
    }
}