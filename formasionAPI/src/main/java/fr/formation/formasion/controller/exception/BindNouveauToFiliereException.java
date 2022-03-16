package fr.formation.formasion.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Seul les nouveaux stagiaires peuvent être affecté à une filière")
public class BindNouveauToFiliereException extends RuntimeException {
	private static final long serialVersionUID = 1L;
}
