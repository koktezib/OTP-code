package org.example.controller;

import org.example.model.OtpCode;
import org.example.service.OtpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final OtpService otp;

    public UserController(OtpService otp) {
        this.otp = otp;
    }

    @PostMapping("/generate")
    public OtpCode generate(@RequestBody Map<String, String> body, Principal p) {
        String opId = body.get("operationId");
        int userId = Integer.parseInt(p.getName());
        logger.info("Пользователь {} запросил генерацию OTP для операции {}", userId, opId);
        OtpCode code = otp.generate(userId, opId);
        logger.info("OTP сгенерирован: {} для пользователя {}", code.getCode(), userId);
        return code;
    }

    @PostMapping("/validate")
    public ResponseEntity<?> validate(@RequestBody Map<String, String> body, Principal p) {
        String opId = body.get("operationId");
        String code = body.get("code");
        int userId = Integer.parseInt(p.getName());
        logger.info("Пользователь {} пытается подтвердить OTP для операции {} с кодом {}", userId, opId, code);
        boolean ok = otp.validate(userId, opId, code);
        if (ok) {
            logger.info("OTP код принят для пользователя {} и операции {}", userId, opId);
            return ResponseEntity.ok(Map.of("status", "OK"));
        } else {
            logger.warn("Неверный OTP код от пользователя {} для операции {}", userId, opId);
            return ResponseEntity.status(400).body(Map.of("status", "BAD_CODE"));
        }
    }
}
