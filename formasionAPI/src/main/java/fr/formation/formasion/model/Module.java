package fr.formation.formasion.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Module {

	@Id
	private String id;

	private String libelle;

	private LocalDate dateDebut;

	private LocalDate dateFin;

	@ManyToOne
	@JoinColumn(name = "personne_id")
	@JsonIgnoreProperties("modules")
	private Personne formateur;

	@ManyToOne
	@JoinColumn(name = "filiere_id")
	@JsonIgnoreProperties("modules")
	private Filiere filiere;

}
