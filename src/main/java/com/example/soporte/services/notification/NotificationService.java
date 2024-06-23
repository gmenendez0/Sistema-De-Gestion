package com.example.soporte.services.notification;

@org.springframework.stereotype.Service
public abstract class NotificationService {
    private final Notifier notifier;

    public NotificationService(Notifier notifier){
        this.notifier = notifier;
    }

    public <T> void sendNotification(T notification, Class<T> aClass){
        notifier.sendNotification(notification, aClass);
    }
}
