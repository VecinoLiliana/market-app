package com.tecdesoftware.market.domain.service;

import com.tecdesoftware.market.config.JwtUtil;
import com.tecdesoftware.market.persistence.crud.ClienteCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private ClienteCrudRepository clienteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    //lógica de negocio
    public String login(String correo, String contrasena) {
        return clienteRepository.findByCorreoElectronico(correo)
                // Revisa se coincide lo que da el usuario con lo que hay en la base de datos
                .filter(cliente -> passwordEncoder.matches(contrasena, cliente.getContrasena()))
                //Si se valida se genera el token
                .map(cliente -> jwtUtil.generateToken(cliente.getCorreoElectronico()))
                // no funciona
                .orElse(null);
    }
}