package org.example.service;

import org.example.model.OtpConfig;

public interface OtpConfigService {
    OtpConfig getConfig();
    void updateConfig(int codeLength, int ttlSeconds);
}
