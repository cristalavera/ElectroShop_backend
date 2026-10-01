package com.electroshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.electroshop.model.Pago;
import com.electroshop.service.PagoService;

@RestController
@RequestMapping("/pagos")
public class PagoController {

    private final PagoService pagoService;


    @Autowired
    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }


    // Ruta POST para crear un pago
    @PostMapping
    public Pago crearPago(@RequestBody Pago nuevoPago) {

        return pagoService.crear(nuevoPago);
    }


    // Ruta GET para listar todos los pagos
    @GetMapping
    public List<Pago> listarPagos() {

        return pagoService.obtenerTodos();
    }


    // Ruta GET para obtener un pago específico
    @GetMapping("/{id}")
    public Pago obtenerPago(@PathVariable Long id) {

        return pagoService.obtenerPorId(id);
    }


    // Ruta PUT para actualizar un pago
    @PutMapping("/{id}")
    public Pago actualizarPago(
            @PathVariable Long id,
            @RequestBody Pago pagoActualizado) {

        return pagoService.actualizar(id, pagoActualizado);
    }


    // Ruta DELETE para eliminar un pago
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPago(@PathVariable Long id) {

        pagoService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}