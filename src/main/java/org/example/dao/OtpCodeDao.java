package org.example.dao;

import org.example.enums.OtpStatus;
import org.example.model.OtpCode;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
public class OtpCodeDao {
    private final JdbcTemplate jdbc;

    public OtpCodeDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private final RowMapper<OtpCode> codeRowMapper = new RowMapper<>() {
        @Override
        public OtpCode mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new OtpCode(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getString("operation_id"),
                    rs.getString("code"),
                    OtpStatus.valueOf(rs.getString("status")),
                    rs.getTimestamp("created_at").toLocalDateTime()
            );
        }
    };

    public void save(OtpCode code) {
        jdbc.update("""
            INSERT INTO otp_codes(user_id, operation_id, code, status)
            VALUES (?, ?, ?, ?)
            """,
                code.getUserId(),
                code.getOperationId(),
                code.getCode(),
                code.getStatus().name()
        );
    }

    public Optional<OtpCode> findActiveByUserAndOperation(int userId, String operationId) {
        return jdbc.query("""
                SELECT id, user_id, operation_id, code, status, created_at
                  FROM otp_codes
                 WHERE user_id = ? AND operation_id = ? AND status = 'ACTIVE'
            """,
                codeRowMapper,
                userId, operationId
        ).stream().findFirst();
    }

    public void updateStatus(int id, OtpStatus status) {
        jdbc.update("UPDATE otp_codes SET status = ? WHERE id = ?", status.name(), id);
    }

    public void expireOldCodes(int ttl) {
        String sql = """
            UPDATE otp_codes
            SET status = 'EXPIRED'
            WHERE status = 'ACTIVE'
              AND created_at < now() - (? || ' seconds')::interval
        """;
        jdbc.update(sql, String.valueOf(ttl));
    }
}
