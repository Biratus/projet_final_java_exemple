import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Constant } from '../constants';
import { FiliereService } from '../filiere.service';
import { Filiere } from '../models/filiere';
import { Module } from '../models/module';
import { Personne } from '../models/personne';
import { User } from '../models/user';
import { ModuleService } from '../module.service';
import { PersonneService } from '../personne.service';
import { UserService } from '../user.service';

@Component({
  selector: 'app-module',
  templateUrl: './module.component.html',
  styleUrls: ['./module.component.css']
})
export class ModuleComponent implements OnInit {

  user: User;
  modules: Module[];
  filieres: Filiere[];
  formateurs: Personne[];
  Constant = Constant;
  error: string;
  libelleSearch: string;
  filiereSearch: string;
  formateurSearch: string;

  constructor(private route: ActivatedRoute, private router: Router, private userService: UserService, private moduleService: ModuleService,
    private filiereService: FiliereService, private personneService: PersonneService) {
  }
  ngOnInit(): void {
    this.loadModules();
    this.filiereService.findAll().subscribe(res => this.filieres = res);
    this.personneService.findAllFormateur().subscribe(res => this.formateurs = res);
    this.userService.getConnectedUser().subscribe(res => {
      this.user = res;
      if (!this.user) this.router.navigate(['/']);
    });

  }

  loadModules() {
    this.route.queryParams.subscribe((res: any) => {
      if (res.filiere_id) {
        this.filiereSearch = res.filiere_id;
        this.search();
      } else
        this.moduleService.findAll().subscribe(res => this.modules = res);
    });
  }

  search() {
    this.moduleService.search(this.libelleSearch, this.filiereSearch, this.formateurSearch).subscribe(res => this.modules = res);
  }

  deleteModule(module: Module) {
    this.moduleService.delete(module).subscribe({
      next: () => { },
      error: () => {
        this.error = "Impossible de supprimé ce module. Contactez un administrateur."
      },
      complete: () => this.search()
    });
  }

  closeAlert() {
    this.error = null;
  }

}
