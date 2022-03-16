import { ThisReceiver } from '@angular/compiler';
import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Constant } from '../constants';
import { Personne } from '../models/personne';
import { UserService } from '../user.service';

@Component({
  selector: 'app-gestion-compte',
  templateUrl: './gestion-compte.component.html',
  styleUrls: ['./gestion-compte.component.css']
})
export class GestionCompteComponent implements OnInit {

  personne: Personne;
  typePersonne: string;

  newPersonne: PersonneDTO;

  Constant = Constant;

  successAlertMsg: string;
  errorAlertMsg: string;

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.userService.getConnectedUser().subscribe(res => {
      let userConnected = res;
      this.personne = userConnected.personne;
      this.newPersonne = { username: userConnected.username };
      switch (this.personne.type) {
        case Constant.TYPE_nouvelInscrit: this.typePersonne = "nouvel inscrit"; break;
        case Constant.TYPE_stagiaire: this.typePersonne = "stagiaire"; break;
        case Constant.TYPE_formateur: this.typePersonne = "formateur"; break;
        case Constant.TYPE_gestionnaire: this.typePersonne = "gestionnaire"; break;
      }
      this.personne.user = userConnected;
      console.log(this.personne);
    });
  }

  changePassword(form: NgForm) {
    this.successAlertMsg = null;
    this.errorAlertMsg = null;
    if (!form.form.valid) {
      this.errorAlertMsg = "Des champs sont requis";
      return;
    }
    this.userService.changePassword(this.newPersonne).subscribe({
      next: () => {
        this.successAlertMsg = "Mot de passe changer avec succès!";
        form.resetForm();
      },
      error: (err) => {
        this.errorAlertMsg = err.error.message;
      }
    });
  }

}

export interface PersonneDTO {
  username: string;
  oldPassword?: string;
  newPassword?: string;
}