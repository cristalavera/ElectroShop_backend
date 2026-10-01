package com.electroshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.electroshop.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

}
