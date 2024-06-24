package com.example.soporte.DTO;

import com.example.soporte.exceptions.InvalidArgumentsException;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import com.example.soporte.DTO.validations.NotBlankIfPresent;

import java.util.ArrayList;
import java.util.List;

public class UpdateTicketDTO{
    @NotBlankIfPresent(message = "Description must not be blank if present.")
    @Size(max = 300, message = "description max size is 300 characters.")
    public String description;

    @NotBlankIfPresent(message = "status must not be blank if present.")
    public String status;

    @Positive(message = "Employee id must be positive.")
    public Long employeeId;

    @Positive(message = "Version id must be positive.")
    public Long versionId;

    @Size(max = 20, message = "Can not relate more than 20 tasks at once.")
    public List<Long> tasksToRelate = new ArrayList<>();

    @Size(max = 20, message = "Can not unrelate more than 20 tasks at once.")
    public List<Long> tasksToUnrelate = new ArrayList<>();

    /**
     * Validates the fields to ensure at least one field is present to update a ticket.
     *
     * @throws InvalidArgumentsException if all fields required for updating are null or empty
     */
    public void validate() throws InvalidArgumentsException {
        if (allFieldsAreNull()) {
            throw new InvalidArgumentsException("At least one field must be present to update a ticket.");
        }
    }

    /**
     * Checks if all fields required for updating a ticket are null or empty.
     *
     * @return true if all fields are null or empty, false otherwise
     */
    private boolean allFieldsAreNull() {
        return description == null && status == null && employeeId == null && versionId == null && tasksToRelate.isEmpty() && tasksToUnrelate.isEmpty();
    }
}
