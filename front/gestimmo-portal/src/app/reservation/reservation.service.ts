import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient, HttpParams  } from '@angular/common/http';
import { Reservation } from '../shared/Models/reservation.model';
import { ReservationCriteria } from '../shared/Models/reservationCriteria.model';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json', 'Access-Control-Allow-Origin': 'http://localhost:8081' })
};

@Injectable({
  providedIn: 'root'
})
export class ReservationService {

 
 
    constructor(private http: HttpClient) {
      httpOptions.headers.append("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
      httpOptions.headers.append('Access-Control-Allow-Methods', 'GET, POST, PUT, DELETE, PATCH');
    }
  
    private reservationUrl = 'http://localhost:8081/gestimmo/reservations';
    //private userUrl = '/api';
  
    public getReservations() {
      return this.http.get<Reservation[]>(this.reservationUrl, httpOptions);
    }
  
    public getReservationsCriteria(param: any) {
     
      const params = new HttpParams({ fromObject: param })
      return this.http.get<Reservation[]>(this.reservationUrl+ "/recherche",  {headers: httpOptions.headers,  params: params});
    }
  
    public getReservationbyId(id: number) {
      return this.http.get<Reservation>(this.reservationUrl+"/"+id, httpOptions);
    }
    
   public annulerReservation(reservation: Reservation) {
    console.log(reservation);
      return this.http.put(this.reservationUrl+"/annuler/", reservation, httpOptions);
    }

    public creationReservation(reservation: Reservation) {
      console.log(reservation);
        return this.http.post(this.reservationUrl+"/creation/", reservation, httpOptions);
      }
}
