package org.example.scheduler;

import org.example.dao.OtpCodeDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class OtpExpirationScheduler {
    private final OtpCodeDao otpCodeDao;

    @Value("${otp.ttl.seconds}")
    private int ttlSeconds;

    public OtpExpirationScheduler(OtpCodeDao otpCodeDao) {
        this.otpCodeDao = otpCodeDao;
    }

    @Scheduled(fixedDelayString = "${otp.expire-scheduler.interval-ms}")
    public void expireOtps() {
        otpCodeDao.expireOldCodes(ttlSeconds);
    }
}
