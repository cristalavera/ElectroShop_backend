package com.electroshop.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.electroshop.dto.UsuarioResponse;
import com.electroshop.service.AuthService;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
import com.electroshop.dto.ActualizarPerfilRequest;
import com.electroshop.model.Usuario;
import com.electroshop.repository.UsuarioRepository;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/perfil")
public class PerfilController {

	private final AuthService authService;

	private final UsuarioRepository usuarioRepository;

	public PerfilController(AuthService authService, UsuarioRepository usuarioRepository) {
		this.authService = authService;
		this.usuarioRepository = usuarioRepository;
	}

	@GetMapping
	public ResponseEntity<UsuarioResponse> obtenerPerfil(Authentication authentication) {

		UsuarioResponse usuario = authService.obtenerUsuarioPorEmail(authentication.getName());

		return ResponseEntity.ok(usuario);
	}

	@PutMapping
	public ResponseEntity<UsuarioResponse> actualizarPerfil(Authentication authentication,
			@Valid @RequestBody ActualizarPerfilRequest datos) {

		Usuario usuario = usuarioRepository.findByEmail(authentication.getName())
		        .orElseThrow(() -> new ResponseStatusException(
		                HttpStatus.NOT_FOUND, "Usuario no encontrado"));

		usuario.setNombre(datos.getNombre());
		usuario.setApellidos(datos.getApellidos());
		usuario.setTelefono(datos.getTelefono());
		usuario.setDni(datos.getDni());

		Usuario usuarioActualizado = usuarioRepository.save(usuario);

		return ResponseEntity.ok(new UsuarioResponse(usuarioActualizado));
	}
    
}