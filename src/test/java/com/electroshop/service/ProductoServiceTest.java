package com.electroshop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import com.electroshop.model.Producto;
import com.electroshop.repository.ProductoRepository;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    private Producto producto;

    @BeforeEach
    void setUp() {

        producto = new Producto();
        producto.setNombre("Portátil");
        producto.setPrecio(800.0);
    }

    @Test
    void debeCrearProductoCorrectamente() {

        when(productoRepository.save(producto))
                .thenReturn(producto);

        Producto resultado = productoService.crear(producto);

        assertNotNull(resultado);
        assertEquals("Portátil", resultado.getNombre());

        verify(productoRepository, times(1)).save(producto);
    }

    @Test
    void debeLanzarExcepcionSiProductoNoExiste() {

        when(productoRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> {
            productoService.obtenerPorId(99L);
        });
    }
}