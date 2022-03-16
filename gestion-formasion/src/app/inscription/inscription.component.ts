import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { FormationService } from '../formation.service';
import { Formation } from '../models/formation';
import { Personne } from '../models/personne';
import { User } from '../models/user';
import { PersonneService } from '../personne.service';

@Component({
  selector: 'app-inscription',
  templateUrl: './inscription.component.html',
  styleUrls: ['./inscription.component.css']
})
export class InscriptionComponent implements OnInit {

  personne=new Personne(null,null,null,null,null,null,null,null,null,null,null,null,null);
  formations:Formation[];

  errorAlertMsg: string;

  inscriptionSuccess=false;
  newPersonne:User;
  // newPersonne=new User('username','password',null);

  constructor(private personneService:PersonneService,private formationService:FormationService) { }

  ngOnInit(): void {
    this.formationService.getAllSimple().subscribe(res => this.formations=res);
  }

  inscription(form:NgForm) {
    this.errorAlertMsg = null;

    if(!form.form.valid) {
      console.log(form);
      this.errorAlertMsg = "Des informations sont manquantes";
      return;
    }
    
    this.personneService.register(this.personne).subscribe({
      next:(res) => {
        this.newPersonne=res;
        this.inscriptionSuccess=true;
      },
      error:(err) => {
        this.errorAlertMsg = err.error.message;

      }
    });
  }

}
