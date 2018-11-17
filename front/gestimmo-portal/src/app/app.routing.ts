import { Route, RouterModule } from '@angular/router';
import { AuthenticationComponent } from './authentication/authentication.component';
import { ReservationComponent } from './reservation/reservation.component';
import { ApartmentComponent } from './apartment/apartment.component';
import {  BienComponent } from './bien/bien.component';

const APP_ROUTE: Route[] = [
       {path: '', component: AuthenticationComponent }, 
       { path:'accueil', component: AuthenticationComponent }
   ]

export const AppRouting = RouterModule.forRoot(APP_ROUTE);