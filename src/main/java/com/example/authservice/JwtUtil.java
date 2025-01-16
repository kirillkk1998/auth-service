package com.example.authservice;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "your-secret-key"; // Секретный ключ для подписи токена
    private static final long EXPIRATION_TIME = 864_000_000; // 10 дней в миллисекундах

    // Метод для создания JWT токена
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username) // Устанавливаем имя пользователя в токен
                .setIssuedAt(new Date()) // Устанавливаем дату создания токена
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Устанавливаем срок действия токена
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) // Подписываем токен с использованием секретного ключа
                .compact(); // Генерируем токен
    }

    // Метод для извлечения имени пользователя из токена
    public String extractUsername(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY) // Устанавливаем секретный ключ для проверки подписи
                .parseClaimsJws(token) // Парсим токен
                .getBody(); // Получаем данные из токена
        return claims.getSubject(); // Возвращаем имя пользователя
    }

    // Метод для проверки срока действия токена
    public boolean isTokenExpired(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        return claims.getExpiration().before(new Date()); // Проверяем, истек ли срок действия токена
    }
}