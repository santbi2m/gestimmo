import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient  } from '@angular/common/http';
import { Reservation } from '../shared/Models/reservation.model';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json', 'Access-Control-Allow-Origin': '*' })
};
@Injectable({
  providedIn: 'root'
})
export class ReservationService {

 
    constructor(private http: HttpClient) {}
  
    private reservationUrl = 'http://localhost:8081/gestimmo/reservations';
    //private userUrl = '/api';
  
    public getReservations() {
      httpOptions.headers.append("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
      httpOptions.headers.append('Access-Control-Allow-Methods', 'GET, POST, PUT, DELETE, PATCH');
      return this.http.get<Reservation[]>(this.reservationUrl, httpOptions);
    }
  
   /* public deleteUser(user) {
      return this.http.delete(this.userUrl + "/"+ user.id);
    }
  
    public createUser(user) {
      return this.http.post<User>(this.userUrl, user);
    }*/
}
