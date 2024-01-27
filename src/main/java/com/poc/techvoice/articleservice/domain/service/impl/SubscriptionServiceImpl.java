package com.poc.techvoice.articleservice.domain.service.impl;

import com.poc.techvoice.articleservice.domain.entities.Category;
import com.poc.techvoice.articleservice.domain.entities.User;
import com.poc.techvoice.articleservice.domain.entities.dto.NotificationDto;
import com.poc.techvoice.articleservice.domain.service.SubscriptionService;
import com.poc.techvoice.articleservice.domain.service.notification.ArticleHub;
import com.poc.techvoice.articleservice.domain.service.notification.Observer;
import com.poc.techvoice.articleservice.domain.service.notification.Subscriber;
import com.poc.techvoice.articleservice.external.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final ArticleHub articleHub;
    private final CategoryRepository categoryRepository;
    private final RoutingFactory routingFactory;

    @PostConstruct
    private void initializeSubscribers() {
        Set<Category> articleCategories = categoryRepository.findAllCategories();
        articleCategories.forEach(category -> {
            Integer categoryId = category.getId();
            List<User> readers = category.getSubscribedUsers();
            List<Observer> subscribers = readers.stream()
                    .map(reader -> new Subscriber(routingFactory, reader.getEmail(), reader.getNotificationChannel()))
                    .collect(Collectors.toList());

            subscribers.forEach(subscriber -> articleHub.registerObserver(subscriber, categoryId));
        });

        log.info("Fetched initial subscribers");
    }


    @Async
    @Override
    public void notifySubscriber(NotificationDto notification, Integer categoryId) {
        articleHub.setNotification(notification, categoryId);
    }
}
