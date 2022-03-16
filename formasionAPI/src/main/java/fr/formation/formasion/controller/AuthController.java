package fr.formation.formasion.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import fr.formation.formasion.model.User;
import fr.formation.formasion.service.UserService;

@RestController
@RequestMapping
@CrossOrigin
public class AuthController {
	
	@Autowired
	UserService service;
	
	@PostMapping("/authenticate")
	public User authenticateUser(@RequestBody User u) {
		Optional<User> optU = service.getByUsername(u.getUsername());
		if(optU.isPresent()) {
			if(!optU.get().getPassword().equals(u.getPassword())) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Le mot de passe est incorrect");
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Le nom d'utilisateur n'existe pas");
		}
		
		return optU.get();
	}

}
