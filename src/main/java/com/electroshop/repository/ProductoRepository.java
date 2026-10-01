package com.electroshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.electroshop.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

}
