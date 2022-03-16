import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Constant } from './constants';
import { Personne } from './models/personne';

const API = Constant.API_data+"/personne";

@Injectable({
  providedIn: 'root'
})
export class PersonneService {

  constructor(private http:HttpClient) { }

  findAllFormateur():Observable<Personne[]> {
    return this.http.get<Personne[]>(API+"?type="+Constant.TYPE_formateur);
  }

  findAllStagiaireWithoutFiliere():Observable<Personne[]> {
    return this.http.get<Personne[]>(API+"/nouveau");
  }

  findStagiaireSouhaitantFormation(libelle:string):Observable<Personne[]> {
    return this.http.get<Personne[]>(API+'/nouveau?formation='+libelle);
  }

  register(personne:Personne):Observable<any> {
    return this.http.post(Constant.API_public+'/inscription',personne);
  }
}

