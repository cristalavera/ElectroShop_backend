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

import com.electroshop.model.Venta;
import com.electroshop.repository.VentaRepository;

@RestController
@RequestMapping("/ventas")
public class VentaController {

    @Autowired
    private VentaRepository ventaRepository;

 // Ruta POST para crear una venta
    @PostMapping
    public Venta crearVenta(@RequestBody Venta nuevaVenta) {
        return ventaRepository.save(nuevaVenta); // Inserta la venta en la BD
    }
    
    // Ruta GET para listar ventas
    @GetMapping
    public List<Venta> listarVentas() {
        return ventaRepository.findAll();  // Devuelve todas las ventas
    }
    
    // Ruta GET para una venta específica
    @GetMapping("/{id}")
    public Venta obtenerVenta(@PathVariable Long id) {
        return ventaRepository.findById(id).orElse(null);
    }
    
    // Ruta PUT para actualizar una venta
    @PutMapping("/{id}")
    public Venta actualizarVenta(@PathVariable Long id, @RequestBody Venta ventaActualizada) {
        Venta venta = ventaRepository.findById(id).orElse(null);
        if(venta != null) {
            venta.setFecha(ventaActualizada.getFecha());
            venta.setCantidad(ventaActualizada.getCantidad());
            venta.setProductos(ventaActualizada.getProductos());
            venta.setCliente(ventaActualizada.getCliente());
            return ventaRepository.save(venta);  // Guarda cambios
        }
        return null;
    }
    
    // Ruta DELETE para borrar una venta
    @DeleteMapping("/{id}")
    public String eliminarVenta(@PathVariable Long id) {
        ventaRepository.deleteById(id);
        return "Venta eliminada correctamente";
    }
}
