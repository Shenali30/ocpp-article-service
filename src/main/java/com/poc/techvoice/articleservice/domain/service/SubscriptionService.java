package com.poc.techvoice.articleservice.domain.service;

import com.poc.techvoice.articleservice.domain.entities.dto.NotificationDto;

public interface SubscriptionService {

    void notifySubscriber(NotificationDto notification, Integer categoryId);
}
