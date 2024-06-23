package com.example.soporte.services.notification;

import com.example.soporte.externalAPI.ProjectsModuleRestAPIControllerNotifier;
import com.example.soporte.services.notification.notifications.TicketTaskNotification;

import java.util.List;

public class TicketNotificationService extends NotificationService{
    public TicketNotificationService(){
        super(new ProjectsModuleRestAPIControllerNotifier("")); //TODO AGREGAR URL
    }

    public void notifyTicketTask(Long ticketId, List<Long> downList, List<Long> upList){
        TicketTaskNotification ticketTaskNotification = new TicketTaskNotification(ticketId, downList, upList);
        sendNotification(ticketTaskNotification, TicketTaskNotification.class);
    }
}
