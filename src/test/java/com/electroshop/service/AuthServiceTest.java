package com.electroshop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.server.ResponseStatusException;

import com.electroshop.model.Usuario;
import com.electroshop.repository.UsuarioRepository;

class AuthServiceTest {

    @Test
    void obtenerUsuarioPorEmailInexistenteDevuelve404() {
        AuthenticationManager authenticationManager =
                mock(AuthenticationManager.class);
        UsuarioRepository usuarioRepository =
                mock(UsuarioRepository.class);
        JwtService jwtService = mock(JwtService.class);

        AuthService authService = new AuthService(
                authenticationManager,
                usuarioRepository,
                jwtService);

        when(usuarioRepository.findByEmail("noexiste@example.com"))
                .thenReturn(Optional.empty());

        ResponseStatusException excepcion = assertThrows(
                ResponseStatusException.class,
                () -> authService.obtenerUsuarioPorEmail("noexiste@example.com"));

        assertEquals(HttpStatus.NOT_FOUND, excepcion.getStatusCode());
    }
}