import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Constant } from './constants';
import { Filiere } from './models/filiere';
import { Personne } from './models/personne';

const API = Constant.API_data+'/filiere';

@Injectable({
  providedIn: 'root'
})
export class FiliereService {

  constructor(private http:HttpClient) { }

  findAll():Observable<Filiere[]> {
    return this.http.get<Filiere[]>(API);
  }

  delete(f:Filiere) {
    return this.http.delete(API+'/'+f.id);
  }

  search(libelle:string,formateur:string,active:boolean) {
    let params=[];
    if(libelle) params.push("libelle="+libelle);
    if(formateur) params.push("formateurId="+formateur);
    if(active!=null) params.push("active="+active);
    let paramStr = params.length>0?'?'+params.join('&'):'';
    return this.http.get<Filiere[]>(API+'/search'+paramStr);
  }

  getFilieresNotStartedForFormation(formations:string[]):Observable<any> {
    return this.http.post<any>(API+'/formations',formations);
  }

  bindFilieresAndStagiaire(stagiaires:Personne[]):Observable<any> {
    return this.http.post(API+"/inscription",stagiaires);
  }

  create(filiere:Filiere):Observable<any> {
    return this.http.post(API,filiere);
  }
}
