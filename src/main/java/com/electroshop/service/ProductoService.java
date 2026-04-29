package com.electroshop.service;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.electroshop.model.Producto;
import com.electroshop.repository.ProductoRepository;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> obtenerTodos() {
        return productoRepository.findAll();
    }

    public Producto obtenerPorId(Long id) {
        return productoRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Producto no encontrado"));
    }

    public Producto crear(Producto producto) {
        return productoRepository.save(producto);
    }

    public List<Producto> crearVarios(List<Producto> productos) {
        return productoRepository.saveAll(productos);
    }
    
    public Producto actualizar(Long id, Producto producto) {
        Producto existente = obtenerPorId(id);
        existente.setNombre(producto.getNombre());
        existente.setDescripcion(producto.getDescripcion());
        existente.setPrecio(producto.getPrecio());
        existente.setStock(producto.getStock());
        return productoRepository.save(existente);
    }

    public Producto actualizarParcial(Long id, Map<String, Object> cambios) {
        Producto producto = obtenerPorId(id);

        if (cambios.containsKey("nombre")) {
            producto.setNombre((String) cambios.get("nombre"));
        }
        if (cambios.containsKey("descripcion")) {
            producto.setDescripcion((String) cambios.get("descripcion"));
        }
        if (cambios.containsKey("precio")) {
            producto.setPrecio(Double.valueOf(cambios.get("precio").toString()));
        }
        if (cambios.containsKey("stock")) {
            producto.setStock(Integer.valueOf(cambios.get("stock").toString()));
        }

        return productoRepository.save(producto);
    }
    
    public void eliminar(Long id) {
        Producto producto = obtenerPorId(id);
        productoRepository.delete(producto);
    }
}
