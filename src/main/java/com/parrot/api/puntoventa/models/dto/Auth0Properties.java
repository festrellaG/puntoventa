package com.parrot.api.puntoventa.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ConfigurationProperties(prefix = "auth0")
public class Auth0Properties {
    private String clientId;
    private String clientSecret;
    private String audience;
    private String tokenUrl;

}
