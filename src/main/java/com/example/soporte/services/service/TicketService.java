package com.example.soporte.services.service;

import com.example.soporte.DTO.CreateTicketDTO;
import com.example.soporte.DTO.GetTicketDTO;
import com.example.soporte.DTO.GetTicketStatisticsDTO;
import com.example.soporte.DTO.UpdateTicketDTO;
import com.example.soporte.exceptions.InvalidArgumentsException;
import com.example.soporte.models.ExternalEntities.Client;
import com.example.soporte.models.ExternalEntities.Employee;
import com.example.soporte.models.Product.Version;
import com.example.soporte.models.Ticket.Status;
import com.example.soporte.models.Ticket.Ticket;
import com.example.soporte.repositories.TicketRepository;
import com.example.soporte.services.notification.TicketNotificationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class TicketService extends Service{
    private final ClientService clientService;
    private final EmployeeService employeeService;
    private final VersionService versionService;
    private final TicketNotificationService ticketNotificationService;
    private final TicketRepository repository;

    @Autowired
    public TicketService(TicketRepository repository, ClientService clientService, EmployeeService employeeService, VersionService versionService, TicketNotificationService ticketNotificationService) {
        this.employeeService = employeeService;
        this.clientService = clientService;
        this.versionService = versionService;
        this.ticketNotificationService = ticketNotificationService;
        this.repository = repository;
    }

    /**
     * Retrieves a Ticket entity by its ID from the repository.
     *
     * @param id the ID of the Ticket entity
     * @return the Ticket entity if found, otherwise null
     */
    private Ticket getTicketById(Long id){
        return executeRepositorySupplierSafely(() -> repository.findById(id).orElse(null));
    }

    /**
     * Retrieves all Ticket entities from the repository.
     *
     * @return a list of Ticket entities
     */
    private List<Ticket> getTickets(){
        return executeRepositorySupplierSafely(repository::findAll);
    }

    /**
     * Retrieves all Ticket entities as DTOs.
     *
     * @return a list of GetTicketDTO representing all Ticket entities
     */
    public List<GetTicketDTO> getAllTicketsDTOs(){
        List<Ticket> tickets = getTickets();
        return tickets.stream().map(this::getTicketDTO).toList();
    }

    /**
     * Retrieves a Ticket entity as DTO by its ID.
     *
     * @param id the ID of the Ticket entity
     * @return the GetTicketDTO representing the Ticket entity, or null if not found
     */
    public GetTicketDTO getTicketDTOById(Long id){
        Ticket ticket = getTicketById(id);
        if(ticket == null) return null;

        return getTicketDTO(ticket);
    }

    /**
     * Converts a Ticket entity into a GetTicketDTO.
     *
     * @param ticket the Ticket entity to convert
     * @return the corresponding GetTicketDTO
     */
    public GetTicketDTO getTicketDTO(Ticket ticket){
        Client client = clientService.getClientById(ticket.getClientId());
        Employee employee = employeeService.getEmployeeByFileName(ticket.getEmployeeId());

        return new GetTicketDTO(ticket, client, employee);
    }

    /**
     * Retrieves all Task IDs associated with a Ticket.
     *
     * @param id the ID of the Ticket
     * @return a list of Task IDs associated with the Ticket, or null if Ticket not found
     */
    public List<Long> getTasksByTicketId(Long id){
        Ticket ticket = getTicketById(id);
        return Optional.ofNullable(ticket).map(Ticket::getTasks).orElse(null);
    }

    /**
     * Retrieves the maximum response time (in hours) of a Ticket.
     *
     * @param id the ID of the Ticket
     * @return the maximum response time of the Ticket in hours, or null if Ticket not found
     */
    public Duration getTicketMaxResponseTime(Long id){
        Ticket ticket = getTicketById(id);
        return Optional.ofNullable(ticket).map(Ticket::getMaxResponseTime).orElse(null);
    }

    /**
     * Retrieves statistics for a Ticket by its ID.
     *
     * @param id the ID of the Ticket
     * @return the GetTicketStatisticsDTO containing statistics for the Ticket, or null if Ticket not found
     */
    public GetTicketStatisticsDTO getStatisticsByTicketId(Long id){
        Ticket ticket = getTicketById(id);
        if (ticket == null) return null;

        return new GetTicketStatisticsDTO(ticket);
    }

    /**
     * Retrieves Tickets associated with a specific Version ID.
     *
     * @param versionId the ID of the Version
     * @return a list of Tickets associated with the Version ID
     */
    public List<Ticket> getTicketsByVersionId(Long versionId){
        return executeRepositorySupplierSafely(() -> repository.getTicketsByVersion(versionId));
    }

    /**
     * Retrieves a page of Tickets associated with a specific Version ID.
     *
     * @param versionId the ID of the Version
     * @param page      the pagination information
     * @return a Page containing Tickets associated with the Version ID
     */
    public Page<Ticket> getTicketPageByVersionId(Long versionId, Pageable page){
        return executeRepositorySupplierSafely(() -> repository.getTicketsPageByVersionId(versionId, page));
    }

    /**
     * Creates a new Ticket based on the provided CreateTicketDTO.
     * If tasks where associated, a notification is sent to the Projects service.
     *
     * @param createTicketDTO the DTO containing information to create the Ticket
     * @return the GetTicketDTO representing the created Ticket
     * @throws InvalidArgumentsException if validation fails or required entities do not exist
     */
    @Transactional
    public GetTicketDTO createTicket(CreateTicketDTO createTicketDTO) {
        if (!clientService.clientExists(createTicketDTO.clientId)) throw new InvalidArgumentsException("Client does not exist.");
        if (createTicketDTO.employeeId != null && employeeService.employeeDoesNotExist(createTicketDTO.employeeId)) throw new InvalidArgumentsException("Employee does not exist.");

        createTicketDTO.version = versionService.getVersionById(createTicketDTO.versionId);
        if(createTicketDTO.version == null) throw new InvalidArgumentsException("Version does not exist.");

        Ticket ticket = new Ticket(createTicketDTO);
        saveTicket(ticket);

        if(!createTicketDTO.tasksIds.isEmpty()) ticketNotificationService.notifyTicketTask(ticket.getId(), List.of(), createTicketDTO.tasksIds);

        return getTicketDTO(ticket);
    }

    /**
     * Saves a Ticket entity to the repository.
     *
     * @param ticket the Ticket entity to save
     */
    private void saveTicket(Ticket ticket){
        executeRepositorySupplierSafely(() -> repository.save(ticket));
    }

    /**
     * Deletes a Ticket by its ID.
     *
     * @param id the ID of the Ticket to delete
     */
    @Transactional
    public void deleteTicketById(Long id){
        executeRepositoryRunnableSafely(() -> repository.deleteById(id));
    }

    /**
     * Updates a Ticket based on the provided UpdateTicketDTO and Ticket ID.
     * If tasks where associated or unassociated, a notification is sent to the Projects service.
     *
     * @param dto the DTO containing fields to update in the Ticket
     * @param id  the ID of the Ticket to update
     * @return the GetTicketDTO representing the updated Ticket
     * @throws InvalidArgumentsException if validation fails or required entities do not exist
     */
    @Transactional
    public GetTicketDTO updateTicket(UpdateTicketDTO dto, Long id){
        dto.validate();
        Ticket ticket = getTicketById(id);
        if(ticket == null) return null;

        updateTicketBasicFields(dto, ticket);
        updateTicketEmployee(dto, ticket);
        updateTicketVersion(dto, ticket);
        updateTicketTasks(dto, ticket);

        saveTicket(ticket);
        if(!dto.tasksToRelate.isEmpty() || !dto.tasksToUnrelate.isEmpty()) ticketNotificationService.notifyTicketTask(id, dto.tasksToUnrelate, dto.tasksToRelate);

       return getTicketDTO(ticket);
    }

    /**
     * Updates basic fields of a Ticket entity based on an UpdateTicketDTO.
     *
     * @param dto    the UpdateTicketDTO containing fields to update
     * @param ticket the Ticket entity to update
     */
    private void updateTicketBasicFields(UpdateTicketDTO dto, Ticket ticket){
        if(dto.description != null) ticket.setDescription(dto.description);
        if(dto.status != null) ticket.setStatus(Status.fromString(dto.status));
    }

    /**
     * Updates the employee field of a ticket if a valid employee ID is provided in the update DTO.
     * @param dto The DTO containing update data.
     * @param ticket The ticket to update.
     * @throws InvalidArgumentsException If the employee ID does not exist in the system.
     */
    private void updateTicketEmployee(UpdateTicketDTO dto, Ticket ticket){
        if(dto.employeeId != null) {
            if(employeeService.employeeDoesNotExist(dto.employeeId)) throw new InvalidArgumentsException("Employee does not exist.");
            ticket.setEmployeeId(dto.employeeId);
        }
    }

    /**
     * Updates the version of a ticket if a valid version ID is provided in the update DTO.
     * @param dto The DTO containing update data.
     * @param ticket The ticket to update.
     * @throws InvalidArgumentsException If the version ID does not exist in the system.
     */
    private void updateTicketVersion(UpdateTicketDTO dto, Ticket ticket){
        if(dto.versionId != null) {
            Version version = versionService.getVersionById(dto.versionId);
            if(version == null) throw new InvalidArgumentsException("Version does not exist.");
            ticket.setVersion(version);
        }
    }

    /**
     * Handles updating tasks associated with a ticket based on the update DTO.
     * @param dto The DTO containing update data.
     * @param ticket The ticket to update.
     */
    private void updateTicketTasks(UpdateTicketDTO dto, Ticket ticket){
        if(!dto.tasksToUnrelate.isEmpty()) unrelateTasksFromTicket(dto, ticket);
        if(!dto.tasksToRelate.isEmpty()) relateTasksToTicket(dto, ticket);
    }

    /**
     * Relates new tasks to a ticket.
     * @param dto The DTO containing tasks to relate.
     * @param ticket The ticket to which tasks will be associated.
     */
    private void relateTasksToTicket(UpdateTicketDTO dto, Ticket ticket){
        List<Long> tasks = dto.tasksToRelate;
        ticket.addTasks(tasks);
    }

    /**
     * Unrelates tasks from a ticket.
     * @param dto The DTO containing tasks to unrelate.
     * @param ticket The ticket from which tasks will be disassociated.
     */
    private void unrelateTasksFromTicket(UpdateTicketDTO dto, Ticket ticket){
        List<Long> tasks = dto.tasksToUnrelate;
        ticket.removeTasks(tasks);
    }
}
