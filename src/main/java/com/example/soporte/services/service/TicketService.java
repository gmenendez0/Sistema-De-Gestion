package com.example.soporte.services.service;

import com.example.soporte.DTO.CreateTicketDTO;
import com.example.soporte.DTO.GetTicketDTO;
import com.example.soporte.DTO.UpdateTicketDTO;
import com.example.soporte.models.ExternalEntities.Client;
import com.example.soporte.models.ExternalEntities.Employee;
import com.example.soporte.models.ExternalEntities.Task;
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

    @Transactional
    public Ticket createTicket(CreateTicketDTO createTicketDTO) {
        if (!clientService.clientExists(createTicketDTO.clientId)) throw new IllegalArgumentException("Client does not exist.");
        if (!employeeService.employeeExists(createTicketDTO.employeeId)) throw new IllegalArgumentException("Employee does not exist.");

        createTicketDTO.version = versionService.getVersionById(createTicketDTO.versionId);
        if(createTicketDTO.version == null) throw new IllegalArgumentException("Version does not exist.");

        createTicketDTO.tasks = createTicketDTO.tasksIds.stream().map(Task::new).toList();

        Ticket ticket = new Ticket(createTicketDTO);
        saveTicket(ticket);

        if(!createTicketDTO.tasks.isEmpty()) ticketNotificationService.notifyTicketTask(ticket.getId(), List.of(), createTicketDTO.tasksIds);

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
        if(!dto.tasksToRelate.isEmpty() || !dto.tasksToUnrelate.isEmpty()) ticketNotificationService.notifyTicketTask(id, dto.tasksToUnrelate, dto.tasksToRelate);

       return ticket;
    }

    private void updateTicketBasicFields(UpdateTicketDTO dto, Ticket ticket){
        if(dto.description != null) ticket.setDescription(dto.description);
        if(dto.status != null) ticket.setStatus(Status.fromString(dto.status));
    }

    private void updateTicketEmployee(UpdateTicketDTO dto, Ticket ticket){
        if(dto.employeeId != null && employeeService.employeeExists(dto.employeeId)) {
            ticket.setEmployeeId(dto.employeeId);
        } else {
            throw new IllegalArgumentException("Employee does not exist.");
        }
    }

    private void updateTicketVersion(UpdateTicketDTO dto, Ticket ticket){
        if(dto.versionId != null) {
            Version version = versionService.getVersionById(dto.versionId);
            if(version == null) throw new IllegalArgumentException("Version does not exist.");
            ticket.setVersion(version);
        }
    }

    private void updateTicketTasks(UpdateTicketDTO dto, Ticket ticket){
        if(!dto.tasksToRelate.isEmpty()) relateTasksToTicket(dto, ticket);
        if(!dto.tasksToUnrelate.isEmpty()) unrelateTasksFromTicket(dto, ticket);
    }

    private void relateTasksToTicket(UpdateTicketDTO dto, Ticket ticket){
        List<Task> tasks = dto.tasksToRelate.stream().map(Task::new).toList();
        ticket.addTasks(tasks);
    }

    private void unrelateTasksFromTicket(UpdateTicketDTO dto, Ticket ticket){
        List<Task> tasks = dto.tasksToUnrelate.stream().map(Task::new).toList();
        ticket.removeTasks(tasks);
    }

    public List<Task> getTasksByTicketId(Long id){
        Ticket ticket = getTicketById(id);
        return Optional.ofNullable(ticket).map(Ticket::getTasks).orElse(null);
    }

    public Duration getTicketMaxResponseTime(Long id){
        Ticket ticket = getTicketById(id);
        return Optional.ofNullable(ticket).map(Ticket::getMaxResponseTime).orElse(null);
    }
}
