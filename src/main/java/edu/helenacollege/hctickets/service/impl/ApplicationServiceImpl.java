package edu.helenacollege.hctickets.service.impl;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.helenacollege.hctickets.dto.ApplicationCreateDto;
import edu.helenacollege.hctickets.dto.ApplicationResponseDto;
import edu.helenacollege.hctickets.mapper.ApplicationMapper;
import edu.helenacollege.hctickets.model.Application;
import edu.helenacollege.hctickets.repository.ApplicationRepository;
import edu.helenacollege.hctickets.service.ApplicationService;
import jakarta.persistence.EntityNotFoundException;
//import lombok.RequiredArgsConstructor;

@Service
//@RequiredArgsConstructor
@Transactional
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final ApplicationMapper mapper;
    
    public ApplicationServiceImpl(ApplicationRepository applicationRepository, ApplicationMapper mapper) {
		super();
		this.applicationRepository = applicationRepository;
		this.mapper = mapper;
	}

	@Override
    public ApplicationResponseDto create(ApplicationCreateDto dto) {

        Application app = new Application();
        app.setAppName(dto.appName());
        app.setDescription(dto.description());
        app.setStatus(dto.status());
        app.setCreationDate(OffsetDateTime.now());
        
        app = applicationRepository.save(app);
        return mapper.toResponseDto(app);
    }

//    @Override
//    public ApplicationResponseDto update(Integer id, ApplicationUpdateDto dto) {
//
//        Application app = applicationRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Application not found"));
//
//        app.setAppName(dto.appName());
//        app.setDescription(dto.description());
//        app.setStatus(dto.status());
//
//        if (dto.closedBy() != null) {
//        	app.setClosedBy(
//                    userRepository.findById(dto.closedBy())
//                            .orElseThrow(() -> new EntityNotFoundException("User not found"))
//            );
//            app.setClosedDate(OffsetDateTime.now());
//        }
//
//        app = applicationRepository.save(app);
//        return mapper.toResponseDto(app);
//    }

    @Override
    @Transactional(readOnly = true)
    public ApplicationResponseDto findById(Integer id) {
        return applicationRepository.findById(id)
                .map(mapper::toResponseDto)
                .orElseThrow(() -> new EntityNotFoundException("Application not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ApplicationResponseDto> findAll() {
        return applicationRepository.findAll()
                .stream()
                .map(mapper::toResponseDto)
                .toList();
    }

    @Override
    public void delete(Integer id) {
        applicationRepository.deleteById(id);
    }
}