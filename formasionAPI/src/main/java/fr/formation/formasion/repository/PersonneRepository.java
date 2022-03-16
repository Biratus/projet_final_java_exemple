package fr.formation.formasion.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.formation.formasion.model.Filiere;
import fr.formation.formasion.model.Formation;
import fr.formation.formasion.model.Personne;
import fr.formation.formasion.model.TypePersonne;

public interface PersonneRepository extends JpaRepository<Personne, Integer> {
	@Modifying
	@Query("UPDATE Personne p SET p.filiere = :f WHERE p.id=:pId")
	@Transactional
	void bindPersonneToFiliere(@Param("pId") Integer personneId, @Param("f") Filiere filiere);

	@Query("SELECT p FROM Personne p JOIN p.modules m WHERE p.type = 'FORMATEUR' AND m.filiere = :f")
	List<Personne> findFormateursOfFiliere(@Param("f") Filiere f);
	
	List<Personne> findByFormationSouhaiteAndType(Formation f,TypePersonne type);

	List<Personne> findByType(TypePersonne type);

	@Modifying
	@Query("UPDATE Personne p SET p.type=:type where p=:p")
	@Transactional
	void updatePersonneType(Personne p, TypePersonne type);
}
