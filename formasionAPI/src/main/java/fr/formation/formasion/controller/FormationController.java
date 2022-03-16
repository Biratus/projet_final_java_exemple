	package fr.formation.formasion.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.formation.formasion.model.Formation;
import fr.formation.formasion.service.FormationService;

@RestController
@RequestMapping("/api/formation")
@CrossOrigin
public class FormationController {
	
	@Autowired
	FormationService service;
	
	@GetMapping("")
	public List<Formation> findAll() {
		return service.getAll();
	}
	
	@PostMapping("")
	public Formation createFormation(@RequestBody Formation f) {
		return service.create(f);
	}
	
	@DeleteMapping("/{libelle}")
	public void deleteFormation(@PathVariable String libelle) {
		service.delete(libelle);
	}

}
