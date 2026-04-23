package edu.helenacollege.hctickets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import edu.helenacollege.hctickets.model.Ticket;
import edu.helenacollege.hctickets.model.TicketAssignment;
import edu.helenacollege.hctickets.model.User;
public interface TicketAssignmentRepository extends JpaRepository<TicketAssignment, Integer> {
	@Query(value = "SELECT ta FROM TicketAssignment ta JOIN FETCH ta.technician"
			+ " JOIN FETCH ta.ticket WHERE ta.id = :id")
    TicketAssignment findTicketAssignmentDetails(@Param("id") int id, @Param("technician") User technician, @Param("ticket") Ticket ticket);
}
