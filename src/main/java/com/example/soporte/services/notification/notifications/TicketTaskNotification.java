package com.example.soporte.services.notification.notifications;

import java.util.List;

public class TicketTaskNotification{
    public List<Long> downList;
    public Long ticketId;
    public List<Long> upList;

    public TicketTaskNotification(Long ticketId, List<Long> downList, List<Long> upList){
        this.ticketId = ticketId;
        this.downList = downList;
        this.upList = upList;
    }
}
