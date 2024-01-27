package com.poc.techvoice.articleservice.domain.service;

import com.poc.techvoice.articleservice.domain.entities.dto.NotificationDto;

public interface RoutingService {
    void notifyChange(NotificationDto notification, String contact);
}
