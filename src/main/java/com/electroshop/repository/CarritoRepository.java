package com.electroshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.electroshop.model.Carrito;

public interface CarritoRepository extends JpaRepository<Carrito, Long> {

}