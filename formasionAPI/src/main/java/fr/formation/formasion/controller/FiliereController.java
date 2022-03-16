package fr.formation.formasion.controller;

import java.util.AbstractMap.SimpleEntry;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
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

import fr.formation.formasion.model.Filiere;
import fr.formation.formasion.model.Personne;
import fr.formation.formasion.model.TypePersonne;
import fr.formation.formasion.model.User;
import fr.formation.formasion.service.FiliereService;
import fr.formation.formasion.service.UserService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/filiere")
@CrossOrigin
@Slf4j
public class FiliereController {

	@Autowired
	FiliereService fs;

	@Autowired
	UserService us;

	@GetMapping("")
	public List<Filiere> findAll(@RequestHeader Map<String, String> headers) {
		String encodedAuth = headers.get("authorization").split(" ")[1];
		String decoded = new String(Base64.getDecoder().decode(encodedAuth));
		Optional<User> optU = us.getByUsername(decoded.split(":")[0]);
		return fs.getFiliereOfPersonne(optU.get().getPersonne().getType(), decoded.split(":")[0]);

	}

	@GetMapping("/{id}")
	public Filiere getById(@PathVariable Integer id) {
		return fs.getById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "La filière n'existe pas"));
	}

	/*
	 * Il est possible d'affecter des stagiaires a la filière créer directement En
	 * mettant une liste de stagiaire (leur id) dans le body de la requète (détail
	 * dans la méthode create de FiliereService)
	 */
	@PostMapping("")
	public void createFiliere(@RequestBody Filiere f) {
		fs.create(f);
	}

	@PutMapping("")
	public void updateFiliere(@RequestBody Filiere f) {
		fs.update(f);
	}

	@PutMapping("/{id}")
	public void deleteFiliere(@PathVariable Integer id) {
		fs.delete(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "La filière n'existe pas"));
	}

	/*
	 * Affecte la liste de personnes donnée en body de la requète à la filière mis
	 * dans l'url
	 */
	@PutMapping("/stagiaires/{id}")
	public void setStagiairesOfFiliere(@RequestBody List<Personne> personnes, @PathVariable Integer id) {
		fs.setStagiairesOfFiliere(personnes, id);
	}

	@GetMapping("/personne")
	public List<Filiere> getFiliereOfPersonne(@RequestParam String username) {
		Optional<User> optU = us.getByUsername(username);
		if (optU.isEmpty()) {
			return null;
		}
		return fs.getFiliereOfPersonne(optU.get().getPersonne().getType(), username);
	}

	@GetMapping("/formation/{libelleFormation}")
	public List<Filiere> findByDormation(@PathVariable String libelleFormation) {
		return fs.getFilieresOfFormation(libelleFormation);
	}

	@GetMapping("/search")
	public List<Filiere> search(@RequestParam(required = false) String libelle,
			@RequestParam(required = false) Integer formateurId,
			@RequestParam(required = false) String active,
			@RequestHeader Map<String, String> headers) {
		String encodedAuth = headers.get("authorization").split(" ")[1];
		String decoded = new String(Base64.getDecoder().decode(encodedAuth));
		Optional<User> optU = us.getByUsername(decoded.split(":")[0]);
		if (optU.isEmpty()) {
			return null;
		}
		return fs.searchFilieres(libelle, formateurId, Objects.nonNull(active)?Boolean.parseBoolean(active):null, optU.get());
	}
	
	@PostMapping("/formations")
	public Map<String,List<Filiere>> getFilieresForFormation(@RequestBody List<String> list) {
		return list.stream()
				.map(formationLibelle -> new SimpleEntry<String,List<Filiere>>(formationLibelle,fs.getFilieresNotStartedOfFormation(formationLibelle)))
				.collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue));
	}
	
	@PostMapping("/inscription")
	public void inscritStagiairesFilieres(@RequestBody List<Personne> stagiaires) {
		//Check data OK
		for(Personne p : stagiaires) {
			if(!TypePersonne.NOUVEL_INSCRIT.equals(p.getType())
					|| Objects.isNull(p.getFiliere()))
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Les stagiaires doivent avoir le type nouvel inscrit et une filière affecté");
		}
		for(Personne p : stagiaires) {
			fs.setStagiaireOfFiliere(p,p.getFiliere());
		}
	}
}
