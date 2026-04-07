package edu.helenacollege.hctickets.controller;

//import java.util.List;
//import java.util.Set;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import edu.helenacollege.hctickets.dto.TicketCommentCreateDto;
//import edu.helenacollege.hctickets.dto.TicketCommentResponseDto;
//import edu.helenacollege.hctickets.dto.TicketCommentUpdateDto;
//import edu.helenacollege.hctickets.dto.TicketResponseDto;
//import edu.helenacollege.hctickets.dto.UserCreateDto;
//import edu.helenacollege.hctickets.dto.UserResponseDto;
//import edu.helenacollege.hctickets.service.DataCacheService;
//import edu.helenacollege.hctickets.service.TicketCommentService;
//import edu.helenacollege.hctickets.service.TicketService;
//import edu.helenacollege.hctickets.service.UserService;
//import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
//import jakarta.validation.Valid;
//
//@Controller
//@RequestMapping("/ticketcomment")
public class TicketCommentController
{
	//get method, add ticket, set internal to true by default 
//	private final DataCacheService dataCacheService;
//	private final TicketCommentService commentService;
//	private final TicketService ticketService;
//	private final UserService userService;
	
//	public TicketCommentController(TicketCommentService commentService, DataCacheService dataCacheService, TicketService ticketService, UserService userService) {
//        this.commentService = commentService;
//        this.dataCacheService = dataCacheService;
//        this.ticketService = ticketService;
//        this.userService = userService;
//	}
    
	
//	@GetMapping("/ticket/{ticketid}/create")
//    @HxRequest
//    public String newCommentForm(Model model,@PathVariable Integer ticketId) {
//		UserResponseDto user = userService.findById(1);
//		TicketResponseDto ticket = ticketService.findById(ticketId);
//		if (ticket == null || user == null) {
//            return "ticketcomment/list"; // fallback
//        }
//        model.addAttribute("comment", new TicketCommentCreateDto(ticket.id(),user.id()));
//        return "ticketcomment/form";
//    }
//	
//	@GetMapping("/ticket/{ticketid}/edit")
//    @HxRequest
//    public String editCommentForm(@PathVariable Integer ticketId, Model model) {
//    	TicketCommentResponseDto comment = commentService.findById(ticketId);
//    	UserResponseDto user = userService.findById(1);
//        if (comment == null) {
//            return "ticketcomment/list"; // fallback
//        }
//        model.addAttribute("comment", comment);
//        //System.err.println("RoleId: " + comment.roleId());
//        model.addAttribute("roles", dataCacheService.findRoles()); //List.of("USER", "ADMIN"));
//        return "ticketcomment/form";
//    }
//	
//	@PostMapping
//    @HxRequest
//    public String createComment(@Valid @ModelAttribute TicketCommentCreateDto comment, @RequestParam(required = false) Set<String> roles, BindingResult result, Model model) {
//        if (result.hasErrors()) {
//            model.addAttribute("roles", List.of("USER", "ADMIN"));
//            return "user/form";
//        }
//        //userService.saveUser(user, roles != null ? roles : Set.of());
//        commentService.create(comment);
//        model.addAttribute("comments", commentService.findAll());
//        return "user/partials :: userRows";
//    }
//	
//	 @PostMapping("/{id}")
//	 @HxRequest
//	  public String updateUser(@PathVariable Integer id, @Valid @ModelAttribute TicketCommentUpdateDto comment, @RequestParam(required = true) int roleId, BindingResult result, Model model) {
//	      if (result.hasErrors()) {
//	          model.addAttribute("roles", dataCacheService.findRoles());
//	          return "user/form";
//	      }
//	      //user.setId(id);
//	      //user.roleId(roleId);
//	      commentService.update(id, comment);
//	      model.addAttribute("users", commentService.findAll());
//	      //return "user/partials :: userRow";
//	      return "user/partials/userRow";
//	  }
}
