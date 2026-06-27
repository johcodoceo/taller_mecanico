package com.taller_mecanico.auth_service.security;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    public String generateToken(String username, String rol) {

        Key key = Keys.hmacShaKeyFor(secret.getBytes());

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .claim("rol", normalizarRol(rol))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    private String normalizarRol(String rol) {
        if (rol == null || rol.isBlank()) {
            return "ROLE_CLIENTE";
        }

        String rolNormalizado = rol.trim().toUpperCase();
        return rolNormalizado.startsWith("ROLE_") ? rolNormalizado : "ROLE_" + rolNormalizado;
    }

    public String extractUsername(String token) {

        Key key = Keys.hmacShaKeyFor(secret.getBytes());

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
}
