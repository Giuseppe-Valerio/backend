package com.valeriobarbershop.backend.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.io.Decoders;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils implements InitializingBean {

    @Value("${app.jwt.secret}")
    private String secretString;

    @Value("${app.jwt.expiration}")
    private long expiration; // Ora caricato da application.properties

    private Key SECRET_KEY;

    @Override
    public void afterPropertiesSet() {
        if (secretString == null || secretString.isBlank()) {
            throw new IllegalArgumentException("JWT secret non configurato in application.properties");
        }
        // Decodifica la chiave Base64
        byte[] keyBytes = Decoders.BASE64.decode(secretString);
        this.SECRET_KEY = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractEmail(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    public boolean validateToken(String token) {
        try {
            getClaimsFromToken(token);
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            // Log dell'errore (aggiungi un logger se necessario)
            System.err.println("Token validation error: " + ex.getMessage());
            return false;
        }
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Claims extractAllClaims(String token) {
        return getClaimsFromToken(token);
    }
}