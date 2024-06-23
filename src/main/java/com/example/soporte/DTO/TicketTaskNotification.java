package com.example.soporte.DTO;

import java.util.ArrayList;
import java.util.List;

public class TicketTaskNotification{
    List<Long> downList = new ArrayList<>();
    Long ticketId;
    List<Long> upList = new ArrayList<>();

    public TicketTaskNotification(Long ticketId, List<Long> downList, List<Long> upList){
        this.ticketId = ticketId;
        this.downList = downList;
        this.upList = upList;
    }
}
