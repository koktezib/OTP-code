package org.example.service;

public interface AuthService {
    void register(String login, String rawPassword);
    String login(String login, String rawPassword);  // возвращает JWT
}