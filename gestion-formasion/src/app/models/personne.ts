import { Filiere } from "./filiere";
import { Formation } from "./formation";
import { Module } from "./module";
import { User } from "./user";

export class Personne {
    id: number;
    username?: string;
    nom: string;
    prenom: string;
    mail: string;
    phone: string;
    dateNaissance: Date;
    ville: string;
    dateInscription: Date;
    type: string;
    filiere: Filiere;
    formationSouhaite: Formation;
    modules: Module[];
    user: User;

    constructor(id: number, nom: string, prenom: string, mail: string, phone: string,
        dateNaissance: Date, ville: string, dateInscription: Date, type: string, filiere: Filiere, formation: Formation,
        modules: Module[], user: User) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.phone = phone;
        this.dateNaissance = dateNaissance;
        this.ville = ville;
        this.dateInscription = dateInscription;
        this.type = type;
        this.filiere = filiere;
        this.formationSouhaite = formation;
        this.modules = modules;
        this.user = user;
    }
}