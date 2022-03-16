package fr.formation.formasion.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.formation.formasion.model.User;

public interface UserRepository extends JpaRepository<User,Integer>{
	Optional<User> findByUsername(String name);
}
