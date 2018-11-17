import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient  } from '@angular/common/http';
import { Reservation } from '../shared/Models/reservation.model';
import { Client } from '../shared/Models/client.model';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json', 'Access-Control-Allow-Origin': 'http://localhost:8081' })
};
@Injectable({
  providedIn: 'root'
})
export class ClientService {

 
    constructor(private http: HttpClient) {}
  
    private clientUrl = 'http://localhost:8081/gestimmo/clients';
    //private userUrl = '/api';
  
    public getClients() {
      httpOptions.headers.append("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
      httpOptions.headers.append('Access-Control-Allow-Methods', 'GET, POST, PUT, DELETE, PATCH');
      return this.http.get<Reservation[]>(this.clientUrl, httpOptions);
    }

    public getClient(id: number) {
        httpOptions.headers.append("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        httpOptions.headers.append('Access-Control-Allow-Methods', 'GET, POST, PUT, DELETE, PATCH');
        return this.http.get<Client>(this.clientUrl + "/"+ id, httpOptions);
      }
    
  
   /* public deleteUser(user) {
      return this.http.delete(this.userUrl + "/"+ user.id);
    }
  
    public createUser(user) {
      return this.http.post<User>(this.userUrl, user);
    }*/
}
