package com.electroshop.controller;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.electroshop.model.Producto;
import com.electroshop.repository.ProductoRepository;
import jakarta.validation.Valid;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/productos")
@CrossOrigin(origins = "http://localhost:5173")

public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    // Ruta POST para crear un producto
    @PostMapping
    public Producto crearProducto(@Valid @RequestBody Producto nuevoProducto) {
        return productoRepository.save(nuevoProducto); // Inserta el producto en la BD
    }
    
    // Ruta POST para crear varios productos a la vez
    @PostMapping("/varios")
    public List<Producto> crearVariosProductos(@Valid @RequestBody List<Producto> nuevosProductos) {
        return productoRepository.saveAll(nuevosProductos);
    }
    
    // Ruta GET para listar productos
    @GetMapping
    public List<Producto> listarProductos() {
        return productoRepository.findAll();  // Devuelve todos los productos
    }
    
    // Ruta GET para un producto específico
    @GetMapping("/{id}")
    public Producto obtenerProducto(@PathVariable Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Producto no encontrado"));
    }
    
    // Ruta PUT para actualizar totalmente un producto
    @PutMapping("/{id}")
    public Producto actualizarProducto(@Valid @PathVariable Long id, @RequestBody Producto productoActualizado) {
        Producto producto = productoRepository.findById(id).orElse(null);
        if(producto != null) {
            producto.setNombre(productoActualizado.getNombre());
            producto.setDescripcion(productoActualizado.getDescripcion());
            producto.setPrecio(productoActualizado.getPrecio());
            producto.setStock(productoActualizado.getStock());
            return productoRepository.save(producto);  // Guarda cambios
        }
        return null;
    }
    
    // Ruta PUT para actualizar parcialmente un producto
    @PatchMapping("/{id}")
    public ResponseEntity<Producto> actualizarParcial(
            @PathVariable Long id,
            @RequestBody Map<String, Object> cambios) {

        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));

        // Aplicar cambios solo a los campos enviados
        if (cambios.containsKey("nombre")) {
            producto.setNombre((String) cambios.get("nombre"));
        }
        if (cambios.containsKey("descripción")) {
            producto.setDescripcion((String) cambios.get("descripción"));
        }
        if (cambios.containsKey("precio")) {
            producto.setPrecio(Double.valueOf(cambios.get("precio").toString()));
        }
        if (cambios.containsKey("stock")) {
            producto.setStock(Integer.valueOf(cambios.get("stock").toString()));
        }
    
        Producto actualizado = productoRepository.save(producto);
        return ResponseEntity.ok(actualizado);
    }
    
    
    // Ruta DELETE para borrar un producto
    @DeleteMapping("/{id}")
    public String eliminarProducto(@PathVariable Long id) {
        productoRepository.deleteById(id);
        return "Producto eliminado correctamente";
    }
}
    
    



