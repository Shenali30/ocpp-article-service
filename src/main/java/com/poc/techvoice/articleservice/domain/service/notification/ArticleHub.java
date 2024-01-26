package com.poc.techvoice.articleservice.domain.service.notification;

import com.poc.techvoice.articleservice.domain.entities.dto.NotificationDto;

import java.util.HashMap;
import java.util.Map;

public class ArticleHub implements Subject {
    private Map<Observer, Integer> observers = new HashMap<>();
    private NotificationDto notification;


    @Override
    public void registerObserver(Observer observer, Integer category) {
        observers.put(observer, category);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(Integer categoryId) {
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
