package com.electroshop.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.electroshop.dto.LoginRequest;
import com.electroshop.dto.UsuarioResponse;
import com.electroshop.model.Usuario;
import com.electroshop.repository.UsuarioRepository;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;

    public AuthService(AuthenticationManager authenticationManager,
                       UsuarioRepository usuarioRepository) {
        this.authenticationManager = authenticationManager;
        this.usuarioRepository = usuarioRepository;
    }

    public UsuarioResponse login(LoginRequest datos) {

        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                datos.getEmail(),
                datos.getPassword()
            )
        );

        Usuario usuario = usuarioRepository.findByEmail(datos.getEmail())
                .orElseThrow(() ->
                    new IllegalArgumentException("Usuario no encontrado"));

        return new UsuarioResponse(usuario);
    }
}
