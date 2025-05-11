package org.example.service;

import org.example.model.User;

import java.util.List;

public interface AdminService {
    List<User> listAllNonAdmin();
    void deleteUser(int userId);
    void updateOtpConfig(int codeLength, int ttlSeconds);
}