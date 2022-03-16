import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from './models/user';
import { map, Observable, of, Subject, tap, throwError } from 'rxjs';
import { Constant } from './constants';
import { PersonneDTO } from './gestion-compte/gestion-compte.component';

const API = Constant.API_data + '/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  // Observable string sources
  private userConnectedSubject = new Subject<User>();
  // Observable string streams
  connexionEmmited = this.userConnectedSubject.asObservable();

  private userConnected: User;

  constructor(private http: HttpClient) {
    this.http = http;
  }

  authenticate(user: User): Observable<any> {
    return this.http.post<any>(Constant.API_simple + "/authenticate", { username: user.username, password: user.password });
  }

  setConnectedUser(user: User) {
    let authdata = window.btoa(user.username + ':' + user.password);
    sessionStorage.setItem(Constant.AUTH_storage_key, authdata);
    this.emitUserConnected(user);
    this.userConnected = user;
  }

  getConnectedUserAuthData(): string | null {
    return sessionStorage.getItem(Constant.AUTH_storage_key);
  }

  getConnectedUser(): Observable<User> {
    if (!this.userConnected && this.getConnectedUserAuthData()) {
      let [username, password] = window.atob(this.getConnectedUserAuthData()).split(':');
      return this.authenticate(new User(username, password, null)).pipe(tap(u => this.setConnectedUser(u)));
    }
    else return new Observable((observer) => {observer.next(this.userConnected);observer.complete()});
  }

  logout() {
    sessionStorage.removeItem(Constant.AUTH_storage_key);
  }

  changePassword(dto: PersonneDTO): Observable<any> {
    return this.http.post(API + '/changePassword', dto);
  }
  // Service message commands
  emitUserConnected(user: User) {
    this.userConnectedSubject.next(user);
  }
}
