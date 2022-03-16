package fr.formation.formasion.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import fr.formation.formasion.model.Role;
import fr.formation.formasion.model.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
	
	@Query("SELECT ur FROM UserRole ur WHERE ur.user.username=:username AND ur.role=:role")
	Optional<UserRole> findByUsernameAndRole(String username, Role role);
}
