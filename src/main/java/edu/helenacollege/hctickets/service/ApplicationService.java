package edu.helenacollege.hctickets.service;

import java.util.List;

import edu.helenacollege.hctickets.dto.ApplicationCreateDto;
import edu.helenacollege.hctickets.dto.ApplicationResponseDto;
import edu.helenacollege.hctickets.dto.ApplicationUpdateDto;

public interface ApplicationService {

    ApplicationResponseDto create(ApplicationCreateDto dto);

    //ApplicationResponseDto update(Integer id, ApplicationUpdateDto dto);

    ApplicationResponseDto findById(Integer id);

    List<ApplicationResponseDto> findAll();

    void delete(Integer id);
}