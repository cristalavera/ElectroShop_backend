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

import com.electroshop.model.Pedido;
import com.electroshop.service.PedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;


    @Autowired
    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }


    // Ruta POST para crear un pedido
    @PostMapping
    public Pedido crearPedido(@RequestBody Pedido nuevoPedido) {

        return pedidoService.crear(nuevoPedido);
    }


    // Ruta GET para listar todos los pedidos
    @GetMapping
    public List<Pedido> listarPedidos() {

        return pedidoService.obtenerTodos();
    }


    // Ruta GET para obtener un pedido específico
    @GetMapping("/{id}")
    public Pedido obtenerPedido(@PathVariable Long id) {

        return pedidoService.obtenerPorId(id);
    }


    // Ruta PUT para actualizar un pedido
    @PutMapping("/{id}")
    public Pedido actualizarPedido(
            @PathVariable Long id,
            @RequestBody Pedido pedidoActualizado) {

        return pedidoService.actualizar(id, pedidoActualizado);
    }


    // Ruta DELETE para eliminar un pedido
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPedido(@PathVariable Long id) {

        pedidoService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}
