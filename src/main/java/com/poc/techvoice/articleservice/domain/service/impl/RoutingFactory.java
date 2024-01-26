package com.poc.techvoice.articleservice.domain.service.impl;

import com.poc.techvoice.articleservice.domain.enums.Channel;
import com.poc.techvoice.articleservice.domain.service.RoutingService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RoutingFactory {

    private final EmailService emailService;

    public RoutingService getRoutingService(Channel channel) {

        // add other possible notification channels in else if blocks
        if (Channel.EMAIL.equals(channel)) {
            return emailService;
        } else {
            return null;
        }

    }
}
