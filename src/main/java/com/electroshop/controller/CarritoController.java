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

import com.electroshop.model.Carrito;
import com.electroshop.repository.CarritoRepository;

@RestController
@RequestMapping("/carritos")
public class CarritoController {

    @Autowired
    private CarritoRepository carritoRepository;

    // Crear un carrito
    @PostMapping
    public Carrito crearCarrito(@RequestBody Carrito nuevoCarrito) {
        return carritoRepository.save(nuevoCarrito);
    }

    // Listar carritos
    @GetMapping
    public List<Carrito> listarCarritos() {
        return carritoRepository.findAll();
    }

    // Obtener un carrito por su ID
    @GetMapping("/{id}")
    public Carrito obtenerCarrito(@PathVariable Long id) {
        return carritoRepository.findById(id).orElse(null);
    }

    // Actualizar un carrito
    @PutMapping("/{id}")
    public Carrito actualizarCarrito(@PathVariable Long id,
                                     @RequestBody Carrito carritoActualizado) {

        Carrito carrito = carritoRepository.findById(id).orElse(null);

        if (carrito != null) {
            carrito.setUsuario(carritoActualizado.getUsuario());
            carrito.setFechaCreacion(carritoActualizado.getFechaCreacion());

            return carritoRepository.save(carrito);
        }

        return null;
    }

    // Eliminar un carrito
    @DeleteMapping("/{id}")
    public String eliminarCarrito(@PathVariable Long id) {
        carritoRepository.deleteById(id);
        return "Carrito eliminado correctamente";
    }
}