package com.skillio.api_v1.config.jwt;

import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Base64;

public class JwtKeyGenerator {
    public static void main(String[] args) {
        SecretKey key = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256); // Genera clave segura
        String base64Key = Base64.getEncoder().encodeToString(key.getEncoded()); // Codifica en Base64
        System.out.println("Nueva clave segura: " + base64Key);
    }
}
