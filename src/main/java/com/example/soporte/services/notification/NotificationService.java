package com.example.soporte.services.notification;

@org.springframework.stereotype.Service
public abstract class NotificationService {
    private final Notifier notifier;

    public NotificationService(Notifier notifier){
        this.notifier = notifier;
    }

    /**
     * Sends a notification of type T using the configured Notifier.
     *
     * @param notification the notification object to send
     * @param aClass       the class type of the notification
     * @param <T>          the type of the notification object
     */
    public <T> void sendNotification(T notification, Class<T> aClass){
        notifier.sendNotification(notification, aClass);
    }
}
