package fr.formation.formasion.controller;

import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import fr.formation.formasion.model.Module;
import fr.formation.formasion.model.User;
import fr.formation.formasion.service.ModuleService;
import fr.formation.formasion.service.UserService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/module")
@CrossOrigin
@Slf4j
public class ModuleController {

	@Autowired
	private ModuleService ms;

	@Autowired
	private UserService us;
	
	@GetMapping("")
	public List<Module> findAll(@RequestHeader Map<String,String> headers) {
		String encodedAuth = headers.get("authorization").split(" ")[1];
		String decoded = new String(Base64.getDecoder().decode(encodedAuth));
		Optional<User> optU = us.getByUsername(decoded.split(":")[0]);
		return ms.getModuleOfPersonne(optU.get().getPersonne().getType(), decoded.split(":")[0]);
	}

	@GetMapping("/{id}")
	public Module getById(@PathVariable Integer id) {
		return ms.getById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Le module n'existe pas"));
	}

	@PostMapping("")
	public void createModule(@RequestBody Module p) {
		ms.create(p);
	}

	@PutMapping("")
	public void updateModule(@RequestBody Module p) {
		ms.update(p);
	}

	@DeleteMapping("/{id}")
	public void deleteModule(@PathVariable Integer id) {
		ms.delete(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Le module n'existe pas"));
	}

	/*
	 * Renvoie les modules d'une filière
	 */
	@GetMapping("/filiere/{id}")
	public List<Module> getModulesOfFiliere(@PathVariable Integer id) {
		return ms.findByFiliereId(id);
	}

	/*
	 * Renvoie les modules qui n'ont pas de formateur assignés
	 */
	@GetMapping("/formateurMissing")
	public List<Module> getModuleWithoutFormateur() {
		return ms.getModuleWithoutFormateur();
	}

	@GetMapping("/personne")
	public List<Module> getModuleOfPersonne(@RequestParam String username) {
		Optional<User> optU = us.getByUsername(username);
		if(optU.isEmpty()) {
			return null;
		}
		return ms.getModuleOfPersonne(optU.get().getPersonne().getType(), username);
	}
	
	@GetMapping("/search")
	public List<Module> searchModules(@RequestParam(required=false) String libelle,@RequestParam(required=false) Integer filiereId,@RequestParam(required=false) Integer formateurId,@RequestHeader Map<String,String> headers) {
		String encodedAuth = headers.get("authorization").split(" ")[1];
		String decoded = new String(Base64.getDecoder().decode(encodedAuth));
		Optional<User> optU = us.getByUsername(decoded.split(":")[0]);
		if(optU.isEmpty()) {
			return null;
		}
		return ms.searchModulesForUser(libelle, filiereId,formateurId, optU.get());
	}
}
