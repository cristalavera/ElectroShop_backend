package com.electroshop.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.electroshop.model.Usuario;
import com.electroshop.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.electroshop.service.JwtService;
import jakarta.servlet.http.Cookie;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class UsuarioIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Test
	void registroPublicoCreaUsuarioComoCliente() throws Exception {
		String usuarioJson = """
				{
				  "email": "cliente.prueba@example.com",
				  "password": "ClaveSegura123!",
				  "nombre": "Usuario",
				  "apellidos": "De Prueba",
				  "telefono": "600123456",
				  "dni": "12345678Z",
				  "rol": "ADMIN"
				}
				""";

		mockMvc.perform(post("/usuarios").with(csrf()).contentType(MediaType.APPLICATION_JSON).content(usuarioJson))
				.andExpect(status().isOk()).andExpect(jsonPath("$.rol").value("CLIENTE"));
	}

	@Test
	void registroGuardaLaContrasenaCodificada() throws Exception {
		String contrasenaOriginal = "ClaveSegura456!";

		String usuarioJson = """
				{
				  "email": "contrasena.prueba@example.com",
				  "password": "ClaveSegura456!",
				  "nombre": "Prueba",
				  "apellidos": "Contraseña",
				  "telefono": "600123456",
				  "dni": "12345678X"
				}
				""";

		mockMvc.perform(post("/usuarios").with(csrf()).contentType(MediaType.APPLICATION_JSON).content(usuarioJson))
				.andExpect(status().isOk());

		Usuario usuario = usuarioRepository.findByEmail("contrasena.prueba@example.com").orElseThrow();

		assertNotEquals(contrasenaOriginal, usuario.getPassword(), "La contraseña no debe almacenarse en texto plano");

		assertTrue(passwordEncoder.matches(contrasenaOriginal, usuario.getPassword()),
				"La contraseña almacenada debe corresponder a la original");
	}

	@Test
	void registroRechazaUnEmailInvalido() throws Exception {
		String usuarioJson = """
				{
				  "email": "correo-no-valido",
				  "password": "ClaveSegura123!",
				  "nombre": "Usuario",
				  "apellidos": "De Prueba"
				}
				""";

		mockMvc.perform(post("/usuarios").with(csrf()).contentType(MediaType.APPLICATION_JSON).content(usuarioJson))
				.andExpect(status().isBadRequest());
	}

	@Test
	void registroRechazaUnaContrasenaVacia() throws Exception {
		String usuarioJson = """
				{
				  "email": "sin.contrasena@example.com",
				  "password": "",
				  "nombre": "Usuario",
				  "apellidos": "De Prueba"
				}
				""";

		mockMvc.perform(post("/usuarios").with(csrf()).contentType(MediaType.APPLICATION_JSON).content(usuarioJson))
				.andExpect(status().isBadRequest());
	}

	@Test
	void usuarioNoAutenticadoNoPuedeConsultarSusDatos() throws Exception {
		mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/auth/me"))
				.andExpect(status().isForbidden());
	}

	@Test
	void usuarioAutenticadoPuedeConsultarSusDatos() throws Exception {
		Usuario usuario = new Usuario();
		usuario.setEmail("perfil.prueba@example.com");
		usuario.setPassword(passwordEncoder.encode("ClaveSegura123!"));
		usuario.setNombre("Usuario");
		usuario.setApellidos("De Prueba");
		usuario.setRol("CLIENTE");

		usuario = usuarioRepository.save(usuario);

		String token = jwtService.generarToken(usuario.getEmail(), usuario.getRol());

		mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/auth/me")
				.cookie(new Cookie("authToken", token))).andExpect(status().isOk())
				.andExpect(jsonPath("$.email").value("perfil.prueba@example.com"))
				.andExpect(jsonPath("$.rol").value("CLIENTE"));
	}

	@Test
	void clienteAutenticadoNoPuedeConsultarTodosLosUsuarios() throws Exception {
		String token = jwtService.generarToken("cliente.prueba@example.com", "CLIENTE");

		mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/usuarios")
				.cookie(new Cookie("authToken", token))).andExpect(status().isForbidden());
	}

	@Test
	void clienteAutenticadoNoPuedeConsultarUsuarioPorId() throws Exception {
		String token = jwtService.generarToken("cliente.prueba@example.com", "CLIENTE");

		mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/usuarios/1")
				.cookie(new Cookie("authToken", token))).andExpect(status().isForbidden());
	}

	@Test
	void clienteAutenticadoNoPuedeModificarUsuarioPorId() throws Exception {
		String token = jwtService.generarToken("cliente.prueba@example.com", "CLIENTE");

		String usuarioJson = """
				{
				  "email": "otro.usuario@example.com",
				  "password": "OtraClave123!",
				  "nombre": "Nombre modificado",
				  "apellidos": "Apellidos modificados",
				  "telefono": "600111222",
				  "dni": "12345678Z",
				  "rol": "ADMIN"
				}
				""";

		mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put("/usuarios/1")
				.cookie(new Cookie("authToken", token)).with(csrf()).contentType(MediaType.APPLICATION_JSON)
				.content(usuarioJson)).andExpect(status().isForbidden());
	}

	@Test
	void clienteAutenticadoNoPuedeEliminarUsuarioPorId() throws Exception {
		String token = jwtService.generarToken("cliente.prueba@example.com", "CLIENTE");

		mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete("/usuarios/1")
				.cookie(new Cookie("authToken", token)).with(csrf())).andExpect(status().isForbidden());
	}

	@Test
	void usuarioNoAutenticadoNoPuedeConsultarPerfil() throws Exception {
		mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/perfil"))
				.andExpect(status().isForbidden());
	}

	@Test
	void clienteAutenticadoPuedeConsultarSuPerfil() throws Exception {
		Usuario usuario = new Usuario();
		usuario.setEmail("mi.perfil@example.com");
		usuario.setPassword(passwordEncoder.encode("ClaveSegura123!"));
		usuario.setNombre("María");
		usuario.setApellidos("Prueba");
		usuario.setRol("CLIENTE");

		usuario = usuarioRepository.save(usuario);

		String token = jwtService.generarToken(usuario.getEmail(), usuario.getRol());

		mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/perfil")
				.cookie(new Cookie("authToken", token))).andExpect(status().isOk())
				.andExpect(jsonPath("$.email").value("mi.perfil@example.com"))
				.andExpect(jsonPath("$.nombre").value("María")).andExpect(jsonPath("$.rol").value("CLIENTE"));
	}

	@Test
	void clienteAutenticadoPuedeActualizarSuPerfil() throws Exception {
		Usuario usuario = new Usuario();
		usuario.setEmail("actualizar.perfil@example.com");
		usuario.setPassword(passwordEncoder.encode("ClaveSegura123!"));
		usuario.setNombre("Nombre antiguo");
		usuario.setApellidos("Apellidos antiguos");
		usuario.setTelefono("600111222");
		usuario.setDni("12345678A");
		usuario.setRol("CLIENTE");

		usuario = usuarioRepository.save(usuario);

		String token = jwtService.generarToken(usuario.getEmail(), usuario.getRol());

		String datosActualizados = """
				{
				  "nombre": "Nombre nuevo",
				  "apellidos": "Apellidos nuevos",
				  "telefono": "600333444",
				  "dni": "87654321B"
				}
				""";

		mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put("/perfil")
				.cookie(new Cookie("authToken", token)).with(csrf()).contentType(MediaType.APPLICATION_JSON)
				.content(datosActualizados)).andExpect(status().isOk())
				.andExpect(jsonPath("$.nombre").value("Nombre nuevo"))
				.andExpect(jsonPath("$.apellidos").value("Apellidos nuevos"))
				.andExpect(jsonPath("$.telefono").value("600333444")).andExpect(jsonPath("$.dni").value("87654321B"))
				.andExpect(jsonPath("$.email").value("actualizar.perfil@example.com"))
				.andExpect(jsonPath("$.rol").value("CLIENTE"));
	}

	@Test
	void actualizarPerfilNoModificaEmailContrasenaNiRol() throws Exception {
		String contrasenaOriginal = "ClaveSegura123!";

		Usuario usuario = new Usuario();
		usuario.setEmail("seguridad.perfil@example.com");
		usuario.setPassword(passwordEncoder.encode(contrasenaOriginal));
		usuario.setNombre("Nombre original");
		usuario.setApellidos("Apellidos originales");
		usuario.setRol("CLIENTE");

		usuario = usuarioRepository.save(usuario);

		String token = jwtService.generarToken(usuario.getEmail(), usuario.getRol());

		String datosActualizados = """
				{
				  "nombre": "Nombre actualizado",
				  "apellidos": "Apellidos actualizados",
				  "email": "otro.email@example.com",
				  "password": "NuevaClave123!",
				  "rol": "ADMIN"
				}
				""";

		mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put("/perfil")
				.cookie(new Cookie("authToken", token)).with(csrf()).contentType(MediaType.APPLICATION_JSON)
				.content(datosActualizados)).andExpect(status().isOk());

		Usuario usuarioGuardado = usuarioRepository.findByEmail("seguridad.perfil@example.com").orElseThrow();
		
		org.junit.jupiter.api.Assertions.assertEquals(
		        "Nombre actualizado",
		        usuarioGuardado.getNombre());

		org.junit.jupiter.api.Assertions.assertEquals(
		        "Apellidos actualizados",
		        usuarioGuardado.getApellidos());

		org.junit.jupiter.api.Assertions.assertEquals("seguridad.perfil@example.com", usuarioGuardado.getEmail());

		org.junit.jupiter.api.Assertions.assertEquals("CLIENTE", usuarioGuardado.getRol());

		org.junit.jupiter.api.Assertions
				.assertTrue(passwordEncoder.matches(contrasenaOriginal, usuarioGuardado.getPassword()));
	}

	@Test
	void usuarioNoAutenticadoNoPuedeActualizarPerfil() throws Exception {
		String datosActualizados = """
				{
				  "nombre": "Nombre nuevo",
				  "apellidos": "Apellidos nuevos"
				}
				""";

		mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put("/perfil").with(csrf())
				.contentType(MediaType.APPLICATION_JSON).content(datosActualizados)).andExpect(status().isForbidden());
	}

	@Test
	void actualizarPerfilRechazaNombreVacio() throws Exception {
		Usuario usuario = new Usuario();
		usuario.setEmail("perfil.invalido@example.com");
		usuario.setPassword(passwordEncoder.encode("ClaveSegura123!"));
		usuario.setNombre("Nombre original");
		usuario.setApellidos("Apellidos originales");
		usuario.setRol("CLIENTE");

		usuario = usuarioRepository.save(usuario);

		String token = jwtService.generarToken(usuario.getEmail(), usuario.getRol());

		String datosActualizados = """
				{
				  "nombre": "",
				  "apellidos": "Apellidos nuevos"
				}
				""";

		mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put("/perfil")
				.cookie(new Cookie("authToken", token)).with(csrf()).contentType(MediaType.APPLICATION_JSON)
				.content(datosActualizados)).andExpect(status().isBadRequest());
	}

}