package com.pawmart.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {
    private static final String SECRET_KEY =
            "mySecretKeyForPawMartJwtAuthentication2026VerySecure";

    private final SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    public String generateToken(String email) {

        return Jwts.builder()
                .subject(email)                                                 // Nhét Email vào thẻ
                .issuedAt(new Date())                                           // Ngày cấp thẻ
                .expiration(new Date(System.currentTimeMillis() + 86400000))    // Ngày hết hạn (VD: sau 24h)
                .signWith(key, SignatureAlgorithm.HS256)                        // ĐÓNG DẤU BẢO MẬT (Ký bằng Secret Key)
                .compact();                                                     // Nén lại thành chuỗi JWT
    }
    public String extractEmail(String token) {

        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getSubject();
    }

    public boolean isTokenValid(String token, String email) {
        return extractEmail(token).equals(email);
    }
}
