package com.example.soporte.services;

import com.example.soporte.models.ExternalEntities.Task;
import com.example.soporte.models.Ticket.Ticket;
import com.example.soporte.repositories.TicketRepository;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class TicketService extends Service<Ticket, Long> {
    public TicketService(TicketRepository repository) {
        super(repository);
    }

    public Ticket saveTicket(Ticket ticket) {
        return executeRepositorySupplierSafely(() -> repository.save(ticket));
    }

    public Ticket getTicketById(Long id){
        return executeRepositorySupplierSafely(() -> repository.findById(id).orElse(null));
    }

    public void deleteTicketById(Long id){
        executeRepositoryRunnableSafely(() -> repository.deleteById(id));
    }

    public List<Ticket> getAllTickets(){
        return executeRepositorySupplierSafely(() -> repository.findAll());
    }

    public List<Task> getTasksByTicketId(Long id){
        Ticket ticket = executeRepositorySupplierSafely(() -> repository.findById(id).orElse(null));
        return Optional.ofNullable(ticket).map(Ticket::getTasks).orElse(null);
    }
}
