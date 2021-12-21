package com.example.email.services;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.email.model.DailyQuote;
import com.example.email.model.LocalWeather;
import com.example.email.model.Message;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;

/**
 * 
 * @author stefan
 *
 */
@Service
public class SendGridMailerService {

    @Value("${spring.sendgrid.api-key}")
    private String SENDGRID_API_KEY;

    @Value("${spring.sendgrid.api-email-from}")
    private String SENDGRID_API_EMAIL_FROM;

    @Autowired
    EmailWatcherService emailWatcherService;
    
    @Autowired
    DailyQuote dailyQuote;
    
    @Autowired
    LocalWeather localWeather;

    /**
     * 
     * @param message
     * @return
     * @throws IOException
     */
    public String sendTextEmail(Message message) throws IOException {

        Email from = new Email(SENDGRID_API_EMAIL_FROM);
        Content content = new Content(message.getContentType(),
                message.getBody()
                + " \r\n"+dailyQuote.getFormattedQuote()
                +" \r\n"+ localWeather.getFormattedWeather());

        Mail mail = new Mail();
        Personalization personalization = new Personalization();
        
        emailWatcherService.getToEmails().stream().forEach((email) -> {
            personalization.addTo(new Email(email));
        });
        
        // TODO: add bcc and cc if posted

        mail.setFrom(from);
        mail.addPersonalization(personalization);
        mail.addContent(content);
        mail.setSubject(message.getSubject());

        SendGrid sg = new SendGrid(SENDGRID_API_KEY);
        Request request = new Request();
        System.out.println(emailWatcherService.getToEmails().stream());
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            return response.getStatusCode() == 202
                    ? "Email was queued at sendgrid. " + response.getHeaders() + " Status code: "
                            + response.getStatusCode()
                    : "There was a problem with this email. " + response.getHeaders() + " Status code: "
                            + response.getStatusCode();
        } catch (IOException ex) {
            throw ex;
        }
    }
}