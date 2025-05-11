package org.example.service;

import io.jsonwebtoken.security.Keys;
import org.example.dao.UserDao;
import org.example.enums.UserRole;
import org.example.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserDao userDao;
    private final PasswordEncoder encoder;
    private final String jwtSecret;
    private final long jwtExpirationMs;

    public AuthServiceImpl(UserDao userDao,
                           PasswordEncoder encoder,
                           @Value("${jwt.secret}") String jwtSecret,
                           @Value("${jwt.expiration-ms}") long jwtExpirationMs) {
        this.userDao = userDao;
        this.encoder = encoder;
        this.jwtSecret = jwtSecret;
        this.jwtExpirationMs = jwtExpirationMs;
    }

    @Override
    public void register(String login, String rawPassword) {
        if (userDao.findByLogin(login).isPresent()) {
            throw new RuntimeException("Пользователь уже существует");
        }
        UserRole role = (userDao.countAdmins() == 0 ? UserRole.ADMIN : UserRole.USER);
        String hash = encoder.encode(rawPassword);
        userDao.save(new User(0, login, hash, role));
    }

    @Override
    public String login(String login, String rawPassword) {
        User user = userDao.findByLogin(login)
                .orElseThrow(() -> new RuntimeException("Неверный логин/пароль"));
        if (!encoder.matches(rawPassword, user.getPasswordHash())) {
            throw new RuntimeException("Неверный логин/пароль");
        }

        Date now = new Date();
        Date exp = new Date(now.getTime() + jwtExpirationMs);

        byte[] keyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
        Key signingKey = Keys.hmacShaKeyFor(keyBytes);

        return Jwts.builder()
                .setSubject(user.getLogin())
                .claim("id", user.getId())
                .claim("role", user.getRole().name())
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }
}
