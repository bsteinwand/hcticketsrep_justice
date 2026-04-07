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
import org.springframework.web.bind.annotation.RequestParam;

import edu.helenacollege.hctickets.dto.TicketAssignmentCreateDto;
import edu.helenacollege.hctickets.dto.TicketAssignmentResponseDto;
import edu.helenacollege.hctickets.dto.TicketResponseDto;
import edu.helenacollege.hctickets.dto.UserApplicationRoleResponseDto;
import edu.helenacollege.hctickets.dto.UserCreateDto;
import edu.helenacollege.hctickets.dto.UserResponseDto;
import edu.helenacollege.hctickets.model.User;
import edu.helenacollege.hctickets.service.DataCacheService;
import edu.helenacollege.hctickets.service.TicketService;
import edu.helenacollege.hctickets.service.UserApplicationRoleService;
import edu.helenacollege.hctickets.service.UserService;
import edu.helenacollege.hctickets.service.impl.TicketAssignmentServiceImpl;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
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

    public TicketAssignmentController(UserService userService, DataCacheService dataCacheService, TicketService ticketService, 
    		UserApplicationRoleService appRoleService, TicketAssignmentServiceImpl ticketAssignmentService) {
        this.userService = userService;
        this.dataCacheService = dataCacheService;
        this.ticketService = ticketService;
        this.userAppRoleService = appRoleService;
        this.ticketAssignmentService = ticketAssignmentService;
    }
    
    @GetMapping
    public String listUsers(Model model) {
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
    	List<UserApplicationRoleResponseDto> Techs = userAppRoleService.findAll();
    	for(UserApplicationRoleResponseDto t : Techs)
    	{
    		if(t.appId() == ticket.applicationId() || t.appRoleId() >= 3 || userService.findById(t.userId()).status().equals("Active"))
    		{
    			availTechs.add(userService.findById(t.id()));
    		}
    	}
    	UserResponseDto user = userService.findById(1);
    	if(ticket == null || !user.status().equals("Active"))// || user.roleId() >= 3) //byon does not have a role id but works otherwise
    	{
    		return "user/list";
    	}
    	model.addAttribute("technitions", availTechs);
    	model.addAttribute("ticket", ticket);
        model.addAttribute("assignment", new TicketAssignmentCreateDto(ticket.id(),1,user.id()));
        return "ticketassignment/assignform";
    }
    
    @PostMapping("/assign/{id}")
    @HxRequest
    public String createTicketAssignment(@Valid @ModelAttribute TicketAssignmentCreateDto assignment, @RequestParam(required = false) BindingResult result, Model model) {
        System.out.println("Created");
    	if (result.hasErrors()) {
            //model.addAttribute("roles", List.of("USER", "ADMIN"));
            return "user/list";
        }
        //userService.saveUser(user, roles != null ? roles : Set.of());
        ticketAssignmentService.create(assignment);
        model.addAttribute("users", userService.findAll());
        return "user/list";
    }
    
    @GetMapping("/{id}")
    public String editUserForm(@PathVariable Integer id, Model model) {
    	UserResponseDto user = userService.findById(id);
    	List<TicketAssignmentResponseDto> assignments = ticketAssignmentService.findAll();
    	List<TicketResponseDto> tickets = new ArrayList<TicketResponseDto>();
        if (user == null) {
            return "user/list"; // fallback
        }
        for(TicketAssignmentResponseDto assignment : assignments)
        {
        	if(assignment.technicianId() == user.id())
        	{
        		tickets.add(ticketService.findById(assignment.id()));
        	}
        }
        model.addAttribute("user", user);
        model.addAttribute("tickets", tickets);
        return "ticketassignment/assignform";
    }
}
