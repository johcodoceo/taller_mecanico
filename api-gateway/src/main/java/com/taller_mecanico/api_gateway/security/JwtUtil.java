package com.taller_mecanico.api_gateway.security;

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

    public boolean validateToken(String token) {
        try {
            Key key = Keys.hmacShaKeyFor(secret.getBytes());

            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            logger.info("event=jwt_validation_success component=api_gateway");
            return true;
        } catch (Exception e) {
            logger.warn("event=jwt_validation_failed component=api_gateway exception={} message={}",
                    e.getClass().getSimpleName(), e.getMessage());
            return false;
        }
    }

    public String extractUsername(String token) {
        Key key = Keys.hmacShaKeyFor(secret.getBytes());

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        String username = claims.getSubject();
        logger.info("event=jwt_extract_username_success component=api_gateway username={}", username);
        return username;
    }
}
