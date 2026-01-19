package com.electroshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.electroshop.model.Venta;

public interface VentaRepository extends JpaRepository<Venta, Long> {}
