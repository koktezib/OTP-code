package org.example.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;

@Component
public class JwtUtils {
    @Value("${jwt.secret}")
    private String jwtSecret;

    private final Key signingKey;

    public JwtUtils(@Value("${jwt.secret}") String jwtSecret) {
        byte[] keyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
        this.signingKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean validateJwtToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(signingKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public Claims getClaimsFromJwtToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Integer getUserIdFromJwtToken(String token) {
        return getClaimsFromJwtToken(token).get("id", Integer.class);
    }

    public String getUserRoleFromJwtToken(String token) {
        return getClaimsFromJwtToken(token).get("role", String.class);
    }

    public String getUserLoginFromJwtToken(String token) {
        return getClaimsFromJwtToken(token).getSubject();
    }
}
