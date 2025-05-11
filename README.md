# OTP-code: Сервис выдачи и валидации OTP-кодов

Java + Spring Boot сервис для генерации, отправки и проверки OTP-кодов с возможностью управления пользователями и конфигурацией через административный API. Поддерживает email-уведомления, JWT-аутентификацию, PostgreSQL, и автоматическую деактивацию просроченных кодов.

---

## 🚀 Как запустить

### Требования
- Java 17+ (используется Java 22)
- Maven
- PostgreSQL
- SMTP-сервер (например Gmail или локальный SMTP-эмулятор)

### Шаги
1. Настрой `.env` или `application.properties`:
```properties
# ========== ?? ==========
spring.datasource.url=jdbc:postgresql://localhost:5432/otpdb
spring.datasource.username=postgres
spring.datasource.password=123456

# ========== JWT ==========
jwt.secret="k5J2vN8K0ix3W5h0ZcQ7+pYExkzQ8O6tFhQwY4/RJno="
jwt.expiration-ms=3600000  

# ========== Email ==========
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=?????_????????????
spring.mail.password=??????_????????????
spring.mail.protocol=smtp
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
spring.mail.properties.mail.smtp.starttls.required=true

email.from=?????_????????????

# ========== SMPP (SMS) ==========
smpp.host=localhost
smpp.port=2775
smpp.system_id=smppclient1
smpp.password=password
smpp.system_type=OTP
smpp.source_addr=OTPService

# ========== Telegram ==========
telegram.bot.token=7895112907:AAEttcGivcZ9-kJ2CFdqmf8ySGV1kDEgJME
telegram.chat.id=587818592
telegram.api.url=https://api.telegram.org/bot

# ========== Scheduler ==========
otp.ttl.seconds=100
otp.expire-scheduler.interval-ms=5000

spring.sql.init.mode=always
spring.sql.init.schema-locations=classpath:schema.sql


