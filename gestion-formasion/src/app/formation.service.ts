import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Constant } from './constants';
import { Formation } from './models/formation';

const API = Constant.API_data+'/formation'

@Injectable({
  providedIn: 'root'
})
export class FormationService {

  constructor(private http:HttpClient) { }

  getAll():Observable<Formation[]> {
    return this.http.get<Formation[]>(API);
  }

  create(libelle:string):Observable<any> {
    return this.http.post(API,{libelle});
  }

  delete(formation:Formation):Observable<any> {
    return this.http.delete(API+'/'+formation.libelle)
  }

  getAllSimple():Observable<Formation[]> {
    return this.http.get<Formation[]>(Constant.API_public+'/formations');
  }
}
