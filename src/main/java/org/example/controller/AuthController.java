package org.example.controller;

import org.example.security.JwtUtils;
import org.example.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthService auth;
    private final JwtUtils jwt;

    public AuthController(AuthService auth, JwtUtils jwt) {
        this.auth = auth;
        this.jwt = jwt;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String,String> body) {
        String login = body.get("login");
        logger.info("Запрос регистрации пользователя: {}", login);
        auth.register(login, body.get("password"));
        logger.info("Пользователь {} успешно зарегистрирован", login);
        return ResponseEntity.ok(Map.of("status","registered"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String,String> body) {
        String login = body.get("login");
        logger.info("Запрос аутентификации пользователя: {}", login);
        String token = auth.login(login, body.get("password"));
        logger.info("Аутентификация успешна, токен выдан для пользователя {}", login);
        return ResponseEntity.ok(Map.of("token", token));
    }
}
