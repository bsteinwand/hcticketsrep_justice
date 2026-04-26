package edu.helenacollege.hctickets.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import edu.helenacollege.hctickets.dto.TicketAssignmentResponseDto;
import edu.helenacollege.hctickets.dto.TicketAssignmentUpdateDto;
import edu.helenacollege.hctickets.model.TicketAssignment;
import edu.helenacollege.hctickets.model.User;

//import edu.helenacollege.hctickets.dto.TicketAssignmentResponseDto;
//import edu.helenacollege.hctickets.dto.TicketAssignmentUpdateDto;
//import edu.helenacollege.hctickets.model.TicketAssignment;


@Mapper(componentModel = "spring")
public interface TicketAssignmentMapper {

    @Mapping(target = "technicianId", source = "technician.id")
    @Mapping(target = "ticketId", source = "ticket.id")
    @Mapping(target = "assignedBy", source = "assignedBy.id")
    TicketAssignmentResponseDto toResponseDto(TicketAssignment entity);

    void updateEntity(TicketAssignmentUpdateDto dto, @MappingTarget TicketAssignment entity);
    
    default Integer map(User value) {
        if (value == null) {
            return null;
        }

        return value.getId();
    }
}
