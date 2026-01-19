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

import com.electroshop.model.Cliente;
import com.electroshop.repository.ClienteRepository;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

 // Ruta POST para crear un cliente
    @PostMapping
    public Cliente crearCliente(@RequestBody Cliente nuevoCliente) {
        return clienteRepository.save(nuevoCliente); // Inserta el cliente en la BD
    }
    
    // Ruta GET para listar clientes
    @GetMapping
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();  // Devuelve todos los clientes
    }
    
    // Ruta GET para un cliente específico
    @GetMapping("/{id}")
    public Cliente obtenerCliente(@PathVariable Long id) {
        return clienteRepository.findById(id).orElse(null);
    }
    
    // Ruta PUT para actualizar un cliente
    @PutMapping("/{id}")
    public Cliente actualizarCliente(@PathVariable Long id, @RequestBody Cliente clienteActualizado) {
        Cliente cliente = clienteRepository.findById(id).orElse(null);
        if(cliente != null) {
            cliente.setNombre(clienteActualizado.getNombre());
            cliente.setEmail(clienteActualizado.getEmail());
            cliente.setTelefono(clienteActualizado.getTelefono());
            return clienteRepository.save(cliente);  // Guarda cambios
        }
        return null;
    }
    
    // Ruta DELETE para borrar un cliente
    @DeleteMapping("/{id}")
    public String eliminarCliente(@PathVariable Long id) {
        clienteRepository.deleteById(id);
        return "Cliente eliminado correctamente";
    }
}
