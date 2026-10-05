package com.ecommerce.auth;


import com.ecommerce.user.AppUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {
    @Value("${jwt.secretKey}")
    private String secretKey;

    private SecretKey createSecretKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateJwtToken(String userId) {
        return Jwts.builder()
                .subject(userId)
                .claim("UserId", userId)
                .signWith(createSecretKey())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 10))
                .compact();
    }


    public String verifyToken(String token) {
        Claims claims = Jwts
                .parser()
                .verifyWith(createSecretKey())
                .build().parseSignedClaims(token)
                .getPayload();

        return claims.getSubject();
    }
}
