package com.taller_mecanico.auth_service.security;

import java.security.Key;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    public String generateToken(String username, String rol) {
        String rolNormalizado = normalizarRol(rol);
        logger.info("event=jwt_generate_start username={} rol={}", username, rolNormalizado);

        Key key = Keys.hmacShaKeyFor(secret.getBytes());

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .claim("rol", rolNormalizado)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        logger.info("event=jwt_generate_success username={} rol={} expirationMs={}", username, rolNormalizado, expiration);
        return token;
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

        String username = claims.getSubject();
        logger.info("event=jwt_extract_username_success username={}", username);
        return username;
    }
}
