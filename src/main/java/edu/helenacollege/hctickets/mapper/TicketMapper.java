package edu.helenacollege.hctickets.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import edu.helenacollege.hctickets.dto.TicketResponseDto;
import edu.helenacollege.hctickets.model.Ticket;
import edu.helenacollege.hctickets.model.User;

@Mapper(componentModel = "spring")
public interface TicketMapper {

    @Mapping(target = "applicationId", source = "application.id")
    @Mapping(target = "closedBy", source = "closedBy.id")
    @Mapping(target = "createdBy", source = "createdBy.id")
    TicketResponseDto toResponseDto(Ticket entity);
    default Integer map(User value) {
        if (value == null) {
            return null;
        }

        return value.getId();
    }
}