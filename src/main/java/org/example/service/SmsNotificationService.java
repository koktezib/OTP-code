package org.example.service;

import org.smpp.TCPIPConnection;
import org.smpp.Session;
import org.smpp.pdu.BindTransmitter;
import org.smpp.pdu.BindResponse;
import org.smpp.pdu.SubmitSM;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Connection;

@Service
public class SmsNotificationService {
    @Value("${smpp.host}")      private String host;
    @Value("${smpp.port}")      private int port;
    @Value("${smpp.system_id}") private String systemId;
    @Value("${smpp.password}")  private String password;
    @Value("${smpp.system_type}")   private String systemType;
    @Value("${smpp.source_addr}")   private String sourceAddr;

    public void sendCode(String destination, String code) {
        TCPIPConnection connection = new TCPIPConnection(host, port);
        try {
            Session session = new Session(connection);
            BindTransmitter bindPdu = new BindTransmitter();
            bindPdu.setSystemId(systemId);
            bindPdu.setPassword(password);
            bindPdu.setSystemType(systemType);
            BindResponse bindResp = (BindResponse) session.bind(bindPdu);
            if (bindResp.getCommandStatus() != 0) {
                throw new RuntimeException("SMPP Bind failed with status " + bindResp.getCommandStatus());
            }
            SubmitSM submit = new SubmitSM();
            submit.setSourceAddr(sourceAddr);
            submit.setDestAddr(destination);
            submit.setShortMessage("Ваш OTP-код: " + code);
            session.submit(submit);
            session.unbind();
        } catch (Exception e) {
            throw new RuntimeException("Не удалось отправить SMS через SMPP", e);
        } finally {
            try {
                connection.close();
            } catch (IOException ioe) {
                System.err.println("Ошибка при закрытии SMPP-соединения: " + ioe.getMessage());
            }
        }
    }
}
