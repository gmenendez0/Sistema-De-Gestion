package com.example.soporte.services.notification;

import com.example.soporte.externalAPI.ProjectsModuleRestAPIControllerNotifier;
import com.example.soporte.services.notification.notifications.TicketTaskNotification;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class TicketNotificationService extends NotificationService{
    public TicketNotificationService(){
        super(new ProjectsModuleRestAPIControllerNotifier("https://projects-backend-am35.onrender.com/tasks/associations/update"));
    }

    /**
     * Notifies about ticket task updates by sending a notification.
     *
     * @param ticketId the ID of the ticket associated with the task updates
     * @param downList the list of task IDs to be associated with the ticket
     * @param upList   the list of task IDs to be disassociated from the ticket
     */
    public void notifyTicketTask(Long ticketId, List<Long> downList, List<Long> upList){
        TicketTaskNotification ticketTaskNotification = new TicketTaskNotification(ticketId, downList, upList);
        sendNotification(ticketTaskNotification, TicketTaskNotification.class);
    }
}
