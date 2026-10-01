package com.electroshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.electroshop.model.LineaPedido;
import com.electroshop.service.LineaPedidoService;

@RestController
@RequestMapping("/lineas-pedido")
public class LineaPedidoController {

    private final LineaPedidoService lineaPedidoService;

    @Autowired
    public LineaPedidoController(LineaPedidoService lineaPedidoService) {
        this.lineaPedidoService = lineaPedidoService;
    }

    // Ruta POST para crear una línea de pedido
    @PostMapping
    public LineaPedido crearLineaPedido(
            @RequestBody LineaPedido nuevaLineaPedido) {

        return lineaPedidoService.crear(nuevaLineaPedido);
    }

    // Ruta GET para listar todas las líneas de pedido
    @GetMapping
    public List<LineaPedido> listarLineasPedido() {

        return lineaPedidoService.obtenerTodos();
    }

    // Ruta GET para obtener una línea de pedido específica
    @GetMapping("/{id}")
    public LineaPedido obtenerLineaPedido(
            @PathVariable Long id) {

        return lineaPedidoService.obtenerPorId(id);
    }

    // Ruta PUT para actualizar una línea de pedido
    @PutMapping("/{id}")
    public LineaPedido actualizarLineaPedido(
            @PathVariable Long id,
            @RequestBody LineaPedido lineaPedidoActualizada) {

        return lineaPedidoService.actualizar(id, lineaPedidoActualizada);
    }

    // Ruta DELETE para eliminar una línea de pedido
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarLineaPedido(
            @PathVariable Long id) {

        lineaPedidoService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}
