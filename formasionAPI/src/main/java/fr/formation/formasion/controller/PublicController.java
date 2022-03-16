package fr.formation.formasion.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.formation.formasion.model.Personne;
import fr.formation.formasion.model.dto.SimpleFormationDTO;
import fr.formation.formasion.model.dto.UserDTO;
import fr.formation.formasion.service.FormationService;
import fr.formation.formasion.service.PersonneService;

@RestController
@RequestMapping("/public")
@CrossOrigin
public class PublicController {
	
	@Autowired
	PersonneService persService;
	
	@Autowired
	FormationService formationService;

	@PostMapping("/inscription")
	public UserDTO inscription(@RequestBody Personne personne) {
		
		return persService.inscription(personne);
	}
	
	@GetMapping("/formations")
	public List<SimpleFormationDTO> getAllFormations() {
		return formationService.getAll().stream().map(f -> new SimpleFormationDTO(f.getLibelle())).collect(Collectors.toList());
	}
}
