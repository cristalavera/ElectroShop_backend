
package com.electroshop.dto;

public class LoginResponse {

    private final UsuarioResponse usuario;
    private final String token;

    public LoginResponse(UsuarioResponse usuario, String token) {
        this.usuario = usuario;
        this.token = token;
    }

    public UsuarioResponse getUsuario() {
        return usuario;
    }

    public String getToken() {
        return token;
    }
}
