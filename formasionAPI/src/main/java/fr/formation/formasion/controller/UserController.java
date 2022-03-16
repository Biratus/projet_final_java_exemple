package fr.formation.formasion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import fr.formation.formasion.model.User;
import fr.formation.formasion.model.dto.ChangePasswordDTO;
import fr.formation.formasion.model.dto.MessageDTO;
import fr.formation.formasion.service.UserService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/user")
@Slf4j
@CrossOrigin
public class UserController {

	@Autowired
	UserService service;

	@GetMapping("/{name}")
	public User getByUsername(@PathVariable String name) {
		return service.getByUsername(name).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Le nom d'utilisateur n'existe pas"));
	}

	@PostMapping("")
	public User createUser(@RequestBody User u) {
		return service.create(u);
	}
	
	@PostMapping("/changePassword")
	public MessageDTO changePassword(@RequestBody ChangePasswordDTO dto) {
		if(service.changePassword(dto)) {
			return new MessageDTO("Mot de passe changé avec succès!");
		} else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "L'ancien mot de passe n'est pas correct");
		
	}
}
