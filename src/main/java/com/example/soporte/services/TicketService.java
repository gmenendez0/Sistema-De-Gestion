package com.example.soporte.services;

import com.example.soporte.DTO.TicketDTO;
import com.example.soporte.models.ExternalEntities.Client;
import com.example.soporte.models.ExternalEntities.Employee;
import com.example.soporte.models.ExternalEntities.Task;
import com.example.soporte.models.Product.Version;
import com.example.soporte.models.Ticket.Ticket;
import com.example.soporte.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public Ticket createTicket(TicketDTO ticketDTO) {
        Ticket ticket = new Ticket(ticketDTO);

        Client client = clientService.getClientById(ticketDTO.clientId);
        ticket.setClientId(client.getId());

        Employee employee = employeeService.getEmployeeByFileName(ticketDTO.employeeId);
        ticket.setEmployeeId(employee.getFileName());

        Version version = versionService.getVersionById(ticketDTO.versionId);

        List<Long> tasksId = ticketDTO.tasksIds;
        List<Task> tasksList = tasksId.stream().map(Task::new).collect(Collectors.toList());

        ticket.setTasks(tasksList);
        ticket.setVersion(version);
        version.addTicket(ticket);

        repository.save(ticket);

        return ticket;
    }

    public  Ticket updateTicket( TicketDTO ticketDTO){
        Ticket ticket = getTicketById(ticketDTO.versionId);
        ticket.update(ticketDTO);
        repository.save(ticket);
        return  ticket;
    }
}
