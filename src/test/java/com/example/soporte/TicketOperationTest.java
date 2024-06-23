package com.example.soporte;

import com.example.soporte.DTO.CreateTicketDTO;
import com.example.soporte.DTO.GetTicketDTO;
import com.example.soporte.DTO.UpdateTicketDTO;
import com.example.soporte.exceptions.InvalidArgumentsException;
import com.example.soporte.exceptions.RepositoryException;
import com.example.soporte.models.Product.Product;
import com.example.soporte.models.Product.Version;
import com.example.soporte.models.Ticket.Ticket;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ContextConfiguration(classes = SoporteApplicationTests.class)
public class TicketOperationTest extends TicketIntegrationServiceTest{
        Ticket ticket;
        GetTicketDTO getTicketDTO;
        List<GetTicketDTO> ticketsByVersionId;
        Exception exception;
        CreateTicketDTO ticketRequest =  new CreateTicketDTO();
        Collection<Product> products;
        Long versionID = 1L;
        String tittle = "hello";
        String  status = "nuevo";
        String  severity = "s1";
        List<Long> tasksId;
    @Before
    public void setup() {
        System.out.println("Before any test execution");
    }
    private void setupTicketRequest(String title,Long versionId) {
        ticketRequest.title = title;
        ticketRequest.description = title;
        ticketRequest.clientId = 1L;
        ticketRequest.employeeId = 1l;
        ticketRequest.versionId = versionId;
        ticketRequest.tasksIds = new ArrayList<>();
        ticketRequest.severity = severity;
        ticketRequest.status = status;
    }
    private void setupProductsAndVersions() {
        Product productA = new Product("SIU");
        Version versionA1 = new Version("2.0",productA);
        Version versionA2 = new Version("1.1", productA);
        productA.addVersion(versionA1);
        productA.addVersion(versionA2);
        Product productB = new Product("VSFS");
        Version versionB1 = new Version("2.0",productB);
        Version versionB2 = new Version("1.1", productB);
        productB.addVersion(versionB1);
        productB.addVersion(versionB2);
        productRepository.saveAll(List.of(productA, productB ));
    }
    // create ticket
    @Given("^No exist Ticket with title \"([^\"]*)\"$")
    public void noExistTicketWithTitle(String arg0) throws Throwable {
        setupProductsAndVersions();
    }


    @When("^Trying to create a ticket with title \"([^\"]*)\"$")
    public void tryingToCreateATicketWithTitle(String arg0) throws Throwable {
        setupTicketRequest(arg0,versionID );
        try {
            ticket = createTicket(ticketRequest);
        }catch (Exception e) {
            exception = e;
        }
    }

    @Then("^Ticket tittle should be title \"([^\"]*)\"$")
    public void ticketTittleShouldBeTitle(String arg0) throws Throwable {
            assertTrue(getTicketById(1L).title.equals(arg0));
    }
    // delete ticket
    @Given("^Ticket with ID (\\d+)$")
    public void createTicket(long arg1) throws Throwable {
        setupProductsAndVersions();
        setupTicketRequest("carpet", versionID);
        ticket = createTicket(ticketRequest);
    }

    @When("^Trying to delete ticket with ID (\\d+)$")
    public void deleteTicketById(long arg0) {
        try {
            ticketService.deleteTicketById(arg0);
        } catch (Exception e) {
            exception = e;
        }
    }
    @Then("^Ticked not deleted with ID (\\d+)$")
    public void tickedNotDeletedWithID(long arg0) {
        try {
            getTicketById(arg0);
        } catch (Exception e) {
            exception = e;
        }
        assertNull(exception);
        exception = null;
    }
    @Then("^Ticked deleted with ID (\\d+)$")
    public void tickedDeletedWithID(long arg0) {
        try {
            if (getTicketById(arg0)== null){
                throw new RepositoryException("ticket not found");
            };
        } catch (Exception e) {
            exception = e;
        }
        assertNotNull(exception);
        exception = null;
    }
    // products versions
    @Given("there are product and versions registered")
    public void thereAreProductAndVersionsRegistered() {
        setupProductsAndVersions();
    }

    @When("Trying to view all products versions")
    public void tryingToViewAllVersionsOfAllProducts() {
         products = getProducts();
    }

    @Then("all versions of all registered products should be displayed")
    public void allVersionsOfAllRegisteredProductsShouldBeDisplayed() {
        assertEquals(2, products.size());
        Product productA = products.stream()
                .filter(p -> "SIU"
                        .equals(p.getName()))
                        .findFirst()
                        .orElse(null);

        assertNotNull(productA);
        assertEquals(2, productA.getVersions().size());
        Product productB = products.stream().filter(p -> "VSFS".equals(p.getName())).findFirst().orElse(null);
        assertNotNull(productB);
        assertEquals(2, productB.getVersions().size());
    }


    @Given("no product versions are registered")
    public void noProductVersionsAreRegistered() {
        
    }

    @Then("no product versions should be displayed")
    public void noProductVersionsShouldBeDisplayed() {
        assertEquals(0, products.size());
    }
    // get tickets by version ID

    @Given("there are tickets created for version ID (\\d+)$")
    public void thereAreTicketsCreatedForVersionID(long arg0) {
        setupProductsAndVersions();

        for (int i = 1; i <= 3; i++) {
            String title = "Ticket " + i ;
            setupTicketRequest(title, arg0);
            createTicket(ticketRequest);
        }
    }

    @When("Trying to view the tickets for version ID (\\d+)$")
    public void tryingToViewTheTicketsForVersionID(long arg0) {
        ticketsByVersionId = versionService.getTicketsByVersion(arg0).stream().toList();
    }

    @Then("all tickets for version {int} will be displayed")
    public void allTicketsForVersionWillBeDisplayed(long arg0) {
        assertEquals(3,ticketsByVersionId.size());
    }

    @Given("there are no tickets created for version ID {int}")
    public void thereAreNoTicketsCreatedForVersionID(int arg0) {
        setupProductsAndVersions();
    }

    @Then("no tickets for version {int} will be displayed")
    public void noTicketsForVersionWillBeDisplayed(long arg0) {
        assertEquals(0,ticketsByVersionId.size());
    }

    @When("Trying to view details of ticket ID {int}")
    public void tryingToViewDetailsOfTicketID(long arg0) {

        getTicketDTO = ticketService.getTicketDTOById(arg0);
    }

    @Then("The details of ticket ID {int} should be displayed")
    public void theDetailsOfTicketIDShouldBeDisplayed(int arg0) {
        assertEquals(getTicketDTO.title, ticketRequest.title);
        assertEquals(getTicketDTO.description, ticketRequest.description);
        assertEquals(getTicketDTO.status.toString(), ticketRequest.status.toUpperCase());
        assertEquals(getTicketDTO.severity.toString(), ticketRequest.severity.toUpperCase());
        assertEquals(getTicketDTO.client.getId(),ticketRequest.clientId);
    }

    @Then("The user is informed that ticket {int} does not exist")
    public void theUserIsInformedThatTicketDoesNotExist(long arg0) {
        assertEquals(null,getTicketDTO);
    }

    @Given("There are tasks associated with ticket ID {int}")
    public void thereAreTasksAssociatedWithTicketID(Long arg0) {
        setupProductsAndVersions();
        setupTicketRequest("hola",versionID );
        ticketRequest.tasksIds = Arrays.asList(1L, 2L, 3L);
        try {
            ticket = createTicket(ticketRequest);
        }catch (Exception e) {
            exception = e;
        }
    }

    @When("Trying to view tasks associated with ticket ID {int}")
    public void tryingToViewTasksAssociatedWithTicketID(long arg0) {
       tasksId = ticketService.getTasksByTicketId(arg0);
    }

    @Then("all tasks associated with ticket ID {int} are displayed")
    public void allTasksAssociatedWithTicketIDAreDisplayed(int arg0) {
        assertEquals(2, tasksId.size());
    }

    @Then("No Tasks are displayed")
    public void noTasksAreDisplayed() {
        assertEquals(null, tasksId);
    }
//  ticket update
    @Given("Ticket with status {string}")
    public void ticketWithStatus(String arg0) {
        setupProductsAndVersions();
        setupTicketRequest(tittle,versionID);
        ticketRequest.status = arg0;
        createTicket(ticketRequest);
    }

    @When("Trying to change the status to {string}")
    public void tryingToChangeTheStatusTo(String arg0) {
        UpdateTicketDTO updateTicketDTO = new UpdateTicketDTO();
        updateTicketDTO.status = arg0;
        try {
            ticket = updateTicket(updateTicketDTO,1L);
        }catch (Exception e){
            exception = e;
        }
    }

    @Then("Ticket status is {string}")
    public void ticketStatusIs(String arg0) {
        assertEquals(arg0.toUpperCase(),ticket.getStatus().toString());
    }

    @Given("Ticket with description {string}")
    public void ticketWithSeverity(String arg0) {
        setupProductsAndVersions();
        setupTicketRequest(tittle,versionID);
        ticketRequest.description = arg0;
        createTicket(ticketRequest);
    }

    @When("Trying to change the description  to {string}")
    public void tryingToChangeTheDescriptionTo(String arg0) {
        UpdateTicketDTO updateTicketDTO = new UpdateTicketDTO();
        updateTicketDTO.description = arg0;
        try {
            ticket = updateTicket(updateTicketDTO,2L);
        }catch (Exception e){
            exception = e;
        }
    }

    @Then("the ticket description is {string}")
    public void theTicketDescriptionIs(String arg0) {
        assertEquals(arg0,ticket.getDescription());
    }

    @Then("Error invalid Argument is displayed")
    public void errorInvalidArgumentIsDisplayed() {
        assertNotNull(exception);
        exception = null;
    }

    @When("Trying to create a ticket with no version")
    public void tryingToCreateATicketWithNoVersion() {
        setupTicketRequest(tittle,null);

        try {
            createTicket(ticketRequest);
        }catch (Exception e ){
            exception = e;
        }
    }

    @When("Trying to create a ticket with no client")
    public void tryingToCreateATicketWithNoClient() {
        setupTicketRequest(tittle,versionID);
        ticketRequest.clientId = null;
        try {
            createTicket(ticketRequest);
        }catch (Exception e ){
            exception = e;
        }
    }
}
