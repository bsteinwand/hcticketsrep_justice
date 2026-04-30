package edu.helenacollege.hctickets.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import edu.helenacollege.hctickets.dto.TaskResponseDto;
import edu.helenacollege.hctickets.dto.TaskUpdateDto;
import edu.helenacollege.hctickets.model.Task;

@Mapper(componentModel = "spring")
public interface TaskMapper {
	@Mapping(target = "userId", source = "user.id")
    @Mapping(target = "ticketId", source = "ticket.id")
	
    TaskResponseDto toResponseDto(Task entity);

    void updateEntity(TaskUpdateDto dto, @MappingTarget Task entity);
}