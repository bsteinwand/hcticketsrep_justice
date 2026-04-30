package edu.helenacollege.hctickets.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.helenacollege.hctickets.dto.TicketAssignmentCreateDto;
import edu.helenacollege.hctickets.dto.TicketAssignmentResponseDto;
import edu.helenacollege.hctickets.dto.TicketResponseDto;
import edu.helenacollege.hctickets.dto.UserApplicationRoleResponseDto;
import edu.helenacollege.hctickets.dto.UserResponseDto;
import edu.helenacollege.hctickets.repository.TicketAssignmentRepository;
import edu.helenacollege.hctickets.service.DataCacheService;
import edu.helenacollege.hctickets.service.TicketService;
import edu.helenacollege.hctickets.service.UserApplicationRoleService;
import edu.helenacollege.hctickets.service.UserService;
import edu.helenacollege.hctickets.service.impl.TicketAssignmentServiceImpl;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/ticketassignment")
public class TicketAssignmentController
{
	private final UserService userService;
    private final DataCacheService dataCacheService;
    private final TicketService ticketService;
    private final UserApplicationRoleService userAppRoleService;
    private final TicketAssignmentServiceImpl ticketAssignmentService;
    private final TicketAssignmentRepository ticketAssignmentRepository;

    public TicketAssignmentController(UserService userService, DataCacheService dataCacheService, TicketService ticketService, 
    		UserApplicationRoleService appRoleService, TicketAssignmentServiceImpl ticketAssignmentService,
    		TicketAssignmentRepository ticketAssignmentRepository) {
        this.userService = userService;
        this.dataCacheService = dataCacheService;
        this.ticketService = ticketService;
        this.userAppRoleService = appRoleService;
        this.ticketAssignmentService = ticketAssignmentService;
        this.ticketAssignmentRepository = ticketAssignmentRepository;
    }
    
    @GetMapping
    public String listTicketAssignments(Model model) {
//        List<User> users = userService.findAll();
    	List<UserResponseDto> users = userService.findAll();
    	TicketResponseDto ticket = ticketService.findById(1);
    	List<UserResponseDto> Techs = userService.findAll();
    	UserResponseDto user = userService.findById(1);
    	model.addAttribute("technitions", Techs);
    	model.addAttribute("ticket", ticket);
        model.addAttribute("assignment", new TicketAssignmentCreateDto(ticket.id(),1,user.id()));
        return "ticketassignment/assignform";
    }
    
    @GetMapping("/assign/{ticketId}")
    public String assignTicketForm(Model model,@PathVariable int ticketId) {
    	TicketResponseDto ticket = ticketService.findById(ticketId);
    	List<UserResponseDto> availTechs = new ArrayList<UserResponseDto>();
    	List<UserApplicationRoleResponseDto> techs = userAppRoleService.findAll();
    	for(UserApplicationRoleResponseDto t : techs)
    	{
            UserResponseDto userResponseDto = userService.findById(t.userId());
    		if(t.appId().equals(ticket.applicationId()) && t.appRoleId() <= 3 && userResponseDto.status().equals("Active"))
    		{
    			availTechs.add(userResponseDto);
    		}
    	}
    	UserResponseDto user = userService.findById(1);
    	if(ticket == null && !user.status().equals("Active") && user.roleId() <= 3)
    	{
    		model.addAttribute("fail","ticket does not exist or your account is not valid");
    		return "ticketassignment/fail";
    	}
        TicketAssignmentCreateDto ticketAssignmentCreateDto = new TicketAssignmentCreateDto(ticket.id(),1,user.id());


    	model.addAttribute("technicians", availTechs);
    	model.addAttribute("ticket", ticket);
        model.addAttribute("assignment", ticketAssignmentCreateDto);
        return "ticketassignment/assignform";
    }
    
    @PostMapping
    //@HxRequest
    public String createTicketAssignment(@Valid @ModelAttribute TicketAssignmentCreateDto assignment, BindingResult result, Model model) {
    	if (result.hasErrors()) {
    		model.addAttribute("fail",result.getAllErrors());
            return "ticketassignment/fail";
        }
        ticketAssignmentService.create(assignment);
        model.addAttribute("users", userService.findAll());
        return "redirect:ticketassignment/" + assignment.technicianId();
    }
    
    @GetMapping("/{id}")
    public String getTechnitionAssignments(@PathVariable Integer id, Model model) {
        if (userService.findById(id) == null) {
        	model.addAttribute("fail","There is no user by that ID");
        	return "ticketasignment/fail";
        }
    	UserResponseDto user = userService.findById(id);
    	List<TicketAssignmentResponseDto> assignments = ticketAssignmentService.findAll().stream().filter(ta -> ta.technicianId().equals(id)).toList();
    	List<TicketResponseDto> tickets = new ArrayList<TicketResponseDto>();
        for(TicketAssignmentResponseDto assignment : assignments)
        {
        		tickets.add(ticketService.findById(assignment.ticketId()));
        }
        model.addAttribute("user", user);
        model.addAttribute("tickets", tickets);
        return "ticketassignment/assignlist";
    }
}
