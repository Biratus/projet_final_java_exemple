import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthentificationComponent } from './authentification/authentification.component';
import { FiliereComponent } from './filiere/filiere.component';
import { GestionCompteComponent } from './gestion-compte/gestion-compte.component';
import { GestionFormationComponent } from './gestion-formation/gestion-formation.component';
import { InscriptionComponent } from './inscription/inscription.component';
import { ModuleComponent } from './module/module.component';

const routes: Routes = [
  { path: 'authentification', component: AuthentificationComponent },
  { path: 'modules', component: ModuleComponent },
  { path: 'filieres', component: FiliereComponent },
  { path: 'gestion', component: GestionFormationComponent },
  { path: 'compte', component: GestionCompteComponent },
  { path: 'inscription', component: InscriptionComponent }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
