package com.example.soporte.services;

import com.example.soporte.DTO.TicketRequest;
import com.example.soporte.models.ExternalEntities.Client;
import com.example.soporte.models.ExternalEntities.Employee;
import com.example.soporte.models.ExternalEntities.Task;
import com.example.soporte.models.Product.Product;
import com.example.soporte.models.Product.Version;
import com.example.soporte.models.Ticket.Ticket;
import com.example.soporte.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class TicketService extends Service<Ticket, Long> {
    private ClientService clientService;
    private EmployeeService employeeService;
    private VersionService versionService;
    @Autowired
    public TicketService(TicketRepository repository,
                         ClientService clientService,
                         EmployeeService employeeService,
                         VersionService versionService){
        super(repository);
        this.employeeService = employeeService;
        this.clientService = clientService;
        this.versionService = versionService;
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
        Ticket ticket = getTicketById(id);
        return Optional.ofNullable(ticket).map(Ticket::getTasks).orElse(null);
    }

    public Duration getTicketMaxResponseTime(Long id){
        Ticket ticket = getTicketById(id);
        return Optional.ofNullable(ticket).map(Ticket::getMaxResponseTime).orElse(null);
    }

    public Ticket createTicket(Long versionId, TicketRequest ticketRequest) {
        Ticket ticket = new Ticket(ticketRequest);

        Client client = clientService.getClientById(Long.valueOf(ticketRequest.getClientId()));
        ticket.setClientId(client.getId());

        Employee employee = employeeService.getEmployeeByFileName(Long.valueOf(ticketRequest.getEmployeeId()));
        ticket.setEmployeeId(employee.getFileName());


        Version version = versionService.getVersionById(versionId);
        ticket.setVersion(version);
        version.addTicket(ticket);

        repository.save(ticket);

        return ticket;
    }
}
