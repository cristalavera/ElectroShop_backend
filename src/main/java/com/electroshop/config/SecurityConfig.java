package com.electroshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import com.electroshop.security.JwtAuthenticationFilter;
import com.electroshop.service.CustomUserDetailsService;
import com.electroshop.service.JwtService;

@Configuration
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter)
			throws Exception {

		CookieCsrfTokenRepository csrfRepository = CookieCsrfTokenRepository.withHttpOnlyFalse();

		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.formLogin(form -> form.disable()).httpBasic(basic -> basic.disable())
				.csrf(csrf -> csrf.csrfTokenRepository(csrfRepository)
				        .csrfTokenRequestHandler(
				                new org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler())
				        .ignoringRequestMatchers(
				                new org.springframework.security.web.util.matcher.AntPathRequestMatcher(
				                        "/auth/login", "POST"),
				                new org.springframework.security.web.util.matcher.AntPathRequestMatcher(
				                        "/usuarios", "POST")))

				.authorizeHttpRequests(auth -> auth
				        // Inicio de sesión y registro públicos
				        .requestMatchers(HttpMethod.POST, "/auth/login", "/usuarios").permitAll()

				        // Cierre de sesión: requiere autenticación
				        .requestMatchers(HttpMethod.POST, "/auth/logout").authenticated()

				        // Catálogo público: invitados y usuarios autenticados
				        .requestMatchers(HttpMethod.GET, "/productos", "/productos/**").permitAll()

				        // Gestión del catálogo: solo administradores
				        .requestMatchers(HttpMethod.POST, "/productos", "/productos/varios").hasRole("ADMIN")
				        .requestMatchers(HttpMethod.PUT, "/productos/**").hasRole("ADMIN")
				        .requestMatchers(HttpMethod.PATCH, "/productos/**").hasRole("ADMIN")
				        .requestMatchers(HttpMethod.DELETE, "/productos/**").hasRole("ADMIN")

				        // Los endpoints genéricos de usuarios quedan bloqueados
				        // hasta implementar las rutas específicas de perfil
				        .requestMatchers("/usuarios", "/usuarios/**").denyAll()

				        // El resto de rutas requiere autenticación
				        .anyRequest().authenticated())
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(
            JwtService jwtService,
            CustomUserDetailsService userDetailsService) {

        return new JwtAuthenticationFilter(jwtService, userDetailsService);
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}