package com.poc.techvoice.articleservice.domain.service.notification;

import com.poc.techvoice.articleservice.domain.entities.dto.NotificationDto;

public interface Observer {

    void update(NotificationDto notification);
}
