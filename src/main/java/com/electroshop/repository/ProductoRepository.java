package com.electroshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.electroshop.model.Producto;

// Producto: entidad que va a manejar
// Long: tipo de dato de la clave primaria de la entidad
public interface ProductoRepository extends JpaRepository<Producto, Long> {
	
    // Métodos personalizados
	
}

