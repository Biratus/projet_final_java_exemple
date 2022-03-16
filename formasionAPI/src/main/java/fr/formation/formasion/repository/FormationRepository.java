package fr.formation.formasion.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.formation.formasion.model.Formation;

public interface FormationRepository extends JpaRepository<Formation,String> {
	
}
