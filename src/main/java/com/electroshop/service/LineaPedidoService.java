package com.electroshop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.electroshop.model.LineaPedido;
import com.electroshop.repository.LineaPedidoRepository;

@Service
public class LineaPedidoService {

    @Autowired
    private LineaPedidoRepository lineaPedidoRepository;

    // Obtener todas las líneas de pedido
    public List<LineaPedido> obtenerTodos() {
        return lineaPedidoRepository.findAll();
    }

    // Obtener una línea de pedido por su ID
    public LineaPedido obtenerPorId(Long id) {
        return lineaPedidoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Línea de pedido no encontrada"));
    }

    // Crear una línea de pedido
    public LineaPedido crear(LineaPedido lineaPedido) {
        return lineaPedidoRepository.save(lineaPedido);
    }

    // Actualizar una línea de pedido
    public LineaPedido actualizar(Long id, LineaPedido lineaPedido) {

        LineaPedido existente = obtenerPorId(id);

        existente.setPedido(lineaPedido.getPedido());
        existente.setProducto(lineaPedido.getProducto());
        existente.setNombreProducto(lineaPedido.getNombreProducto());
        existente.setPrecioUnitario(lineaPedido.getPrecioUnitario());
        existente.setCantidad(lineaPedido.getCantidad());
        existente.setSubtotal(lineaPedido.getSubtotal());

        return lineaPedidoRepository.save(existente);
    }

    // Eliminar una línea de pedido
    public void eliminar(Long id) {

        LineaPedido lineaPedido = obtenerPorId(id);

        lineaPedidoRepository.delete(lineaPedido);
    }
}
