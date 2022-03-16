package fr.formation.formasion.model;

public enum TypePersonne {
	GESTIONNAIRE("Gestionnaire des formations"),STAGIAIRE("Stagiaire"), FORMATEUR("Formateur"),NOUVEL_INSCRIT("Stagiaire non affecté");

	private String libelle;

	TypePersonne(String libelle) {
		this.libelle = libelle;
	}

	public String toString() {
		return this.libelle;
	}

	public static TypePersonne fromString(String libelle) {
		for (TypePersonne tp : values()) {
			if (tp.libelle.equals(libelle))
				return tp;
		}
		return null;
	}

}
