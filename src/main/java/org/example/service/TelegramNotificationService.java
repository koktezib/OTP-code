package org.example.service;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
public class TelegramNotificationService {
    @Value("${telegram.bot.token}")    private String botToken;
    @Value("${telegram.chat.id}")     private String chatId;
    @Value("${telegram.api.url}")     private String apiUrl;

    public void sendCode(String ignoredLogin, String code) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            URI uri = new URIBuilder(apiUrl + botToken + "/sendMessage")
                    .addParameter("chat_id", chatId)
                    .addParameter("text", "Ваш OTP-код: " + code)
                    .build();
            HttpGet get = new HttpGet(uri);
            try (CloseableHttpResponse resp = client.execute(get)) {
                if (resp.getStatusLine().getStatusCode() != 200) {
                    throw new RuntimeException("Telegram API error: " + resp.getStatusLine());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Не удалось отправить в Telegram", e);
        }
    }
}
