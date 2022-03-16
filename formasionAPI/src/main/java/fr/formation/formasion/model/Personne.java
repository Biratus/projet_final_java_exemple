package fr.formation.formasion.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@SequenceGenerator(name = "personne_gen", sequenceName = "personne_seq", initialValue = 100, allocationSize = 1)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Personne {

	@Id
	@GeneratedValue(generator = "personne_gen")
	private Integer id;

	private String nom;

	private String prenom;
	
	private String mail;
	
	private String phone;
	
	private LocalDate dateNaissance;
	
	private String ville;
	
	private LocalDate dateInscription;

	@Enumerated(EnumType.STRING)
	private TypePersonne type;

	@ManyToOne
	@JoinColumn(name = "filiere_id")
	@JsonIgnoreProperties("stagiaires")
	private Filiere filiere;

	@OneToMany(mappedBy = "formateur")
	@JsonIgnoreProperties("formateur")
	private List<Module> modules;
	
	@OneToOne(mappedBy="personne")
	@JsonIgnoreProperties("personne")
	private User user;
	
	@ManyToOne
	@JoinColumn(name="formation_libelle")
	@JsonIgnoreProperties({"stagiaires","filieres"})
	private Formation formationSouhaite;

	@Override
	public String toString() {
		return "Personne [id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", type=" + type + "]";
	}

	public String getIdentifiant() {
		return this.prenom.toLowerCase().charAt(0)+this.nom.toLowerCase();
	}
}
