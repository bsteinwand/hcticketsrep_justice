package edu.helenacollege.hctickets.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import edu.helenacollege.hctickets.dto.UserApplicationRoleResponseDto;
import edu.helenacollege.hctickets.dto.UserApplicationRoleUpdateDto;
import edu.helenacollege.hctickets.model.UserApplicationRole;

@Mapper(componentModel = "spring")
public interface UserApplicationRoleMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "appId", source = "application.id")
    @Mapping(target = "appRoleId", source = "applicationRole.id")
    UserApplicationRoleResponseDto toResponseDto(UserApplicationRole entity);

    void updateEntity(UserApplicationRoleUpdateDto dto, @MappingTarget UserApplicationRole entity);
}

