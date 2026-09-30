package com.electroshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.electroshop.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}