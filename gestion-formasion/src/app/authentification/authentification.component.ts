import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Constant } from '../constants';
import { User } from '../models/user';
import { UserService } from '../user.service';

@Component({
  selector: 'app-authentification',
  templateUrl: './authentification.component.html',
  styleUrls: ['./authentification.component.css']
})
export class AuthentificationComponent implements OnInit {
  user = new User("","",[]);
  errorMessage:string = null;

  constructor(private userService: UserService, private router: Router, private activatedRoute: ActivatedRoute) {
    this.userService = userService;
    this.router = router;
    this.activatedRoute = activatedRoute;

  }

  ngOnInit(): void {
  }

  onSubmit(form: NgForm) {
    this.errorMessage=null;
    this.userService.authenticate(this.user).subscribe({
      next: (u) => this.user=u,
      error: (err) => {
        this.errorMessage=err.error.message
      },
      complete: () => {
        this.userService.setConnectedUser(this.user);
        switch (this.user.personne.type) {
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
    });
  }

}
