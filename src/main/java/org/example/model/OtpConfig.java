package org.example.model;

public class OtpConfig {
    private boolean id;
    private int codeLength;
    private int ttlSeconds;

    public OtpConfig() {}

    public OtpConfig(boolean id, int codeLength, int ttlSeconds) {
        this.id = id;
        this.codeLength = codeLength;
        this.ttlSeconds = ttlSeconds;
    }

    // getters/setters
    public boolean isId() { return id; }
    public void setId(boolean id) { this.id = id; }

    public int getCodeLength() { return codeLength; }
    public void setCodeLength(int codeLength) { this.codeLength = codeLength; }

    public int getTtlSeconds() { return ttlSeconds; }
    public void setTtlSeconds(int ttlSeconds) { this.ttlSeconds = ttlSeconds; }
}
