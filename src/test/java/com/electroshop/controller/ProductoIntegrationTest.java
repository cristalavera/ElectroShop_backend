package com.electroshop.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import com.electroshop.model.Producto;
import com.electroshop.repository.ProductoRepository;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.junit.jupiter.api.Assertions.assertFalse;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;


@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc

public class ProductoIntegrationTest {

	@Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductoRepository productoRepository;

    @WithMockUser(roles = "ADMIN")
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
		        .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(productoJson))
                .andExpect(status().isOk());

        List<Producto> productos = productoRepository.findAll();
        assertFalse(productos.isEmpty());
    }
    
    @WithMockUser
    @Test
    void debeObtenerProductoExistente() throws Exception {

        Producto producto = new Producto();
        producto.setNombre("Ratón");
        producto.setPrecio(BigDecimal.valueOf(20.0));
        producto.setStock(10);

        productoRepository.save(producto);

        mockMvc.perform(get("/productos/" + producto.getId())
                .header("Authorization", "Bearer testtoken"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Ratón"));
    }
    
    @WithMockUser
    @Test
    void debeDevolverErrorSiProductoNoExiste() throws Exception {

        Long idInexistente = 9999L;

        mockMvc.perform(get("/productos/" + idInexistente)
                .header("Authorization", "Bearer testtoken"))
                .andExpect(status().isNotFound());
    }
    

    @Test
    void invitadoPuedeConsultarCatalogo() throws Exception {
        mockMvc.perform(get("/productos"))
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = "CLIENTE")
    @Test
    void clienteNoPuedeCrearProducto() throws Exception {
        String productoJson = """
                {
                  "nombre": "Monitor",
                  "descripcion": "Monitor de prueba",
                  "precio": 150,
                  "stock": 5
                }
                """;

        mockMvc.perform(post("/productos")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(productoJson))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(roles = "CLIENTE")
    @Test
    void clienteNoPuedeEliminarProducto() throws Exception {
        Producto producto = new Producto();
        producto.setNombre("Auriculares");
        producto.setPrecio(BigDecimal.valueOf(30));
        producto.setStock(5);
        producto = productoRepository.save(producto);

        mockMvc.perform(delete("/productos/" + producto.getId())
                .with(csrf()))
                .andExpect(status().isForbidden());
    }


    @WithMockUser(roles = "CLIENTE")
    @Test
    void clienteNoPuedeModificarProducto() throws Exception {
        Producto producto = new Producto();
        producto.setNombre("Teclado");
        producto.setPrecio(BigDecimal.valueOf(50));
        producto.setStock(10);
        producto = productoRepository.save(producto);

        String productoJson = """
                {
                  "nombre": "Teclado modificado",
                  "descripcion": "Descripción modificada",
                  "precio": 60,
                  "stock": 8
                }
                """;

        mockMvc.perform(put("/productos/" + producto.getId())
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(productoJson))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(roles = "CLIENTE")
    @Test
    void clienteNoPuedeModificarParcialmenteProducto() throws Exception {
        Producto producto = new Producto();
        producto.setNombre("Ratón");
        producto.setPrecio(BigDecimal.valueOf(20));
        producto.setStock(5);
        producto = productoRepository.save(producto);

        String productoJson = """
                {
                  "precio": 25
                }
                """;

        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                .patch("/productos/" + producto.getId())
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(productoJson))
                .andExpect(status().isForbidden());
    }

}