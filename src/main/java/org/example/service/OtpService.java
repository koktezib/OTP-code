package org.example.service;

import org.example.model.OtpCode;

public interface OtpService {
    OtpCode generate(int userId, String operationId);
    boolean validate(int userId, String operationId, String code);
}
