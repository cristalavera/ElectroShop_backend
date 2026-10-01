package com.electroshop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.electroshop.model.Pago;
import com.electroshop.repository.PagoRepository;

@Service
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;


    // Obtener todos los pagos
    public List<Pago> obtenerTodos() {
        return pagoRepository.findAll();
    }


    // Obtener un pago por su ID
    public Pago obtenerPorId(Long id) {

        return pagoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Pago no encontrado"));
    }


    // Crear un pago
    public Pago crear(Pago pago) {
        return pagoRepository.save(pago);
    }


    // Actualizar un pago
    public Pago actualizar(Long id, Pago pagoActualizado) {

        Pago pago = obtenerPorId(id);

        pago.setPedido(pagoActualizado.getPedido());
        pago.setMetodo(pagoActualizado.getMetodo());
        pago.setEstado(pagoActualizado.getEstado());
        pago.setImporte(pagoActualizado.getImporte());
        pago.setFecha(pagoActualizado.getFecha());

        return pagoRepository.save(pago);
    }


    // Eliminar un pago
    public void eliminar(Long id) {

        Pago pago = obtenerPorId(id);

        pagoRepository.delete(pago);
    }
}