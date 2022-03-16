package fr.formation.formasion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.formation.formasion.model.Formation;
import fr.formation.formasion.repository.FormationRepository;

@Service
public class FormationService {

	@Autowired
	private FormationRepository repository;
	
	public Formation create(Formation f) {
		return repository.save(f);
	}
	
	public void delete(String libelle) {
		repository.deleteById(libelle);
	}
	
	public List<Formation> getAll() {
		return repository.findAll();
	}
}
