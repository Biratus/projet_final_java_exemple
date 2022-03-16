import { Formation } from "./formation";
import { Module } from "./module";
import { Personne } from "./personne";

export class Filiere {
    id: number;
    modules: Module[];
    stagiaires: Personne[];
    formation: Formation;
    libelle:string;
    dateDebut:Date;
    dateFin:Date;

    constructor(id: number, modules: Module[], stagiaires: Personne[], formation: Formation,dateDebut:Date,dateFin:Date) {
        this.id = id;
        this.modules = modules;
        this.stagiaires = stagiaires;
        this.formation = formation;
        this.dateDebut=dateDebut;
        this.dateFin=dateFin;
        this.libelle = this.id+"_"+this.formation.libelle;
    }
}