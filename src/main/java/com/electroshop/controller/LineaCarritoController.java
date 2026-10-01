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

import com.electroshop.model.LineaCarrito;
import com.electroshop.repository.LineaCarritoRepository;

@RestController
@RequestMapping("/lineas-carrito")
public class LineaCarritoController {

    @Autowired
    private LineaCarritoRepository lineaCarritoRepository;

    // Crear una línea de carrito
    @PostMapping
    public LineaCarrito crearLineaCarrito(
            @RequestBody LineaCarrito nuevaLinea) {

        return lineaCarritoRepository.save(nuevaLinea);
    }

    // Listar líneas de carrito
    @GetMapping
    public List<LineaCarrito> listarLineasCarrito() {

        return lineaCarritoRepository.findAll();
    }

    // Obtener una línea de carrito por su ID
    @GetMapping("/{id}")
    public LineaCarrito obtenerLineaCarrito(@PathVariable Long id) {

        return lineaCarritoRepository.findById(id).orElse(null);
    }

    // Actualizar una línea de carrito
    @PutMapping("/{id}")
    public LineaCarrito actualizarLineaCarrito(
            @PathVariable Long id,
            @RequestBody LineaCarrito lineaActualizada) {

        LineaCarrito linea = lineaCarritoRepository
                .findById(id)
                .orElse(null);

        if (linea != null) {

            linea.setCarrito(lineaActualizada.getCarrito());
            linea.setProducto(lineaActualizada.getProducto());
            linea.setCantidad(lineaActualizada.getCantidad());

            return lineaCarritoRepository.save(linea);
        }

        return null;
    }

    // Eliminar una línea de carrito
    @DeleteMapping("/{id}")
    public String eliminarLineaCarrito(@PathVariable Long id) {

        lineaCarritoRepository.deleteById(id);

        return "Línea de carrito eliminada correctamente";
    }
}
