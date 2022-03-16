import { Filiere } from "./filiere";
import { Personne } from "./personne";

export class Module {
    id: number;
    libelle: string;
    dateDebut: Date;
    dateFin: Date;
    formateur: Personne;
    filiere: Filiere;

    constructor(id: number, libelle: string, dateDebut: Date, dateFin: Date, formateur: Personne, filiere: Filiere) {
        this.id = id;
        this.libelle = libelle;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.formateur = formateur;
        this.filiere = filiere;
    }
}