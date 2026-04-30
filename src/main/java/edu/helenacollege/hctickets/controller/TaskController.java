package edu.helenacollege.hctickets.controller;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.helenacollege.hctickets.dto.TaskCreateDto;
import edu.helenacollege.hctickets.dto.TaskResponseDto;
import edu.helenacollege.hctickets.dto.TicketAssignmentCreateDto;
import edu.helenacollege.hctickets.dto.TicketAssignmentResponseDto;
import edu.helenacollege.hctickets.dto.TicketResponseDto;
import edu.helenacollege.hctickets.dto.UserApplicationRoleResponseDto;
import edu.helenacollege.hctickets.dto.UserResponseDto;
import edu.helenacollege.hctickets.service.ApplicationService;
import edu.helenacollege.hctickets.service.DataCacheService;
import edu.helenacollege.hctickets.service.TaskService;
import edu.helenacollege.hctickets.service.TicketAssignmentService;
import edu.helenacollege.hctickets.service.TicketService;
import edu.helenacollege.hctickets.service.UserApplicationRoleService;
import edu.helenacollege.hctickets.service.UserService;
import edu.helenacollege.hctickets.service.impl.TicketAssignmentServiceImpl;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/task")
public class TaskController
{
	private final UserService userService;
    private final DataCacheService dataCacheService;
    private final ApplicationService applicationService;
    private final TicketService ticketService;
    private final TaskService taskService;
    private final TicketAssignmentService assignmentService;
    private final UserApplicationRoleService userAppRoleService;
    
    public TaskController(UserService userService, DataCacheService dataCacheService, TicketService ticketService,
    		TaskService taskService, TicketAssignmentService assignmentService, UserApplicationRoleService userAppRoleService, ApplicationService appService) 
    {
        this.userService = userService;
        this.dataCacheService = dataCacheService;
        this.ticketService = ticketService;
        this.taskService = taskService;
        this.assignmentService = assignmentService;
        this.userAppRoleService = userAppRoleService;
        this.applicationService = appService;
    }
    
    @GetMapping
    public String listTasks(Model model) {
    	TicketResponseDto ticket = ticketService.findById(1);
    	UserResponseDto user = userService.findById(1);
    	model.addAttribute("ticket", ticket);
        model.addAttribute("task", new TaskCreateDto("", "", 0, 0, "","",null));
        return "task/taskform";
    }
    
    @GetMapping("/create/ticket/{ticketId}")
    public String taskForm(Model model,@PathVariable int ticketId) {
    	UserResponseDto user = userService.findById(1);
    	TicketResponseDto ticket = ticketService.findById(ticketId);
    	List<TicketAssignmentResponseDto> assignments = assignmentService.findAll().stream().filter(ta -> ta.technicianId().equals(user.id()) && ta.ticketId().equals(ticketId)).toList();
    	TicketAssignmentResponseDto assign = assignments.getFirst();
    	List<UserApplicationRoleResponseDto> list = userAppRoleService.findAll().stream().filter(u -> u.userId().equals(user.id())).toList();
    	List<UserResponseDto> technitions = new ArrayList<UserResponseDto>();
    	for(UserApplicationRoleResponseDto response: userAppRoleService.findAll().stream().filter(u -> u.appRoleId() <= 3).toList())
    	{
    		technitions.add(userService.findById(response.userId()));
    	}
    	List<String> priorities = new ArrayList<String>();
    	priorities.add("Urgent");
    	priorities.add("Emergency");
    	priorities.add("Relaxed");
    	if(list.getFirst().userId() == user.id() && list.getFirst().appRoleId() <= 2)
    	{
    		model.addAttribute("priorities", priorities);
        	model.addAttribute("technitions", technitions);
        	model.addAttribute("app", applicationService.findById(ticket.applicationId()));
            model.addAttribute("task", new TaskCreateDto("", "", ticket.id(), user.id(), "Incomplete","",(OffsetDateTime.now()).plusDays(2)));
            return "task/taskform";
    	}
    	if(assign == null && ticket == null && user.id() != assign.technicianId())
    	{
    		model.addAttribute("fail","Ticket/User is not valid");
    		return "task/fail";
    	}
    	
    	model.addAttribute("priorities", priorities);
    	model.addAttribute("technitions", technitions);
    	model.addAttribute("app", applicationService.findById(ticket.applicationId()));
        model.addAttribute("task", new TaskCreateDto("", "", ticket.id(), user.id(), "Incomplete","",(OffsetDateTime.now()).plusDays(2)));
        return "task/taskform";
    }
    
    @PostMapping
    //@HxRequest
    public String createTask(@Valid @ModelAttribute TaskCreateDto task, BindingResult result, Model model) {
    	if (result.hasErrors()) {
    		model.addAttribute("fail",result.getAllErrors());
            return "task/fail";
        }
    	taskService.create(task);
        model.addAttribute("users", userService.findAll());
        return "redirect:task/" + task.userId();
    }
    
    @GetMapping("/technition/{technitionId}")
    public String getTechnitionTasks(@PathVariable Integer technitionId, Model model) {
        if (userService.findById(technitionId) == null || !(userService.findById(technitionId).status().equals("Active"))) {
        	model.addAttribute("fail","Technition not found");
        	return "task/fail";
        }
        List<TaskResponseDto> startTasks = taskService.findAll().stream().filter(t -> !(t.status().equals("Complete")) && !(t.status().equals("Closed"))).toList();
    	UserResponseDto user = userService.findById(technitionId);
    	List<TaskResponseDto> tasks = new ArrayList<TaskResponseDto>();
    	for(TaskResponseDto task : startTasks)
    	{
    		if(task.userId() == user.id())
    		{
    			tasks.add(task);
    		}
    	}
    	
        model.addAttribute("user", user);
        model.addAttribute("tasks", tasks);
        return "task/tasklist";
    }
}
