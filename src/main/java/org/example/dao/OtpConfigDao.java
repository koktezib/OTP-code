package org.example.dao;

import org.example.model.OtpConfig;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class OtpConfigDao {
    private final JdbcTemplate jdbc;

    public OtpConfigDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public OtpConfig getConfig() {
        return jdbc.queryForObject(
                "SELECT id, code_length AS codeLength, ttl_seconds AS ttlSeconds FROM otp_config WHERE id = TRUE",
                new BeanPropertyRowMapper<>(OtpConfig.class)
        );
    }

    public void updateConfig(int codeLength, int ttlSeconds) {
        jdbc.update("""
            UPDATE otp_config
               SET code_length = ?, ttl_seconds = ?
             WHERE id = TRUE
            """,
                codeLength, ttlSeconds
        );
    }
}
