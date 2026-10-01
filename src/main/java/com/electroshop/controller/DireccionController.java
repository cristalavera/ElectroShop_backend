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

import com.electroshop.model.Direccion;
import com.electroshop.repository.DireccionRepository;

@RestController
@RequestMapping("/direcciones")
public class DireccionController {

    @Autowired
    private DireccionRepository direccionRepository;

    // Crear una dirección
    @PostMapping
    public Direccion crearDireccion(@RequestBody Direccion nuevaDireccion) {
        return direccionRepository.save(nuevaDireccion);
    }

    // Listar direcciones
    @GetMapping
    public List<Direccion> listarDirecciones() {
        return direccionRepository.findAll();
    }

    // Obtener una dirección por su ID
    @GetMapping("/{id}")
    public Direccion obtenerDireccion(@PathVariable Long id) {
        return direccionRepository.findById(id).orElse(null);
    }

    // Actualizar una dirección
    @PutMapping("/{id}")
    public Direccion actualizarDireccion(@PathVariable Long id,
                                         @RequestBody Direccion direccionActualizada) {

        Direccion direccion = direccionRepository.findById(id).orElse(null);

        if (direccion != null) {
            direccion.setDireccion(direccionActualizada.getDireccion());
            direccion.setCodigoPostal(direccionActualizada.getCodigoPostal());
            direccion.setCiudad(direccionActualizada.getCiudad());
            direccion.setProvincia(direccionActualizada.getProvincia());
            direccion.setPais(direccionActualizada.getPais());

            return direccionRepository.save(direccion);
        }

        return null;
    }

    // Eliminar una dirección
    @DeleteMapping("/{id}")
    public String eliminarDireccion(@PathVariable Long id) {
        direccionRepository.deleteById(id);
        return "Dirección eliminada correctamente";
    }
}
