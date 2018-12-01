import { Route, RouterModule } from '@angular/router';
import { AuthenticationComponent } from './authentication/authentication.component';
import { AccueilComponent } from './accueil/accueil.component';

const APP_ROUTE: Route[] = [
       {path: '', component: AuthenticationComponent }, 
       { path:'accueil', component: AccueilComponent },
       { path:'login', component: AuthenticationComponent },
       { path:'logout', component: AccueilComponent }
   ]

export const AppRouting = RouterModule.forRoot(APP_ROUTE);