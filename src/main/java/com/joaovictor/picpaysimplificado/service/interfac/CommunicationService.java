package com.joaovictor.picpaysimplificado.service.interfac;

public interface CommunicationService {
    void sendSmsNotification(String toNumber, String message);
    void sendEmailNotification(String toEmail, String subject, String body);
}
