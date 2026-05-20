package com.electroshop.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.electroshop.model.Producto;
import com.electroshop.repository.ProductoRepository;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.junit.jupiter.api.Assertions.assertFalse;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductoIntegrationTest {

	@Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductoRepository productoRepository;

    @Test
    void debeCrearProductoCorrectamente() throws Exception {

		String productoJson = """
				{
				  "nombre": "Teclado",
				  "descripcion": "Teclado mecánico",
				  "precio": 50,
				  "stock": 10
				}
				        """;

		mockMvc.perform(post("/productos")
		        .header("Authorization", "Bearer testtoken")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productoJson))
                .andExpect(status().isOk());

        List<Producto> productos = productoRepository.findAll();
        assertFalse(productos.isEmpty());
    }
    
    @Test
    void debeObtenerProductoExistente() throws Exception {

        Producto producto = new Producto();
        producto.setNombre("Ratón");
        producto.setPrecio(20);

        productoRepository.save(producto);

        mockMvc.perform(get("/productos/" + producto.getId())
                .header("Authorization", "Bearer testtoken"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Ratón"));
    }
    
    @Test
    void debeDevolverErrorSiProductoNoExiste() throws Exception {

        Long idInexistente = 9999L;

        mockMvc.perform(get("/productos/" + idInexistente)
                .header("Authorization", "Bearer testtoken"))
                .andExpect(status().isNotFound());
    }
       
}
