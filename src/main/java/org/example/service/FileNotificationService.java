package org.example.service;

import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

@Service
public class FileNotificationService {
    private final Path file = Path.of("otp_codes.txt");

    public void sendCode(String ignoredLogin, String code) {
        try (FileWriter fw = new FileWriter(file.toFile(), true)) {
            fw.write(code + System.lineSeparator());
        } catch (IOException e) {
            throw new RuntimeException("Не удалось сохранить код в файл", e);
        }
    }
}