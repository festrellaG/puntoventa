package com.parrot.api.puntoventa.services;

import com.parrot.api.puntoventa.exception.InvalidTokenException;
import com.parrot.api.puntoventa.models.dto.AuthData;
import com.parrot.api.puntoventa.models.dto.TokenValidationResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface Auth0Service {

    AuthData getAccessToken();

    TokenValidationResponse validateToken(HttpServletRequest request) throws InvalidTokenException;
}
