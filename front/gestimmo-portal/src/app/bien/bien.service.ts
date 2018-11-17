import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Bien } from "../shared/Models/bien.model";

const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json', 'Access-Control-Allow-Origin': 'http://localhost:8081' })
  };
@Injectable({
    providedIn: 'root'
  })
  export class BienService {
  
   
      constructor(private http: HttpClient) {}
    
      private bienUrl = 'http://localhost:8081/gestimmo/biens';
    
      public getBiens() {
        return this.http.get<Bien[]>(this.bienUrl, httpOptions);
      }
    
     /* public deleteUser(user) {
        return this.http.delete(this.userUrl + "/"+ user.id);
      }
    
      public createUser(user) {
        return this.http.post<User>(this.userUrl, user);
      }*/
  }