package org.example.model;

import org.example.enums.OtpStatus;

import java.time.LocalDateTime;

public class OtpCode {
    private int id;
    private int userId;
    private String operationId;
    private String code;
    private OtpStatus status;
    private LocalDateTime createdAt;

    public OtpCode() {}

    public OtpCode(int id, int userId, String operationId, String code, OtpStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.operationId = operationId;
        this.code = code;
        this.status = status;
        this.createdAt = createdAt;
    }

    // getters/setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getOperationId() { return operationId; }
    public void setOperationId(String operationId) { this.operationId = operationId; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public OtpStatus getStatus() { return status; }
    public void setStatus(OtpStatus status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}