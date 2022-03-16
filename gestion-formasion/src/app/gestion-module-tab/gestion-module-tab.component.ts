import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { FiliereService } from '../filiere.service';
import { Filiere } from '../models/filiere';
import { Module } from '../models/module';
import { Personne } from '../models/personne';
import { ModuleService } from '../module.service';
import { PersonneService } from '../personne.service';
import { UserService } from '../user.service';

const successMsg = "Le module a été crée";

@Component({
  selector: 'app-gestion-module-tab',
  templateUrl: './gestion-module-tab.component.html',
  styleUrls: ['./gestion-module-tab.component.css']
})
export class GestionModuleTabComponent implements OnInit {

  successAlertMsg: string;
  errorAlertMsg: string;

  module = new Module(null, null, null, null, null, null);

  filieres: Filiere[];
  formateurs: Personne[];

  constructor(private router: Router, private moduleService: ModuleService, private personneService: PersonneService, private filiereService: FiliereService) {
  }
  ngOnInit(): void {
    this.filiereService.findAll().subscribe(res => this.filieres = res);
    this.personneService.findAllFormateur().subscribe(res => this.formateurs = res);
  }

  createModule(moduleForm: NgForm) {
    this.successAlertMsg = null;
    this.errorAlertMsg = null;
    if (!moduleForm.form.valid) {
      this.errorAlertMsg = "Des champs sont requis";
      return;
    } else if (this.module.dateDebut > this.module.dateFin) {
      this.errorAlertMsg = "La date de début doit être avant la date de fin";
      return;
    }
    this.moduleService.create(this.module).subscribe({
      next: () => {
        this.successAlertMsg = successMsg;
        moduleForm.resetForm();
      },
      error: (err) => {
        this.errorAlertMsg = err.error.message;
      }
    });

  }

}
