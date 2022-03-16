import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { InscriptionComponent } from './inscription/inscription.component';
import { MenuComponent } from './menu/menu.component';
import { ModuleComponent } from './module/module.component';
import { FiliereComponent } from './filiere/filiere.component';
import { GestionCompteComponent } from './gestion-compte/gestion-compte.component';
import { GestionFormationComponent } from './gestion-formation/gestion-formation.component';
import { GestionFormationTabComponent } from './gestion-formation-tab/gestion-formation-tab.component';
import { GestionFiliereTabComponent } from './gestion-filiere-tab/gestion-filiere-tab.component';
import { GestionModuleTabComponent } from './gestion-module-tab/gestion-module-tab.component';
import { AuthentificationComponent } from './authentification/authentification.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';
import { UserService } from './user.service';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { BasicAuthInterceptorInterceptor } from './basic-auth-interceptor.interceptor';
import { ModuleService } from './module.service';
import { FiliereService } from './filiere.service';
import { PersonneService } from './personne.service';

@NgModule({
  declarations: [
    AppComponent,
    InscriptionComponent,
    MenuComponent,
    ModuleComponent,
    FiliereComponent,
    GestionCompteComponent,
    GestionFormationComponent,
    GestionFormationTabComponent,
    GestionFiliereTabComponent,
    GestionModuleTabComponent,
    AuthentificationComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    FormsModule,
    HttpClientModule,
    NgbModule
  ],
  providers: [UserService, ModuleService, FiliereService, PersonneService,
    { provide: HTTP_INTERCEPTORS, useClass: BasicAuthInterceptorInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
