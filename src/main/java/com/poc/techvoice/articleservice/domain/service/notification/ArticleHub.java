package com.poc.techvoice.articleservice.domain.service.notification;

import com.poc.techvoice.articleservice.domain.entities.dto.NotificationDto;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ArticleHub implements Subject {
    private Map<Observer, Integer> observers = new HashMap<>();
    private NotificationDto notification;


    @Override
    public void registerObserver(Observer observer, Integer category) {
        log.debug("Registering new observer");
        observers.put(observer, category);
    }

    @Override
    public void removeObserver(Observer observer) {
        log.debug("De-registering observer");
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(Integer categoryId) {
        log.debug("Notifying all readers subscribed to article category");
        for (Map.Entry<Observer, Integer> entry : observers.entrySet()) {
            if (entry.getValue().equals(categoryId)) {
                entry.getKey().update(notification);
            }

        }
    }

    public void setNotification(NotificationDto notification, Integer categoryId) {
        this.notification = notification;
        notifyObservers(categoryId);
    }
}
