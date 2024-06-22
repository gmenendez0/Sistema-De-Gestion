package com.example.soporte.services;

import com.example.soporte.DTO.CreateTicketDTO;
import com.example.soporte.DTO.GetTicketDTO;
import com.example.soporte.models.ExternalEntities.Client;
import com.example.soporte.models.ExternalEntities.Employee;
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

    private Ticket getTicketById(Long id){
        return executeRepositorySupplierSafely(() -> repository.findById(id).orElse(null));
    }

    private GetTicketDTO getTicketDTO(Ticket ticket){
        Client client = clientService.getClientById(ticket.getClientId());
        Employee employee = employeeService.getEmployeeByFileName(ticket.getEmployeeId());

        return new GetTicketDTO(ticket, client, employee);
    }

    public GetTicketDTO getTicketDTOById(Long id){
        Ticket ticket = getTicketById(id);
        if(ticket == null) return null;

        return getTicketDTO(ticket);
    }

    public void deleteTicketById(Long id){
        executeRepositoryRunnableSafely(() -> repository.deleteById(id));
    }

    public List<GetTicketDTO> getAllTickets(){
        List<Ticket> tickets = executeRepositorySupplierSafely(() -> repository.findAll());
        return tickets.stream().map(this::getTicketDTO).toList();
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
        if (!clientService.clientExists(createTicketDTO.clientId)) throw new IllegalArgumentException("Client does not exist.");
        if (!employeeService.employeeExists(createTicketDTO.employeeId)) throw new IllegalArgumentException("Employee does not exist.");

        createTicketDTO.version = versionService.getVersionById(createTicketDTO.versionId);
        if(createTicketDTO.version == null) throw new IllegalArgumentException("Version does not exist.");

        createTicketDTO.tasks = createTicketDTO.tasksIds.stream().map(Task::new).toList();

        Ticket ticket = new Ticket(createTicketDTO);
        executeRepositorySupplierSafely(() -> repository.save(ticket));
        // TODO: Notificar a Proyectos si es que se asocio alguna task a un ticket.

        return ticket;
    }

    public  Ticket updateTicket( CreateTicketDTO createTicketDTO){
        Ticket ticket = getTicketById(createTicketDTO.versionId);
        ticket.update(createTicketDTO);
        repository.save(ticket);
        return  ticket;
    }
}
