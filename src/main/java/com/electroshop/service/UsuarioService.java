package com.electroshop.service;

import com.electroshop.dto.RegistroUsuarioRequest;
import com.electroshop.model.Usuario;
import com.electroshop.repository.UsuarioRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository,
                          PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario registrarUsuario(RegistroUsuarioRequest datos) {

        if (usuarioRepository.findByEmail(datos.getEmail()).isPresent()) {
            throw new IllegalArgumentException("El email ya está registrado");
        }

        Usuario usuario = new Usuario();

        usuario.setEmail(datos.getEmail());
        usuario.setPassword(passwordEncoder.encode(datos.getPassword()));
        usuario.setNombre(datos.getNombre());
        usuario.setApellidos(datos.getApellidos());
        usuario.setTelefono(datos.getTelefono());
        usuario.setDni(datos.getDni());

        usuario.setRol("CLIENTE");

        return usuarioRepository.save(usuario);
    }
}