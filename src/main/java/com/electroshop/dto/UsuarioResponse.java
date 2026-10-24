package com.electroshop.dto;

import com.electroshop.model.Usuario;

public class UsuarioResponse {

    private Long id;
    private String email;
    private String nombre;
    private String apellidos;
    private String telefono;
    private String dni;
    private String rol;

    public UsuarioResponse(Usuario usuario) {
        this.id = usuario.getId();
        this.email = usuario.getEmail();
        this.nombre = usuario.getNombre();
        this.apellidos = usuario.getApellidos();
        this.telefono = usuario.getTelefono();
        this.dni = usuario.getDni();
        this.rol = usuario.getRol();
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getDni() {
        return dni;
    }

    public String getRol() {
        return rol;
    }
}