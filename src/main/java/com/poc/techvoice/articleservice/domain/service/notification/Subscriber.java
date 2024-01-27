package com.poc.techvoice.articleservice.domain.service.notification;

import com.poc.techvoice.articleservice.domain.entities.dto.NotificationDto;
import com.poc.techvoice.articleservice.domain.enums.Channel;
import com.poc.techvoice.articleservice.domain.service.RoutingService;
import com.poc.techvoice.articleservice.domain.service.impl.RoutingFactory;

public class Subscriber implements Observer {

    private final RoutingFactory routingFactory;
    private NotificationDto notification;
    private String email;
    private Channel notificationChannel;

    public Subscriber(RoutingFactory routingFactory, String email, Channel channel) {
        this.routingFactory = routingFactory;
        this.email = email;
        this.notificationChannel = channel;
    }

    @Override
    public void update(NotificationDto notification) {
        this.notification = notification;
        sendNotification();
    }

    private void sendNotification() {

        RoutingService routingService = routingFactory.getRoutingService(notificationChannel);
        routingService.notifyChange(notification, email);

    }

}
