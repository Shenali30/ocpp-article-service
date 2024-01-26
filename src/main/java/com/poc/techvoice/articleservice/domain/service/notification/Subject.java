package com.poc.techvoice.articleservice.domain.service.notification;

public interface Subject {

    void registerObserver(Observer observer, Integer category);

    void removeObserver(Observer observer);

    void notifyObservers(Integer categoryId);
}
