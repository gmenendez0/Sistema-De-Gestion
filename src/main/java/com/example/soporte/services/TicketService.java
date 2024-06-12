package com.example.soporte.services;

import com.example.soporte.models.Ticket;
import com.example.soporte.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class TicketService {
        private TicketRepository ticketRepository;

        @Autowired
        public TicketService(TicketRepository ticketRepository) {
            this.ticketRepository = ticketRepository;
        }
        public Ticket createTicket(Ticket ticket){
            ticketRepository.save(ticket);
            return ticket;
        }
        public Collection<Ticket> getTickets() {
                return ticketRepository.findAll();
        }
}
