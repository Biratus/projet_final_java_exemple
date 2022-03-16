import { ThisReceiver } from '@angular/compiler';
import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Constant } from '../constants';
import { FiliereService } from '../filiere.service';
import { FormationService } from '../formation.service';
import { Filiere } from '../models/filiere';
import { Personne } from '../models/personne';
import { PersonneService } from '../personne.service';
import { UserService } from '../user.service';

@Component({
  selector: 'app-gestion-formation',
  templateUrl: './gestion-formation.component.html',
  styleUrls: ['./gestion-formation.component.css']
})
export class GestionFormationComponent implements OnInit {

  @Output() addedEvent = new EventEmitter();

  active = 1;

  stagiaires: Personne[];

  stagiaireFiliereMap: any;
  stagiaireFiliereToAdd: number[] = [];

  constructor(private route: ActivatedRoute, private router: Router, private userService: UserService, private personneService: PersonneService,
    private filiereService: FiliereService) {
  }
  ngOnInit(): void {
    this.userService.getConnectedUser().subscribe(res => {
      let u = res;
      if (!u || u.personne.type != Constant.TYPE_gestionnaire) {
        this.router.navigate(['/']);
      }
      else {
        this.loadStagiaires();
        this.route.queryParams.subscribe((res:any) => {
          if(res.tab) {
            switch(res.tab) {
              case 'formation':this.active=1;break;
              case 'filiere':this.active=2;break;
              case 'module':this.active=3;break;
              case 'stagiaires':this.active=4;break;
            }
          }
        })
      }
    });
  }
  loadStagiaires() {
    this.personneService.findAllStagiaireWithoutFiliere().subscribe(res => {
      this.stagiaires = res;
      let formationsWantedDistinct = this.stagiaires.map(s => s.formationSouhaite.libelle).filter((elt, i, arr) => arr.indexOf(elt) === i);
      this.filiereService.getFilieresNotStartedForFormation(formationsWantedDistinct).subscribe(map => {
        this.stagiaireFiliereMap = map;
      });
    });
  }
  addStagiaireToFilieres() {
    let stagiairesToAdd = [];
    for (let s of this.stagiaires) {
      if (this.stagiaireFiliereToAdd[s.id]) {
        s.filiere = new Filiere(this.stagiaireFiliereToAdd[s.id], null, null, s.formationSouhaite, null, null);
        stagiairesToAdd.push(s);
      }
    }
    this.filiereService.bindFilieresAndStagiaire(stagiairesToAdd).subscribe(res => {
      this.loadStagiaires();
      this.addedEvent.emit(null);
    });
  }

  filieresFor(libelleFormation: string): Filiere[] {
    return this.stagiaireFiliereMap[libelleFormation];
  }


}