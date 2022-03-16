package fr.formation.formasion.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import fr.formation.formasion.model.Filiere;
import fr.formation.formasion.model.Module;
import fr.formation.formasion.model.TypePersonne;
import fr.formation.formasion.model.User;
import fr.formation.formasion.repository.ModuleRepository;

@Service
public class ModuleService {

	@Autowired
	private ModuleRepository mr;

	public void create(Module m) {
		m.setId(m.getFiliere().getLibelle() + "_" + m.getLibelle());
		this.mr.save(m);
	}

	public List<Module> findAll() {
		return this.mr.findAll();
	}

	public Optional<Module> getById(Integer id) {
		return this.mr.findById(id);
	}

	public void update(Module m) {
		this.mr.save(m);
	}

	public Optional<Boolean> delete(Module m) {
		try {
			this.mr.delete(m);
			return Optional.of(Boolean.TRUE);
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	public Optional<Boolean> delete(Integer id) {
		try {
			this.mr.deleteById(id);
			return Optional.of(Boolean.TRUE);
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	public List<Module> findByFiliereId(Integer id) {
		Filiere f = new Filiere();
		f.setId(id);
		return mr.findByFiliere(f);
	}

	public List<Module> getModuleWithoutFormateur() {
		return mr.findByFormateurIsNull();
	}

	public List<Module> getModuleOfPersonne(TypePersonne type, String username) {
		switch (type) {
		case STAGIAIRE:
			return mr.findModuleOfStagiaire(username);
		case FORMATEUR:
			return mr.findModuleOfFormateur(username);
		case GESTIONNAIRE:
			return mr.findAll();
		}
		return null;
	}

	public List<Module> searchModulesForUserByLibelle(String libelle, User u) {
		TypePersonne type = u.getPersonne().getType();
		String username = u.getUsername();
		switch (type) {
		case FORMATEUR:
			return mr.findModuleOfFormateurByLibelleContaining(username, libelle);
		case GESTIONNAIRE:
			return mr.findByLibelleContaining(libelle);
		}
		return null;
	}

	public List<Module> searchModulesForUserByFiliere(Integer filiereId, User u) {
		TypePersonne type = u.getPersonne().getType();
		String username = u.getUsername();
		switch (type) {
		case FORMATEUR:
			return mr.findModuleOfFormateurByFiliereId(username, filiereId);
		case GESTIONNAIRE:
			return mr.findByFiliere(new Filiere(filiereId));
		}
		return null;
	}

	public List<Module> searchModulesForUserByLibelleAndFiliere(String libelle, Integer filiereId, User u) {
		TypePersonne type = u.getPersonne().getType();
		String username = u.getUsername();
		switch (type) {
		case FORMATEUR:
			return mr.findModuleOfFormateurByLibelleContainingAndFiliereId(username, libelle, filiereId);
		case GESTIONNAIRE:
			return mr.findByLibelleContainingAndFiliereId(libelle, filiereId);
		}
		return null;
	}

	public List<Module> searchModulesByFiliereAndFormateur(Integer filiereId, Integer formateurId) {
		return mr.findByFiliereIdAndFormateurId(filiereId, formateurId);
	}

	public List<Module> searchModulesByLibelleAndFormateur(String libelle, Integer formateurId) {
		return mr.findByLibelleContainingAndFormateurId(libelle, formateurId);
	}

	public List<Module> searchModulesByLibelleAndFiliereAndFormateur(String libelle, Integer filiereId,
			Integer formateurId) {
		return mr.findByLibelleContainingAndFiliereIdAndFormateurId(libelle, filiereId, formateurId);
	}

	public List<Module> searchModulesForUser(String libelle, Integer filiereId, Integer formateurId, User user) {
		List<Module> modules = null;
		TypePersonne type = user.getPersonne().getType();
		String username = user.getUsername();

		if (Objects.nonNull(libelle)) {
			if (Objects.nonNull(filiereId)) {// libelle + filiere
				if (Objects.nonNull(formateurId))//Et formateur
					modules = searchModulesByLibelleAndFiliereAndFormateur(libelle, filiereId, formateurId);
				else
					modules = searchModulesForUserByLibelleAndFiliere(libelle, filiereId, user);
			} else {// libelle seul
				if (Objects.nonNull(formateurId))//Et formateur
					modules = searchModulesByLibelleAndFormateur(libelle, formateurId);
				else
					modules = searchModulesForUserByLibelle(libelle, user);
			}
		} else {// Sans libelle
			if (Objects.nonNull(filiereId)) {// Filiere seule
				if (Objects.nonNull(formateurId))//Et formateur
					modules = searchModulesByFiliereAndFormateur(filiereId, formateurId);
				else
					modules = searchModulesForUserByFiliere(filiereId, user);
				
			} else {// Sans rien
				switch (type) {
				case FORMATEUR:
					modules = mr.findModuleOfFormateur(username);
					break;
				case GESTIONNAIRE:
					if (Objects.nonNull(formateurId))//Et formateur
						modules = mr.findByFormateurId(formateurId);
					else
						modules = mr.findAll();
				}
			}
		}
		return modules;
	}
}
