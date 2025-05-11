package org.example.service;

import org.example.dao.UserDao;
import org.example.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    private final UserDao userDao;
    private final OtpConfigService configService;

    public AdminServiceImpl(UserDao userDao,
                            OtpConfigService configService) {
        this.userDao = userDao;
        this.configService = configService;
    }

    @Override
    public List<User> listAllNonAdmin() {
        return userDao.findAllNonAdmin();
    }

    @Override
    @Transactional
    public void deleteUser(int userId) {
        userDao.deleteById(userId);
    }

    @Override
    public void updateOtpConfig(int codeLength, int ttlSeconds) {
        configService.updateConfig(codeLength, ttlSeconds);
    }
}