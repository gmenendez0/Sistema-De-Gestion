package com.example.soporte.services;

import com.example.soporte.DTO.CreateTicketDTO;
import com.example.soporte.models.ExternalEntities.Task;
import com.example.soporte.models.Ticket.Ticket;
import com.example.soporte.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class TicketService extends Service<Ticket, Long> {
    private final ClientService clientService;
    private final EmployeeService employeeService;
    private final VersionService versionService;

    @Autowired
    public TicketService(TicketRepository repository, ClientService clientService, EmployeeService employeeService, VersionService versionService){
        super(repository);
        this.employeeService = employeeService;
        this.clientService = clientService;
        this.versionService = versionService;
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

    public Ticket createTicket(CreateTicketDTO createTicketDTO) {
        createTicketDTO.client = clientService.getClientById(createTicketDTO.clientId);
        createTicketDTO.employee = employeeService.getEmployeeByFileName(createTicketDTO.employeeId);
        createTicketDTO.version = versionService.getVersionById(createTicketDTO.versionId);
        createTicketDTO.tasks = createTicketDTO.tasksIds.stream().map(Task::new).toList();
        createTicketDTO.validatePostRequestFields();

        Ticket ticket = new Ticket(createTicketDTO);
        return executeRepositorySupplierSafely(() -> repository.save(ticket));
    }

    public  Ticket updateTicket( CreateTicketDTO createTicketDTO){
        Ticket ticket = getTicketById(createTicketDTO.versionId);
        ticket.update(createTicketDTO);
        repository.save(ticket);
        return  ticket;
    }
}
