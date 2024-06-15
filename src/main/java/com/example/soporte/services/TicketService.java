package com.example.soporte.services;

import com.example.soporte.models.Ticket.Ticket;
import com.example.soporte.repositories.TicketRepository;

import java.util.Collection;

public class TicketService extends Service<Ticket, Long> {
    public TicketService(TicketRepository repository) {
        super(repository);
    }

    public Ticket createTicket() {
        Ticket ticket = new Ticket();
        return repository.save(ticket);
    }

    public Collection<Ticket> getTickets() {
        return repository.findAll();
    }
}
