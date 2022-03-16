import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FiliereService } from '../filiere.service';
import { FormationService } from '../formation.service';
import { Filiere } from '../models/filiere';
import { Formation } from '../models/formation';
import { Personne } from '../models/personne';
import { PersonneService } from '../personne.service';
const successMsg = "Filière créée!";
@Component({
  selector: 'app-gestion-filiere-tab',
  templateUrl: './gestion-filiere-tab.component.html',
  styleUrls: ['./gestion-filiere-tab.component.css']
})
export class GestionFiliereTabComponent implements OnInit {

  @Output() addedEvent=new EventEmitter();

  formations: Formation[];
  formation: string;

  stagiaires: Personne[];
  stagiairesSelect: any[] = [];

  successAlertMsg: string;
  errorAlertMsg: string;

  constructor(private formationService: FormationService, private personneService: PersonneService, private filiereService: FiliereService) { }

  ngOnInit(): void {
    this.formationService.getAll().subscribe(res => this.formations = res);
  }

  formationSelected() {
    this.personneService.findStagiaireSouhaitantFormation(this.formation).subscribe(res => this.stagiaires = res);
  }

  createFiliere() {
    this.successAlertMsg=null;
    this.errorAlertMsg=null;
    let stagiairesSelected = [];
    for (let s of this.stagiaires) {
      if (this.stagiairesSelect[s.id]) stagiairesSelected.push(s);
    }
    let filiere = new Filiere(null, null, stagiairesSelected, new Formation(this.formation), null, null);
    this.filiereService.create(filiere).subscribe({
      next: () => {
        this.successAlertMsg = successMsg;
        this.formation = null;
        this.stagiaires = null;
        this.stagiairesSelect = [];
        this.addedEvent.emit(null);
      },
      error: (err) => {
        this.errorAlertMsg = err.error.message;
      }
    });
  }

}
