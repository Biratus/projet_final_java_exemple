import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Constant } from './constants';
import { UserService } from './user.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'Forma\' Sion';

  connected = false;
  redirect = false;

  constructor(private router: Router, private userService: UserService) {
    this.userService.connexionEmmited.subscribe({
      next: () => this.connected = true,
      error: (err) => console.log(err)
    });
  }

  ngOnInit(): void {
    this.userService.getConnectedUser().subscribe(res => {
      let userConnected = res;
      if (userConnected) {
        this.connected = true;
        if (this.redirect)
          switch (userConnected.personne.type) {
            case Constant.TYPE_nouvelInscrit:
            case Constant.TYPE_stagiaire:
              this.router.navigate(['/modules']);
              break;
            case Constant.TYPE_formateur:
              this.router.navigate(['/filieres']);
              break;
            case Constant.TYPE_gestionnaire:
              this.router.navigate(['/gestion']);
              break;
          }
      }
      else {
        this.router.navigate(['/authentification']);
      }
    });

  }

}
