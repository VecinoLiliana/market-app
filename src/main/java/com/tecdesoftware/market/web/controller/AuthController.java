// Paquete donde se encuentra el controlador (estructura de carpetas del proyecto)
package com.tecdesoftware.market.web.controller;

// Importación del DTO que recibe las credenciales del usuario (correo, contraseña)
import com.tecdesoftware.market.domain.dto.LoginRequest;
// Importación del servicio de autenticación que gestiona la lógica del login y token
import com.tecdesoftware.market.domain.service.AuthService;

// Importaciones de Swagger/OpenAPI para documentar la API en Swagger UI
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

// Importaciones de Spring Framework necesarias para el controlador REST
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections; // Utilizado para crear un Map inmutable al devolver el token

// Habilita el acceso desde cualquier origen (Cross-Origin Resource Sharing)
@CrossOrigin(origins = "*")

// Indica que esta clase es un controlador REST (Spring)
@RestController

// Define la ruta base del controlador (/auth)
@RequestMapping("/auth")

// Etiqueta para Swagger que agrupa los endpoints bajo la categoría "Autenticación"
@Tag(name = "Autenticación", description = "Endpoint para iniciar sesión y obtener JWT")
public class AuthController {

    // Inyección de dependencia del servicio AuthService que contiene la lógica de autenticación
    @Autowired
    private AuthService authService;

    // Documentación de Swagger para describir el endpoint /auth/login
    @Operation(
            summary = "Iniciar sesión", // Título breve en Swagger UI
            description = "Autentica a un cliente por su correo electrónico y contraseña. Si es exitoso, devuelve un token JWT.", // Descripción detallada
            requestBody = @RequestBody( // Especifica cómo debe ser el cuerpo de la solicitud (LoginRequest)
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = LoginRequest.class), // Especifica que se espera un objeto de tipo LoginRequest
                            examples = @ExampleObject(value = """
                            {
                              "correo": "kepler@me.com",
                              "contrasena": "123456"
                            }
                            """) // Ejemplo de entrada para facilitar pruebas desde Swagger UI
                    )
            ),
            responses = { // Posibles respuestas del endpoint
                    @ApiResponse(responseCode = "200", description = "Login exitoso. Se devuelve el token JWT."),
                    @ApiResponse(responseCode = "401", description = "Credenciales inválidas"),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
            }
    )
    // Define que este método responde a peticiones POST en /auth/login
    @PostMapping("/login")
    public ResponseEntity<?> login(@org.springframework.web.bind.annotation.RequestBody LoginRequest request) {
        // Llama al servicio de autenticación pasando el correo y contraseña
        String token = authService.login(request.getCorreo(), request.getContrasena());

        // Si el token es null, significa que las credenciales son incorrectas
        if (token == null) {
            // Devuelve una respuesta HTTP 401 Unauthorized con el mensaje "Credenciales inválidas"
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }

        // Si el login fue exitoso, devuelve el token en un Map {"token": token} con status 200 OK
        return ResponseEntity.ok(Collections.singletonMap("token", token));
    }
}
