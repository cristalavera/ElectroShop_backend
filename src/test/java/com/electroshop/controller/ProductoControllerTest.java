package com.electroshop.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;
import com.electroshop.model.Producto;
import com.electroshop.repository.ProductoRepository;


public class ProductoControllerTest {
	@Mock
    private ProductoRepository productoRepository;

    private ProductoController productoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Inyección del mock mediante constructor
        productoController = new ProductoController(productoRepository);
    }

    @Test
    void testCrearProducto_correcto() {
        // Arrange
        Producto producto = new Producto();
        producto.setNombre("Portátil");
        producto.setPrecio(1000.0);

        when(productoRepository.save(producto)).thenReturn(producto);

        // Act
        Producto resultado = productoController.crearProducto(producto);

        // Assert
        assertNotNull(resultado);
        assertEquals("Portátil", resultado.getNombre());
        verify(productoRepository, times(1)).save(producto);
    }

    @Test
    void testObtenerProducto_noExiste() {
        // Arrange
        when(productoRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> {
            productoController.obtenerProducto(1L);
        });
    }
}
