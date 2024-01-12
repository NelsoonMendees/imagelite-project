package com.api.imageapi.application.jwt;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

@Component
public class SecretKeyGenerator {
    
    private SecretKey key;

    public SecretKey getKey() {
        if (key == null) {
            key = Jwts.SIG.HS256.key().build();
        }

        return key;
    }

}
