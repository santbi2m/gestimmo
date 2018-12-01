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
  
   
      constructor(private http: HttpClient) {
        httpOptions.headers.append("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
         httpOptions.headers.append('Access-Control-Allow-Methods', 'GET, POST, PUT, DELETE, PATCH');
      }
    
      private bienUrl = 'http://localhost:8081/gestimmo/biens';
    
      public getBiens() {
        return this.http.get<Bien[]>(this.bienUrl, httpOptions);
      }
    
      public getBienById(id: number) {
        return this.http.get<Bien>(this.bienUrl + "/"+ id, httpOptions);
      }
    
      public createBien(bien: Bien) {
        return this.http.post<Bien>(this.bienUrl+'/create', bien, httpOptions);
      }
  }