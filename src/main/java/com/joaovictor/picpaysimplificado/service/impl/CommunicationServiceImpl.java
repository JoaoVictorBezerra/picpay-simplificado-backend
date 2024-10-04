package com.joaovictor.picpaysimplificado.service.impl;

import com.joaovictor.picpaysimplificado.service.interfac.CommunicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Log4j2
public class CommunicationServiceImpl implements CommunicationService {
    private final ChannelIntegrationService channelIntegrationService;

    @Override
    public void sendSmsNotification(String toNumber, String message) {
        channelIntegrationService.sendMessageTwilio(toNumber, message);
    }

    @Override
    public void sendEmailNotification(String toEmail, String subject, String body) {
        channelIntegrationService.sendEmailTwilio(toEmail, subject, body);
    }
}
