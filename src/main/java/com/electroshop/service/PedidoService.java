package com.electroshop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.electroshop.model.Pedido;
import com.electroshop.repository.PedidoRepository;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;


    // Obtener todos los pedidos
    public List<Pedido> obtenerTodos() {
        return pedidoRepository.findAll();
    }


    // Obtener un pedido por su ID
    public Pedido obtenerPorId(Long id) {

        return pedidoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Pedido no encontrado"));
    }


    // Crear un pedido
    public Pedido crear(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }


    // Actualizar un pedido
    public Pedido actualizar(Long id, Pedido pedidoActualizado) {

        Pedido pedido = obtenerPorId(id);

        pedido.setUsuario(pedidoActualizado.getUsuario());
        pedido.setFecha(pedidoActualizado.getFecha());
        pedido.setEstado(pedidoActualizado.getEstado());
        pedido.setTotal(pedidoActualizado.getTotal());
        pedido.setDireccionEnvio(pedidoActualizado.getDireccionEnvio());
        pedido.setLineasPedido(pedidoActualizado.getLineasPedido());
        pedido.setPago(pedidoActualizado.getPago());

        return pedidoRepository.save(pedido);
    }


    // Eliminar un pedido
    public void eliminar(Long id) {

        Pedido pedido = obtenerPorId(id);

        pedidoRepository.delete(pedido);
    }
}