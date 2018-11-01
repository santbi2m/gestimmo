import { Route, RouterModule } from '@angular/router';
import { AuthenticationComponent } from './authentication/authentication.component';
import { ReservationComponent } from './reservation/reservation.component';
import { ApartmentComponent } from './apartment/apartment.component';
import { PropertyComponent } from './property/property.component';

const APP_ROUTE: Route[] = [
       {path: '', component: AuthenticationComponent }, 
       { path:'accueil', component: AuthenticationComponent },
       { path:'reservation', component: ReservationComponent },
       { path:'apartment', component: ApartmentComponent },
       { path:'property', component: PropertyComponent }
   ]

export const AppRouting = RouterModule.forRoot(APP_ROUTE);