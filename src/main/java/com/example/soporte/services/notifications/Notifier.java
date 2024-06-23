package com.example.soporte.services.notifications;

import org.springframework.stereotype.Component;

@Component
public interface Notifier{
    <T> void sendNotification(T notification, Class<T> aClass);
}
