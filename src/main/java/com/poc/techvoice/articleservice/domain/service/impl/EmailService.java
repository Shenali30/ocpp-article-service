package com.poc.techvoice.articleservice.domain.service.impl;

import com.poc.techvoice.articleservice.application.constants.AppConstants;
import com.poc.techvoice.articleservice.application.constants.LoggingConstants;
import com.poc.techvoice.articleservice.application.enums.ResponseEnum;
import com.poc.techvoice.articleservice.domain.entities.dto.NotificationDto;
import com.poc.techvoice.articleservice.domain.enums.Action;
import com.poc.techvoice.articleservice.domain.service.RoutingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailService implements RoutingService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void notifyChange(NotificationDto notification, String receiverEmail) {

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(receiverEmail);
            message.setSubject(AppConstants.EMAIL_SUBJECT);
            message.setText(String.format(AppConstants.EMAIL_BODY, notification.getArticleTitle(),
                    getActionVerb(notification.getAction()), notification.getCategory()));
            mailSender.send(message);

        } catch (Exception ex) {
            log.error(LoggingConstants.EMAIL_SEND_ERROR, ex.getMessage(), ResponseEnum.INTERNAL_ERROR.getDesc(), ex.getStackTrace());
        }
    }

    private String getActionVerb(Action action) {
        if (Action.PUBLISH.equals(action)) {
            return "published";
        } else if (Action.UPDATE.equals(action)) {
            return "updated";
        } else if (Action.DELETE.equals(action)) {
            return "deleted";
        } else {
            return null;
        }
    }
}
