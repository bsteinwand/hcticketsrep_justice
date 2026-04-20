package edu.helenacollege.hctickets.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import edu.helenacollege.hctickets.model.User;
import edu.helenacollege.hctickets.model.UserApplicationRole;
public interface UserApplicationRoleRepository extends JpaRepository<UserApplicationRole, Integer> {
	//List<UserApplicationRole> findUserApplicationRoleWithUser(@Param("userId") int userId);
}