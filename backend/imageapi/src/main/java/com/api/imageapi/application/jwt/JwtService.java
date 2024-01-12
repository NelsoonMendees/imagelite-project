package com.api.imageapi.application.jwt;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.api.imageapi.domain.AccessToken;
import com.api.imageapi.domain.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final SecretKeyGenerator keyGenerator;

    public AccessToken generateToken(User user) {

        var key = keyGenerator.getKey();
        var expirationDate = generateExpirationDate();
        var claims = generateTokenClaims(user);

        String token = Jwts.builder().signWith(key).subject(user.getEmail()).expiration(expirationDate)
                .claims(claims).compact();

        return new AccessToken(token);

    }

    private Date generateExpirationDate() {
        var expirationMinutes = 60;

        LocalDateTime now = LocalDateTime.now().plusMinutes(expirationMinutes);
        return Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
    }

    private Map<String, Object> generateTokenClaims(User user) {
        Map<String, Object> claims = new HashMap<>();

        claims.put("name", user.getName());
        return claims;
    }

    public String getEmailFromToken(String tokenJwt) {
        try {
            JwtParser build = Jwts.parser().verifyWith(keyGenerator.getKey()).build();

            Jws<Claims> jwsClaims = build.parseSignedClaims(tokenJwt);

            Claims claims = jwsClaims.getPayload();

            return claims.getSubject();

        } catch (JwtException e) {
            throw new InvalidTokenException(e.getMessage());
        }
    }
}
