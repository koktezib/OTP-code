CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    login VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL CHECK (role IN ('USER','ADMIN'))
    );

CREATE TABLE IF NOT EXISTS otp_config (
    id BOOLEAN PRIMARY KEY CHECK (id IS TRUE),
    code_length INTEGER NOT NULL,
    ttl_seconds INTEGER NOT NULL
    );

INSERT INTO otp_config(id, code_length, ttl_seconds)
VALUES (TRUE, 6, 300)
    ON CONFLICT (id) DO NOTHING;

CREATE TABLE IF NOT EXISTS otp_codes (
    id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES users(id) ON DELETE CASCADE,
    operation_id VARCHAR(100),
    code VARCHAR(10) NOT NULL,
    status VARCHAR(10) NOT NULL CHECK (status IN ('ACTIVE','EXPIRED','USED')),
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
    );

CREATE INDEX IF NOT EXISTS idx_otp_status ON otp_codes(status);
CREATE INDEX IF NOT EXISTS idx_otp_created ON otp_codes(created_at);
