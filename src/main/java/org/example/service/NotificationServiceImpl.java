package org.example.service;

import org.example.dao.UserDao;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {
    private final EmailNotificationService emailSvc;
    private final SmsNotificationService smsSvc;
    private final TelegramNotificationService tgSvc;
    private final FileNotificationService fileSvc;
    private final UserDao userDao;

    public NotificationServiceImpl(EmailNotificationService emailSvc,
                                   SmsNotificationService smsSvc,
                                   TelegramNotificationService tgSvc,
                                   FileNotificationService fileSvc,
                                   UserDao userDao) {
        this.emailSvc = emailSvc;
        this.smsSvc = smsSvc;
        this.tgSvc = tgSvc;
        this.fileSvc = fileSvc;
        this.userDao = userDao;
    }

    @Override
    public void send(int userId, String code) {
        var user = userDao.findById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        emailSvc.sendCode(user.getLogin(), code);
        smsSvc.sendCode(user.getLogin(), code);
        tgSvc.sendCode(user.getLogin(), code);
        fileSvc.sendCode(user.getLogin(), code);
    }
}
