import { StringMapWithRename } from '@angular/compiler/src/compiler_facade_interface';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { Constant } from '../constants';
import { FiliereService } from '../filiere.service';
import { Filiere } from '../models/filiere';
import { Personne } from '../models/personne';
import { PersonneService } from '../personne.service';
import { UserService } from '../user.service';

@Component({
  selector: 'app-filiere',
  templateUrl: './filiere.component.html',
  styleUrls: ['./filiere.component.css']
})
export class FiliereComponent implements OnInit {

  typeUser: string;
  Constant = Constant;
  error: string;

  filieres: Filiere[];
  formateurs: Personne[];

  filiereStatus = "all";
  libelleSearch: string;
  formateurSearch: string;

  searchForm: FormGroup;

  constructor(private router: Router, private userService: UserService,
    private filiereService: FiliereService, private personneService: PersonneService) {
  }

  ngOnInit(): void {
    this.userService.getConnectedUser().subscribe(res => {
      let userConnected = res;
    if (userConnected &&
      (userConnected.personne.type == Constant.TYPE_formateur
        || userConnected.personne.type == Constant.TYPE_gestionnaire)) {
      this.typeUser = userConnected.personne.type;
    } else {
      this.router.navigate(['/']);
    }
    this.personneService.findAllFormateur().subscribe(res => this.formateurs = res);
    this.filiereService.findAll().subscribe(res => this.filieres = res);
  });
  }

  search() {
    let active;
    switch (this.filiereStatus) {
      case 'all': active = null; break;
      case 'active': active = true; break;
      case 'inactive': active = false; break;
    }
    this.filiereService.search(this.libelleSearch, this.formateurSearch, active)
      .subscribe(res => this.filieres = res);
  }

  closeAlert() {
    this.error = null;
  }

  deleteFiliere(filiere: Filiere) {
    this.filiereService.delete(filiere).subscribe({
      next: () => { },
      error: () => {
        this.error = "Impossible de supprimé cette filière. Contactez un administrateur."
      },
      complete: () => this.search()
    });
  }
}
