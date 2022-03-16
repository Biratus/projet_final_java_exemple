package fr.formation.formasion.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import fr.formation.formasion.controller.exception.BindNouveauToFiliereException;
import fr.formation.formasion.model.Filiere;
import fr.formation.formasion.model.Formation;
import fr.formation.formasion.model.Personne;
import fr.formation.formasion.model.TypePersonne;
import fr.formation.formasion.model.User;
import fr.formation.formasion.repository.FiliereRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FiliereService {

	@Autowired
	private FiliereRepository fr;

	@Autowired
	private PersonneService ps;

	public void create(Filiere f) {
		if (Objects.nonNull(f.getStagiaires()) && !ps.checkAllNouvelInscrit(f.getStagiaires()))
			throw new BindNouveauToFiliereException();
		Filiere persistedFiliere = this.fr.save(f);
		if (Objects.nonNull(f.getStagiaires())) {
			for (Personne p : f.getStagiaires()) {
				ps.bindStagiaireToFiliere(p, persistedFiliere);
			}
		}

	}

	public List<Filiere> findAll() {
		return this.fr.findAll();
	}

	public Optional<Filiere> getById(Integer id) {
		return this.fr.findById(id);
	}

	public void update(Filiere f) {
		this.fr.save(f);
	}

	public Optional<Boolean> delete(Filiere f) {
		try {
			this.fr.delete(f);
			return Optional.of(Boolean.TRUE);
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	public Optional<Boolean> delete(Integer id) {
		try {
			this.fr.deleteById(id);
			return Optional.of(Boolean.TRUE);
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	public void setStagiairesOfFiliere(List<Personne> pList, Integer filiereId) {
		Filiere f = new Filiere();
		f.setId(filiereId);
		if (!ps.checkAllNouvelInscrit(pList))
			throw new BindNouveauToFiliereException();
		ps.bindStagiairesToFiliere(pList, f);
	}
	
	public void setStagiaireOfFiliere(Personne p, Filiere f) {
		ps.bindStagiaireToFiliere(p, f);
		ps.setTypeOf(p,TypePersonne.STAGIAIRE);
	}

	public List<Filiere> getFiliereOfPersonne(TypePersonne type, String username) {
		switch (type) {
		case FORMATEUR:
			return fr.findFiliereOfFormateur(username);
		case GESTIONNAIRE:
			return fr.findAll();
		}
		return null;
	}

	public List<Filiere> searchFilieres(String libelle, Integer formateurId, Boolean active, User user) {
		List<Filiere> filieres = null;
		TypePersonne type = user.getPersonne().getType();
		String username = user.getUsername();
		log.debug("type: "+type);
		if (Objects.isNull(active)) {
			switch (type) {
			case FORMATEUR:
				filieres = fr.findFiliereOfFormateur(username);
				break;
			case GESTIONNAIRE:
				if (Objects.nonNull(formateurId))
					filieres = fr.findFiliereByFormateurId(formateurId);
				else
					filieres = fr.findAll();
			}
		} else if (active) {// active
			switch (type) {
			case FORMATEUR:
				filieres = fr.findActiveFiliereOfFormateur(username);
				break;
			case GESTIONNAIRE:
				if (Objects.nonNull(formateurId))
					filieres = fr.findActiveFiliereByFormateurId(formateurId);
				else
					filieres = fr.findActiveFiliere();
			}
		} else {// inactive
			switch (type) {
			case FORMATEUR:
				filieres = fr.findInactiveFiliereOfFormateur(username);
				break;
			case GESTIONNAIRE:
				if (Objects.nonNull(formateurId))
					filieres = fr.findInactiveFiliereByFormateurId(formateurId);
				else
					filieres = fr.findInactiveFiliere();
			}
		}

		// Tri par libelle car libelle construit au runtime
		if (Objects.nonNull(libelle))
			filieres = filieres.stream().filter(f -> f.getLibelle().contains(libelle)).collect(Collectors.toList());

		return filieres;

	}

	public List<Filiere> getFilieresOfFormation(String libelleFormation) {
		return fr.findFiliereByFormation(new Formation(libelleFormation));
	}

	public List<Filiere> getFilieresNotStartedOfFormation(String libelleFormation) {
		return fr.findFilieresNotStartedOfFormation(libelleFormation);
	}
}
