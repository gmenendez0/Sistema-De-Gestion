package com.example.soporte;

import com.example.soporte.DTO.CreateTicketDTO;
import com.example.soporte.DTO.GetTicketDTO;
import com.example.soporte.DTO.UpdateTicketDTO;
import com.example.soporte.models.Product.Product;
import com.example.soporte.models.Ticket.Ticket;

import com.example.soporte.repositories.ProductRepository;
import com.example.soporte.services.service.TicketService;
import com.example.soporte.services.service.VersionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Collection;
import java.util.List;


@ContextConfiguration(classes = SoporteApplication.class)
@WebAppConfiguration
public class TicketIntegrationServiceTest {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    TicketService ticketService;
    @Autowired
    ProductRepository productRepo;
    @Autowired
    VersionService versionService;

    Ticket createTicket(CreateTicketDTO ticketRequest) {
        return ticketService.createTicket(ticketRequest);
    }

    GetTicketDTO getTicketById(Long id) {
        return ticketService.getTicketDTOById(id);
    }

    List<GetTicketDTO> getTickets() {
        return ticketService.getAllTickets();
    }

    void  deleteTicket(Long ticketId){
        ticketService.deleteTicketById(ticketId);
    }

    Ticket updateTicket(UpdateTicketDTO ticketRequest, Long id) {
        return ticketService.updateTicket(ticketRequest , id);
    }

    // products
    Collection<Product> getProducts(){return productRepo.findAll();}
}
