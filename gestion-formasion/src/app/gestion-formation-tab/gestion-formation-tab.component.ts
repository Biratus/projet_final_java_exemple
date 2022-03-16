import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormationService } from '../formation.service';
import { Formation } from '../models/formation';
import { UserService } from '../user.service';

const successMsg = 'Formation créée!';

@Component({
  selector: 'app-gestion-formation-tab',
  templateUrl: './gestion-formation-tab.component.html',
  styleUrls: ['./gestion-formation-tab.component.css']
})
export class GestionFormationTabComponent implements OnInit {

  formations: Formation[];

  libelleToCreate: string;

  successAlertMsg: string;
  errorAlertMsg: string;

  constructor(private router: Router, private formationService: FormationService) {
  }
  ngOnInit(): void {
    this.loadFormations();
  }

  loadFormations() {
    this.formationService.getAll().subscribe(res => this.formations = res);
  }

  createFormation() {
    this.successAlertMsg = null;
    this.errorAlertMsg = null;
    this.formationService.create(this.libelleToCreate).subscribe({
      next: () => {
        this.successAlertMsg = successMsg;
        this.loadFormations();
      },
      error: (err) => {
        this.errorAlertMsg = err.error.message;
      }
    });
  }

  delete(formation: Formation) {
    this.formationService.delete(formation).subscribe(res => {
      this.loadFormations();
    });
  }

}
