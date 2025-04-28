package com.parrot.api.puntoventa.controller;

import com.parrot.api.puntoventa.models.dto.Auth0Credentials;
import com.parrot.api.puntoventa.models.dto.AuthData;
import com.parrot.api.puntoventa.models.dto.TokenValidationResponse;
import com.parrot.api.puntoventa.services.Auth0Service;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/auth0")
public class Auth0Controller {

    @Autowired
    private Auth0Service auth0Service;

    @GetMapping("/token")
    public ResponseEntity<Object> getToken() {
        try {
            AuthData authData = auth0Service.getAccessToken();
            return ResponseEntity.ok(authData);
        } catch (Exception e) {
            log.error("Error al obtener el token: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AuthData("Error: " + e.getMessage()));
        }
    }

    @GetMapping("/validate-token")
    public ResponseEntity<Object> validateToken(HttpServletRequest request) {
        try {
            TokenValidationResponse response = auth0Service.validateToken(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error al obtener el token: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Token inválido o expirado");
        }
    }


}


