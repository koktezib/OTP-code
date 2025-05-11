package org.example.service;

import org.example.dao.OtpCodeDao;
import org.example.dao.OtpConfigDao;
import org.example.enums.OtpStatus;
import org.example.model.OtpCode;
import org.example.model.OtpConfig;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;
@Service
public class OtpServiceImpl implements OtpService {
    private final OtpConfigDao cfgDao;
    private final OtpCodeDao codeDao;
    private final NotificationService notifier;
    private final Random rnd = new Random();

    public OtpServiceImpl(OtpConfigDao cfgDao,
                          OtpCodeDao codeDao,
                          NotificationService notifier) {
        this.cfgDao = cfgDao;
        this.codeDao = codeDao;
        this.notifier = notifier;
    }

    @Override
    public OtpCode generate(int userId, String operationId) {
        OtpConfig cfg = cfgDao.getConfig();
        String code = rnd.ints(cfg.getCodeLength(), 0, 10)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
        OtpCode otp = new OtpCode(0, userId, operationId, code, OtpStatus.ACTIVE, LocalDateTime.now());
        codeDao.save(otp);
        notifier.send(userId, code);
        return otp;
    }

    @Override
    public boolean validate(int userId, String operationId, String code) {
        return codeDao.findActiveByUserAndOperation(userId, operationId)
                .map(otp -> {
                    boolean ok = otp.getCode().equals(code);
                    codeDao.updateStatus(otp.getId(), ok ? OtpStatus.USED : OtpStatus.EXPIRED);
                    return ok;
                }).orElse(false);
    }
}