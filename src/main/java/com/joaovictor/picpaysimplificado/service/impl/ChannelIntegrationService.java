package com.joaovictor.picpaysimplificado.service.impl;

import com.joaovictor.picpaysimplificado.exceptions.external.TwilioInitProblemException;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.Account;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
@Log4j2
public class ChannelIntegrationService {
    @Value("${twilio.account.sid}")
    private String ACCOUNT_SID;
    @Value("${twilio.account.token}")
    private String AUTH_TOKEN;
    @Value("${twilio.account.number}")
    private String TWILIO_FROM_NUMBER;
    @Value("${sendgrid.api.key}")
    private String SENDGRID_API_KEY;
    @Value("${sendgrid.account.email}")
    private String SENDGRID_ACCOUNT_EMAIL;

    public boolean checkTwilioStatus() {
        try {
            log.info("CONFIGURATION:: Start to check Twilio service");
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
            Account account = Account.fetcher(ACCOUNT_SID).fetch();
            return account != null && account.getStatus().equals(Account.Status.ACTIVE);
        } catch (Exception exception) {
            log.error("CONFIGURATION:: Error to configure Twilio");
            return false;
        }
    }

    public void sendMessageTwilio(String toNumber, String message) {
        log.info("TWILIO:: Init send message method");
        try {
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
            var response = Message.creator(new PhoneNumber(toNumber), new PhoneNumber(TWILIO_FROM_NUMBER), message).create();
            if (response.getErrorCode() != null) {
                log.info("TWILIO:: An error occurred while sending SMS");
                throw new TwilioInitProblemException("An error occurred while sending SMS to Twilio");
            }
        } catch (TwilioInitProblemException exception) {
            throw exception;
        } catch (Exception exception) {
            log.info("TWILIO:: An error occurred while sending SMS");
            throw exception;
        }
    }

    public void sendEmailTwilio(String toEmail, String subject, String body) {
        log.info("SEND GRID:: Init send email method");
        Email from = new Email(SENDGRID_ACCOUNT_EMAIL);
        Email to = new Email(toEmail);
        Content content = new Content("text/plain", body);
        Mail mail = new Mail(from, subject, to, content);
        SendGrid sg = new SendGrid(SENDGRID_API_KEY);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            log.info("SENDGRID:: E-mail successfully sent {}", response);
        } catch (IOException ex) {
            log.error("SEND GRID:: An error occurred while sending email");
        }
    }
}
