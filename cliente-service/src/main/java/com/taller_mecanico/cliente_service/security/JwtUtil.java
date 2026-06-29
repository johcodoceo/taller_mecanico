package com.taller_mecanico.cliente_service.security;

import java.security.Key;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    @Value("${jwt.secret}")
    private String secret;

    private Key getKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public String extractRol(String token) {
        Object rol = extractAllClaims(token).get("rol");
        return rol == null ? null : rol.toString();
    }

    public boolean isTokenValid(String token) {
        try {
            extractAllClaims(token);
            logger.info("event=jwt_validation_success");
            return true;
        } catch (Exception e) {
            logger.warn("event=jwt_validation_failed exception={} message={}",
                    e.getClass().getSimpleName(), e.getMessage());
            return false;
        }
    }
}
