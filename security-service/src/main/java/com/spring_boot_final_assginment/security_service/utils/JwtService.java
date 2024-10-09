package com.spring_boot_final_assginment.security_service.utils;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtService {

    public static String SECRET;

    @Value("${JWT_SECRET_KEY}")
    public void setSecret(String secret) {
        this.SECRET = secret;
    }

    public void validateToken(final String token) {
        System.out.println(3);
        Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
    }


    public String generateToken(String userName) {
        System.out.println("generate token");
        Map<String, Object> claims = new HashMap<>();
        System.out.println("gen2");
        return createToken(claims, userName);
    }

    private String createToken(Map<String, Object> claims, String userName) {
        System.out.println("create");
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}