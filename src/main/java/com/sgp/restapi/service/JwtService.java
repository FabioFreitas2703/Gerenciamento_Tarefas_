package com.sgp.restapi.service;

import java.util.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.io.Decoders;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Gerar token JWT
    public String gerarToken(String email) {

        Instant agora = Instant.now();

        return Jwts.builder()
                .subject(email)
                .issuedAt(Date.from(agora))
                .expiration(Date.from(agora.plus(expiration, ChronoUnit.MILLIS)))
                .signWith(getSigningKey())
                .compact();
    }

    // Extrair email do token
    public String extrairEmail(String token) {

        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    // Validar token JWT
    public boolean validarToken(String token) {

        try {
            extrairEmail(token);
            return true;

        } catch (io.jsonwebtoken.JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}