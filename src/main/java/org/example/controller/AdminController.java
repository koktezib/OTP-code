package org.example.controller;

import org.example.model.OtpConfig;
import org.example.model.User;
import org.example.service.AdminService;
import org.example.service.OtpConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    private final AdminService admin;
    private final OtpConfigService cfg;

    public AdminController(AdminService admin, OtpConfigService cfg) {
        this.admin = admin;
        this.cfg = cfg;
    }

    @GetMapping("/users")
    public List<User> list() {
        logger.info("Получен запрос на список всех не-админов");
        List<User> users = admin.listAllNonAdmin();
        logger.debug("Найдено пользователей: {}", users.size());
        return users;
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        logger.warn("Удаление пользователя с id = {}", id);
        admin.deleteUser(id);
        logger.info("Пользователь с id = {} удалён", id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/config")
    public OtpConfig getConfig() {
        logger.info("Получен запрос на получение конфигурации OTP");
        return cfg.getConfig();
    }

    @PutMapping("/config")
    public ResponseEntity<?> updateConfig(@RequestBody OtpConfig c) {
        logger.info("Обновление конфигурации OTP: длина кода = {}, TTL = {} секунд",
                c.getCodeLength(), c.getTtlSeconds());
        admin.updateOtpConfig(c.getCodeLength(), c.getTtlSeconds());
        logger.debug("Конфигурация OTP обновлена");
        return ResponseEntity.ok().build();
    }
}
