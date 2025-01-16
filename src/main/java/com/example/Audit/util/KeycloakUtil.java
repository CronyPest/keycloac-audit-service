package com.example.Audit.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class KeycloakUtil {

    public String getDomainUserName(Authentication authentication) {
        Jwt token = (Jwt) authentication.getPrincipal();
        Map<String, Object> claims = token.getClaims();

        return claims.get("preferred_username").toString();
    }
}
