package fr.formation.formasion.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import fr.formation.formasion.model.Filiere;
import fr.formation.formasion.model.Formation;
import fr.formation.formasion.model.Personne;
import fr.formation.formasion.model.TypePersonne;
import fr.formation.formasion.model.dto.UserDTO;
import fr.formation.formasion.repository.PersonneRepository;

@Service
public class PersonneService {

	@Autowired
	private PersonneRepository pr;

	@Autowired
	private UserService userService;

	public Personne create(Personne p) {
		return this.pr.save(p);
	}

	public List<Personne> findAll() {
		return this.pr.findAll();
	}
	
	public List<Personne> findAll(TypePersonne type) {
		return this.pr.findByType(type);
	}

	public Optional<Personne> getById(Integer id) {
		return pr.findById(id);
	}

	public void update(Personne p) {
		this.pr.save(p);
	}

	public Optional<Boolean> delete(Personne p) {
		try {
			this.pr.delete(p);
			return Optional.of(Boolean.TRUE);
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	public Optional<Boolean> delete(Integer id) {
		try {
			this.pr.deleteById(id);
			return Optional.of(Boolean.TRUE);
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	public List<Personne> getFormateursOfFiliere(Filiere f) {
		return pr.findFormateursOfFiliere(f);
	}

	public void bindStagiairesToFiliere(List<Personne> pList, Filiere f) {
		for (Personne p : pList) {
			bindStagiaireToFiliere(p, f);
		}
	}
	

	public void bindStagiaireToFiliere(Personne p, Filiere f) {
		pr.bindPersonneToFiliere(p.getId(), f);
	}

	public Boolean checkAllNouvelInscrit(List<Personne> pList) {
		for (Personne p : pList) {
			if (!TypePersonne.NOUVEL_INSCRIT.equals(pr.getById(p.getId()).getType()))
				return false;
		}
		return true;
	}

	@Transactional
	public UserDTO inscription(Personne personne) {
		// Create User
		personne.setDateInscription(LocalDate.now());
		personne.setType(TypePersonne.NOUVEL_INSCRIT);
		personne.setFiliere(null);
		personne = create(personne);
		return userService.inscription(personne);
		

	}
	
	public List<Personne> getNouvellePersonneSouhaitantFormation(String libelleFormation) {
		return pr.findByFormationSouhaiteAndType(new Formation(libelleFormation), TypePersonne.NOUVEL_INSCRIT);
	}
	
	public List<Personne> getNouvellePersonneSouhaitantFormation() {
		return pr.findByType(TypePersonne.NOUVEL_INSCRIT);
	}

	public void setTypeOf(Personne p, TypePersonne type) {
		pr.updatePersonneType(p,type);
	}
}
