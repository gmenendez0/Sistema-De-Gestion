package com.example.soporte.services.service;

import com.example.soporte.DTO.CreateTicketDTO;
import com.example.soporte.DTO.GetTicketDTO;
import com.example.soporte.DTO.UpdateTicketDTO;
import com.example.soporte.exceptions.InvalidArgumentsException;
import com.example.soporte.exceptions.RepositoryException;
import com.example.soporte.models.ExternalEntities.Client;
import com.example.soporte.models.ExternalEntities.Employee;
import com.example.soporte.models.Product.Version;
import com.example.soporte.models.Ticket.Status;
import com.example.soporte.models.Ticket.Ticket;
import com.example.soporte.repositories.TicketRepository;
import com.example.soporte.services.notification.TicketNotificationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class TicketService extends Service<Ticket, Long>{
    private final ClientService clientService;
    private final EmployeeService employeeService;
    private final VersionService versionService;
    private final TicketNotificationService ticketNotificationService;
    @Autowired
    TicketRepository repository;

    @Autowired
    public TicketService(TicketRepository repository, ClientService clientService, EmployeeService employeeService, VersionService versionService, TicketNotificationService ticketNotificationService) {
        super(repository);
        this.employeeService = employeeService;
        this.clientService = clientService;
        this.versionService = versionService;
        this.ticketNotificationService = ticketNotificationService;
    }

    private Ticket getTicketById(Long id){
        return executeRepositorySupplierSafely(() -> repository.findById(id).orElse(null));
    }

    private void saveTicket(Ticket ticket){
        executeRepositorySupplierSafely(() -> repository.save(ticket));
    }
    private List<Ticket> verifyTicketTittleAlreadyExist(String title){

      return executeRepositorySupplierSafely(() -> repository.findByTitle(title));
    }

    @Transactional
    public Ticket createTicket(CreateTicketDTO createTicketDTO) {
        if (!verifyTicketTittleAlreadyExist(createTicketDTO.title).isEmpty()){throw new RepositoryException("already exist ticket with tittle: " + createTicketDTO.title);}
        if (!clientService.clientExists(createTicketDTO.clientId)) throw new InvalidArgumentsException("Client does not exist.");
        if (createTicketDTO.employeeId != null && !employeeService.employeeExists(createTicketDTO.employeeId)) throw new InvalidArgumentsException("Employee does not exist.");

        createTicketDTO.version = versionService.getVersionById(createTicketDTO.versionId);
        if(createTicketDTO.version == null) throw new InvalidArgumentsException("Version does not exist.");

        Ticket ticket = new Ticket(createTicketDTO);
        saveTicket(ticket);

        //if(!createTicketDTO.tasks.isEmpty()) ticketNotificationService.notifyTicketTask(ticket.getId(), List.of(), createTicketDTO.tasksIds);

        return ticket;
    }

    public GetTicketDTO getTicketDTOById(Long id){
        Ticket ticket = getTicketById(id);
        if(ticket == null) return null;

        return getTicketDTO(ticket);
    }

    private GetTicketDTO getTicketDTO(Ticket ticket){
        Client client = clientService.getClientById(ticket.getClientId());
        Employee employee = employeeService.getEmployeeByFileName(ticket.getEmployeeId());

        return new GetTicketDTO(ticket, client, employee);
    }

    public List<GetTicketDTO> getAllTickets(){
        List<Ticket> tickets = executeRepositorySupplierSafely(() -> repository.findAll());
        return tickets.stream().map(this::getTicketDTO).toList();
    }

    public void deleteTicketById(Long id){
        executeRepositoryRunnableSafely(() -> repository.deleteById(id));
    }

    @Transactional
    public Ticket updateTicket(UpdateTicketDTO dto, Long id){
        dto.validate();
        Ticket ticket = getTicketById(id);
        if(ticket == null) return null;

        updateTicketBasicFields(dto, ticket);
        updateTicketEmployee(dto, ticket);
        updateTicketVersion(dto, ticket);
        updateTicketTasks(dto, ticket);

        saveTicket(ticket);
        //if(!dto.tasksToRelate.isEmpty() || !dto.tasksToUnrelate.isEmpty()) ticketNotificationService.notifyTicketTask(id, dto.tasksToUnrelate, dto.tasksToRelate);

       return ticket;
    }

    private void updateTicketBasicFields(UpdateTicketDTO dto, Ticket ticket){
        if(dto.description != null) ticket.setDescription(dto.description);
        if(dto.status != null) ticket.setStatus(Status.fromString(dto.status));
    }

    private void updateTicketEmployee(UpdateTicketDTO dto, Ticket ticket){
        if(dto.employeeId != null) {
            if(!employeeService.employeeExists(dto.employeeId)) throw new InvalidArgumentsException("Employee does not exist.");
            ticket.setEmployeeId(dto.employeeId);
        }
    }

    private void updateTicketVersion(UpdateTicketDTO dto, Ticket ticket){
        if(dto.versionId != null) {
            Version version = versionService.getVersionById(dto.versionId);
            if(version == null) throw new InvalidArgumentsException("Version does not exist.");
            ticket.setVersion(version);
        }
    }

    private void updateTicketTasks(UpdateTicketDTO dto, Ticket ticket){
        if(!dto.tasksToUnrelate.isEmpty()) unrelateTasksFromTicket(dto, ticket);
        if(!dto.tasksToRelate.isEmpty()) relateTasksToTicket(dto, ticket);
    }

    private void relateTasksToTicket(UpdateTicketDTO dto, Ticket ticket){
        // SET ??
        List<Long> tasks = dto.tasksToRelate;
        ticket.addTasks(tasks);

    }

    private void unrelateTasksFromTicket(UpdateTicketDTO dto, Ticket ticket){
        List<Long> tasks = dto.tasksToUnrelate;
        ticket.removeTasks(tasks);
    }

    public List<Long> getTasksByTicketId(Long id){
        Ticket ticket = getTicketById(id);
        return Optional.ofNullable(ticket).map(Ticket::getTasks).orElse(null);
    }

    public Duration getTicketMaxResponseTime(Long id){
        Ticket ticket = getTicketById(id);
        return Optional.ofNullable(ticket).map(Ticket::getMaxResponseTime).orElse(null);
    }
}
