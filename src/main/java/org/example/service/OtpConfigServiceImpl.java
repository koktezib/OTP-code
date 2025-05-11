package org.example.service;

import org.example.dao.OtpConfigDao;
import org.example.model.OtpConfig;
import org.springframework.stereotype.Service;

@Service
public class OtpConfigServiceImpl implements OtpConfigService {
    private final OtpConfigDao dao;

    public OtpConfigServiceImpl(OtpConfigDao dao) {
        this.dao = dao;
    }

    @Override
    public OtpConfig getConfig() {
        return dao.getConfig();
    }

    @Override
    public void updateConfig(int codeLength, int ttlSeconds) {
        dao.updateConfig(codeLength, ttlSeconds);
    }
}
