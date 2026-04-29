package com.electroshop.controller;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.electroshop.model.Producto;
import com.electroshop.service.ProductoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/productos")
@CrossOrigin(origins = "http://localhost:5173")

public class ProductoController {

	
	private final ProductoService productoService;

	@Autowired
	public ProductoController(ProductoService productoService) {
	    this.productoService = productoService;
	}

    // Ruta POST para crear un producto
	@PostMapping
	public Producto crearProducto(@Valid @RequestBody Producto nuevoProducto) {
	    return productoService.crear(nuevoProducto);
	}
    
    // Ruta POST para crear varios productos a la vez
	@PostMapping("/varios")
	public List<Producto> crearVariosProductos(@Valid @RequestBody List<Producto> nuevosProductos) {
	    return productoService.crearVarios(nuevosProductos);
	}
    
    // Ruta GET para listar productos
	@GetMapping
	public List<Producto> listarProductos() {
	    return productoService.obtenerTodos(); //Devuelve todos los productos
	}
    
    // Ruta GET para un producto específico
    @GetMapping("/{id}")
    public Producto obtenerProducto(@PathVariable Long id) {
        return productoService.obtenerPorId(id);
    }
    
    // Ruta PUT para actualizar totalmente un producto
    @PutMapping("/{id}")
    public Producto actualizarProducto(@PathVariable Long id, @Valid @RequestBody Producto productoActualizado) {
        return productoService.actualizar(id, productoActualizado);
    }
    
    // Ruta PUT para actualizar parcialmente un producto
    @PatchMapping("/{id}")
    public ResponseEntity<Producto> actualizarParcial(
            @PathVariable Long id,
            @RequestBody Map<String, Object> cambios) {

        Producto actualizado = productoService.actualizarParcial(id, cambios);
        return ResponseEntity.ok(actualizado);
    }
    
    
    // Ruta DELETE para borrar un producto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
    
    



