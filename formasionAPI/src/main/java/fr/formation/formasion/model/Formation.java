package fr.formation.formasion.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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
public class Formation {

	@Id
	private String libelle;

	@OneToMany(mappedBy = "formation")
	@JsonIgnoreProperties("formation")
	private List<Filiere> filieres;
	
	@OneToMany(mappedBy="formationSouhaite")
	@JsonIgnoreProperties("formationSouhaite")
	private List<Personne> stagiaires;

	public Formation(String libelle) {
		this.libelle = libelle;
	}
}
