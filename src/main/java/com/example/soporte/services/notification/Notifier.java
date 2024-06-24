package com.example.soporte.services.notification;

import org.springframework.stereotype.Component;

@Component
public interface Notifier{
    /**
     * Sends a notification of type T.
     *
     * @param notification the notification object to send
     * @param aClass       the class type of the notification
     * @param <T>          the type of the notification object
     */
    <T> void sendNotification(T notification, Class<T> aClass);
}
