package com.parrot.api.puntoventa.services.impl;

import com.parrot.api.puntoventa.exception.InvalidTokenException;
import com.parrot.api.puntoventa.models.dto.Auth0Properties;
import com.parrot.api.puntoventa.models.dto.AuthData;
import com.parrot.api.puntoventa.models.dto.SecurityProperties;
import com.parrot.api.puntoventa.models.dto.TokenValidationResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.core5.http.ContentType;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;
import com.parrot.api.puntoventa.services.Auth0Service;

import java.io.IOException;
import java.time.Instant;
import java.util.Map;

@Slf4j
@Service
public class Auth0Serviceimpl implements Auth0Service {

    @Autowired
    private Auth0Properties auth0Properties;

    @Autowired
    private JwtDecoder jwtDecoder;

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public AuthData getAccessToken() {
        AuthData auth = new AuthData();
        auth.setToken(getAccessToken(auth0Properties.getClientId(), auth0Properties.getClientSecret()));
       return auth;
    }

    public String getAccessToken(String clientId, String clientSecret) {

        if (clientId == null) {
            return "No se ingreso client_id";
        }

        if (clientSecret == null) {
            return "No se ingreso client_secret";
        }

        JSONObject requestBody = new JSONObject();
        requestBody.put("client_id", clientId);
        requestBody.put("client_secret", clientSecret);
        requestBody.put("audience", auth0Properties.getAudience());
        requestBody.put("grant_type", "client_credentials");

        try {
            String response = Request.post(auth0Properties.getTokenUrl())
                    .bodyString(requestBody.toString(), ContentType.APPLICATION_JSON)
                    .execute()
                    .returnContent()
                    .asString();

            JSONObject jsonResponse = new JSONObject(response);
            return jsonResponse.getString("access_token");

        } catch (IOException e) {
            log.error("Error de IO al comunicarse con Auth0", e);
            throw new RuntimeException("Error de comunicación con Auth0: " + e.getMessage());
        } catch (Exception e) {
            log.error("Error inesperado al obtener el token de Auth0", e);
            throw new RuntimeException("Error inesperado al obtener el token de Auth0: " + e.getMessage());
        }
    }

    @Override
    public TokenValidationResponse validateToken(HttpServletRequest request) throws InvalidTokenException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new InvalidTokenException("Token no proporcionado o en formato incorrecto");
        }

        String token = authHeader.substring(7);

        try {
            Jwt jwt = jwtDecoder.decode(token);

            // Validaciones adicionales que puedas necesitar
            if (!isTokenValid(jwt)) {
                throw new InvalidTokenException("Token inválido");
            }

            // Crear objeto de respuesta con la información necesaria
            TokenValidationResponse response = new TokenValidationResponse();
            response.setValid(true);
            response.setSub(jwt.getClaimAsString("sub"));
            response.setTokenValue(jwt.getTokenValue());

            return response;

        } catch (JwtException e) {
            throw new InvalidTokenException("Token inválido o expirado: " + e.getMessage());
        }
    }

    private boolean isTokenValid(Jwt jwt) {
        // Validaciones personalizadas adicionales
        Instant expirationTime = jwt.getExpiresAt();
        Instant now = Instant.now();

        if (expirationTime == null || now.isAfter(expirationTime)) {
            return false;
        }

        // Verificar el emisor del token
        String issuer = jwt.getClaimAsString("iss");
        if (issuer == null || !issuer.equals(securityProperties.getJwtUrl())) {
            return false;
        }

        return true;
    }
}
