package com.example.demo.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {

    // 密钥（生产环境应该放在配置文件里）
    //private static final Key KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    // 使用固定字符串生成密钥（至少 32 位）
    private static final Key KEY = Keys.hmacShaKeyFor("your-very-secure-secret-key-at-least-32-chars".getBytes());
    // 有效期：24小时
    private static final long EXPIRATION = 86400000;

    // 生成 Token
    public String createToken(Long userId, String username) {
        return Jwts.builder()
                .setSubject(username)
                .claim("userId", userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(KEY)
                .compact();
    }

    // 解析 Token
    public Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
