import { Route, RouterModule } from '@angular/router';
import { ReservationComponent } from './reservation.component';
import { ReservationRechercheComponent } from './reservation-recherche/reservation-recherche.component';
import { ReservationCreateEditComponent } from './reservation-create-edit/reservation-create-edit.component';
import { ReservationDetailsComponent } from './reservation-details/reservation-details.component';

const RESERVATION_ROUTE: Route[] = [
    {path: 'reservation', component: ReservationComponent , children: [
        { path:'', component: ReservationRechercheComponent },
        { path:'new', component: ReservationCreateEditComponent },
        { path:':index', component: ReservationDetailsComponent },
        { path:':index/edit', component: ReservationCreateEditComponent }
    ]}
]

export const reservationRouting = RouterModule.forChild(RESERVATION_ROUTE);