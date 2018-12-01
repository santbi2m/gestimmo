import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormArray } from '@angular/forms';
import { User } from '../shared/Models/user.model';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { AuthenticationService } from './authentication.service';

@Component({
  selector: 'app-authentication',
  templateUrl: './authentication.component.html',
  styleUrls: ['./authentication.component.css']
})
export class AuthenticationComponent implements OnInit {
  public user: User;
  public loginForm: FormGroup;

  constructor(private fb: FormBuilder, private app: AuthenticationService, private http: HttpClient, private router: Router) { }

  ngOnInit() {
    this.initForm();
  }

  initForm(user = {login:'', passwd:''}){
    this.loginForm = this.fb.group({
      login: [user.login, Validators.required],
      passwd: [user.passwd, Validators.required],
  })
}


 
  login() {
    this.app.authenticate(this.user, () => {
        this.router.navigateByUrl('/');
        console.log(this.user)
    });
    return false;
  }



}
