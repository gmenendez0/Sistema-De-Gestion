package com.example.soporte.services.notifications;

import com.example.soporte.DTO.TicketTaskNotification;

import java.util.List;

public class TicketNotificationService extends NotificationService{
    public TicketNotificationService(){
        super(null);
    }

    public void notifyTicketTask(Long ticketId, List<Long> downList, List<Long> upList){
        TicketTaskNotification ticketTaskNotification = new TicketTaskNotification(ticketId, downList, upList);
        sendNotification(ticketTaskNotification, TicketTaskNotification.class);
    }
}
