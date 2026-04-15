package edu.helenacollege.hctickets.dto;

public record TicketAssignmentCreateDto(
        Integer ticketId,
        Integer technicianId,
        Integer assignedBy
)
{
    public TicketAssignmentCreateDto() {
    this(0,0, 0);
}
}
