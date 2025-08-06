// Paquete donde se encuentra la clase de configuración de seguridad
package com.tecdesoftware.market.config;

// Importaciones de Spring Framework necesarias para configurar la seguridad
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// Indica que esta clase es una clase de configuración (Spring Bean)
@Configuration

// Habilita la seguridad web de Spring Security en la aplicación
@EnableWebSecurity
public class SecurityConfig {

    // Inyección de dependencia del filtro JWT personalizado
    @Autowired
    private JwtFilter jwtFilter;

    // Bean que define el algoritmo para encriptar contraseñas
    // En este caso, se utiliza BCrypt, un estándar de seguridad para hashear contraseñas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Bean que define la cadena de filtros de seguridad (SecurityFilterChain)
    // Aquí se configuran las reglas de acceso, protección CSRF, filtros, etc.
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Desactiva la protección CSRF (porque es una API REST, no usa sesiones ni formularios web)
                .authorizeHttpRequests(auth -> auth
                        // Define las rutas públicas (no requieren autenticación)
                        .requestMatchers("/auth/**", "/swagger-ui/", "/v3/**", "/products/**").permitAll()
                        // Cualquier otra ruta requiere autenticación (token JWT válido)
                        .anyRequest().authenticated()
                )
                // Configura la política de sesiones como STATELESS (sin sesiones)
                // Esto es fundamental para APIs REST con autenticación por token (JWT)
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // Añade el filtro JWT personalizado antes del filtro estándar de autenticación de Spring
        // Esto asegura que el token JWT se valide antes de intentar autenticar el usuario
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        // Retorna la configuración de seguridad ya construida
        return http.build();
    }
}
