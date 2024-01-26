package com.poc.techvoice.articleservice.domain.service.impl;

import com.poc.techvoice.articleservice.domain.entities.Category;
import com.poc.techvoice.articleservice.domain.entities.User;
import com.poc.techvoice.articleservice.domain.service.notification.ArticleHub;
import com.poc.techvoice.articleservice.domain.service.notification.Subscriber;
import com.poc.techvoice.articleservice.external.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class SubscriptionService {

    private final ArticleHub articleHub;
    private final CategoryRepository categoryRepository;
    private final RoutingFactory routingFactory;

    @PostConstruct
    public void initializeSubscribers() {
        Set<Category> articleCategories = categoryRepository.findAllCategories();
        articleCategories.forEach(category -> {
            Integer categoryId = category.getId();
            List<User> readers = category.getSubscribedUsers();
            List<Subscriber> subscribers = readers.stream()
                    .map(reader -> new Subscriber(routingFactory, reader.getEmail(), reader.getNotificationChannel()))
                    .collect(Collectors.toList());

            subscribers.forEach(subscriber -> articleHub.registerObserver(subscriber, categoryId));
        });

        log.info("Fetched initial subscribers");
    }

}
