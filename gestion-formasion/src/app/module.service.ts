import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Constant } from './constants';
import { Module } from './models/module';
const API=Constant.API_data+"/module";

@Injectable({
  providedIn: 'root'
})
export class ModuleService {

  constructor(private http:HttpClient) { }

  findAll():Observable<Module[]> {
    return this.http.get<Module[]>(API);
  }

  delete(m:Module) {
    return this.http.delete(API+'/'+m.id);
  }

  search(libelle:string,filiere:string,formateur:string) {
    let params=[];
    if(libelle) params.push("libelle="+libelle);
    if(filiere) params.push("filiereId="+filiere);
    if(formateur) params.push("formateurId="+formateur);

    let paramStr = params.length>0?'?'+params.join('&'):'';
    return this.http.get<Module[]>(API+'/search'+paramStr);
  }

  create(module:Module):Observable<any> {
    return this.http.post(API,module);
  }
}
