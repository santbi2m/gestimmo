import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Appartement } from '../shared/Models/apartment.model';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json', 'Access-Control-Allow-Origin': 'http://localhost:8081' })
};
@Injectable({
  providedIn: 'root'
})
export class ApartmentService {


  constructor(private http: HttpClient) {
    httpOptions.headers.append("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
    httpOptions.headers.append('Access-Control-Allow-Methods', 'GET, POST, PUT, DELETE, PATCH');
  }
    
  private appartementUrl = 'http://localhost:8081/gestimmo/appartements';

  public getAppartement() {
    return this.http.get<Appartement[]>(this.appartementUrl, httpOptions);
  }

  public getAppartementByReservation(id: number) {
    return this.http.get<Appartement[]>(this.appartementUrl+"/reservation/"+id, httpOptions);
  }
}
