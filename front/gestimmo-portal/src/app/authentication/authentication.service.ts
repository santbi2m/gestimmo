import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json', 'Access-Control-Allow-Origin': 'http://localhost:8081' })
  };
@Injectable()
export class AuthenticationService {

  authenticated = false;
  private loginUrl = 'http://localhost:8081/gestimmo/login';

  constructor(private http: HttpClient) {
    httpOptions.headers.append("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
    httpOptions.headers.append('Access-Control-Allow-Methods', 'GET, POST, PUT, DELETE, PATCH');
  }

  

  authenticate(credentials, callback) {

        const headers = new HttpHeaders(credentials ? {
            authorization : 'Basic ' + btoa(credentials.username + ':' + credentials.password)
        } : {});

        this.http.post(this.loginUrl, credentials, httpOptions).subscribe(response => {
            if (response['name']) {
                this.authenticated = true;
            } else {
                this.authenticated = false;
            }
            return callback && callback();
        });

    }

}