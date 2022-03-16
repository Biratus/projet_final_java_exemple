package fr.formation.formasion.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import fr.formation.formasion.model.Filiere;
import fr.formation.formasion.model.Personne;
import fr.formation.formasion.model.TypePersonne;
import fr.formation.formasion.service.PersonneService;

@RestController
@RequestMapping("/api/personne")
@CrossOrigin
public class PersonneController {

	@Autowired
	private PersonneService ps;

	@GetMapping("")
	public List<Personne> findAll(@RequestParam(required=false) TypePersonne type) {
		if(Objects.nonNull(type)) return ps.findAll(type);
		return ps.findAll();
	}

	@GetMapping("/{id}")
	public Personne getById(@PathVariable Integer id) {
		return ps.getById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "La personne n'existe pas"));
	}

	@PostMapping("")
	public void createPersonne(@RequestBody Personne p) {
		ps.create(p);
	}

	@PutMapping("")
	public void updatePersonne(@RequestBody Personne p) {
		ps.update(p);
	}

	@PutMapping("/{id}")
	public void deletePersonne(@PathVariable Integer id) {
		ps.delete(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "La personne n'existe pas"));
	}

	/*
	 * Renvoie la liste des formateurs intervenant sur une filière donné en paramètre
	 */
	@GetMapping("/formateurs")
	public List<Personne> getFormateursOfFiliere(@RequestParam(name = "filiere_id") Integer id) {
		Filiere f = new Filiere();
		f.setId(id);
		return ps.getFormateursOfFiliere(f);
	}
	
	@GetMapping("/nouveau")
	public List<Personne> getNouveauSouhaitantFormation(@RequestParam (required=false) String formation) {
		if(Objects.nonNull(formation)) 
			return ps.getNouvellePersonneSouhaitantFormation(formation);
		else 
			return ps.getNouvellePersonneSouhaitantFormation();
	}
}
