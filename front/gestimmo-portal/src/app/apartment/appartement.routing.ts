import { Route, RouterModule } from '@angular/router';
import { ApartmentComponent } from './apartment.component';
import { AppartementCreateEditComponent } from './appartement-create-edit/appartement-create-edit.component';

const APPARTEMENT_ROUTE: Route[] = [
    {path: 'appartement', component: ApartmentComponent , children: [
        { path:'', component: AppartementCreateEditComponent },
        { path:'new', component: AppartementCreateEditComponent },
    ]}
]

export const appartementRouting = RouterModule.forChild(APPARTEMENT_ROUTE);