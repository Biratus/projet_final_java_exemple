package fr.formation.formasion.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@SequenceGenerator(name = "filiere_gen", sequenceName = "filiere_seq", initialValue = 100, allocationSize = 1)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Filiere {

	@Id
	@GeneratedValue(generator = "filiere_gen")
	private Integer id;

	@OneToMany(mappedBy = "filiere")
	@JsonIgnoreProperties("filiere")
	private List<Module> modules;

	@OneToMany(mappedBy = "filiere")
	@JsonIgnoreProperties("filiere")
	private List<Personne> stagiaires;

	@ManyToOne
	@JoinColumn(name = "formation_libelle")
	@JsonIgnoreProperties({"filieres","stagiaires"})
	private Formation formation;
	
	public Filiere(Integer id) {
		this.id=id;
	}

	public String getLibelle() {
		return this.id+"_"+this.formation.getLibelle();
	}
	
	public LocalDate getDateDebut() {
		LocalDate min = null;
		for(Module m : modules) {
			if(Objects.isNull(min) || min.isAfter(m.getDateDebut())) min=m.getDateDebut();
		}
		return min;
	}
	
	public LocalDate getDateFin() {
		LocalDate max = null;
		for(Module m : modules) {
			if(Objects.isNull(max) || max.isBefore(m.getDateFin())) max=m.getDateFin();
		}
		return max;
	}

}
