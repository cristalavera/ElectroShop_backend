package com.electroshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.electroshop.model.Usuario;
import com.electroshop.repository.UsuarioRepository;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Crear un usuario
    @PostMapping
    public Usuario crearUsuario(@RequestBody Usuario nuevoUsuario) {
        return usuarioRepository.save(nuevoUsuario);
    }

    // Listar usuarios
    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    // Obtener un usuario por su ID
    @GetMapping("/{id}")
    public Usuario obtenerUsuario(@PathVariable Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    // Actualizar un usuario
    @PutMapping("/{id}")
    public Usuario actualizarUsuario(@PathVariable Long id,
                                     @RequestBody Usuario usuarioActualizado) {

        Usuario usuario = usuarioRepository.findById(id).orElse(null);

        if (usuario != null) {
            usuario.setEmail(usuarioActualizado.getEmail());
            usuario.setPassword(usuarioActualizado.getPassword());
            usuario.setNombre(usuarioActualizado.getNombre());
            usuario.setApellidos(usuarioActualizado.getApellidos());
            usuario.setTelefono(usuarioActualizado.getTelefono());
            usuario.setDni(usuarioActualizado.getDni());
            usuario.setRol(usuarioActualizado.getRol());

            return usuarioRepository.save(usuario);
        }

        return null;
    }

    // Eliminar un usuario
    @DeleteMapping("/{id}")
    public String eliminarUsuario(@PathVariable Long id) {
        usuarioRepository.deleteById(id);
        return "Usuario eliminado correctamente";
    }
}