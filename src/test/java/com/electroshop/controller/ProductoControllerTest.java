package com.electroshop.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.electroshop.model.Producto;
import com.electroshop.service.ProductoService;

import org.springframework.web.server.ResponseStatusException;

public class ProductoControllerTest {

    @Mock
    private ProductoService productoService;

    private ProductoController productoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productoController = new ProductoController(productoService);
    }

    @Test
    void testCrearProducto_correcto() {
        // Arrange
        Producto producto = new Producto();
        producto.setNombre("Portátil");
        producto.setPrecio(1000.0);

        when(productoService.crear(producto)).thenReturn(producto);

        // Act
        Producto resultado = productoController.crearProducto(producto);

        // Assert
        assertNotNull(resultado);
        assertEquals("Portátil", resultado.getNombre());
        verify(productoService, times(1)).crear(producto);
    }

    @Test
    void testObtenerProducto_noExiste() {
        // Arrange
        when(productoService.obtenerPorId(1L))
            .thenThrow(new ResponseStatusException(null));

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> {
            productoController.obtenerProducto(1L);
        });
    }
}