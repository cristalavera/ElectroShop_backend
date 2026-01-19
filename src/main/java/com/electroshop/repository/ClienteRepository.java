package com.electroshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.electroshop.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {}