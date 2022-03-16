import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Constant } from '../constants';
import { User } from '../models/user';
import { UserService } from '../user.service';

interface MenuItem {
  href: string;
  label: string;
  active:boolean;
}

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {

  connectedUser: User = null;
  items: MenuItem[] = [];

  constructor(private router: Router, private userService: UserService) {
    
  }

  ngOnInit(): void {
    this.userService.getConnectedUser().subscribe(res => {
      this.connectedUser = res;
      if (this.connectedUser) this.buildItems();
    });

  }

  buildItems() {
    let type = this.connectedUser.personne.type;
    let possibleItems = [
      { href: '/modules', label: 'Modules' ,active:false},
      { href: '/filieres', label: 'Filières',active:false },
      { href: '/gestion', label: 'Gestion des formations',active:false }
    ];
    let itemList = [0, 0, 0];
    switch (type) {
      case Constant.TYPE_gestionnaire: itemList[2] = 1;
      case Constant.TYPE_formateur: itemList[1] = 1;
      case Constant.TYPE_stagiaire: itemList[0] = 1;
    }
    for (let index in possibleItems) {
      if (itemList[index] == 1) this.items.push(possibleItems[index]);
    }
    this.items.push({ href: '/compte', label: 'Mon compte',active:false });
    
  }

  logout() {
    this.userService.logout();
    this.router.navigate(['/']).then(() => {
      window.location.reload();
    });
  }

  isItemActive(item:MenuItem) {
    return this.router.url.match(item.href);
  }

}
