import { Route, RouterModule } from '@angular/router';
import { ApartmentComponent } from './apartment.component';
import { AppartementCreateEditComponent } from './appartement-create-edit/appartement-create-edit.component';
import { AppartementRechercheComponent } from './appartement-recherche/appartement-recherche.component';
import { AppartementDetailComponent } from './appartement-detail/appartement-detail.component';

const APPARTEMENT_ROUTE: Route[] = [
    {path: 'appartement', component: ApartmentComponent , children: [
        { path:'', component: AppartementRechercheComponent },
        { path:'new', component: AppartementCreateEditComponent },
        { path:':index', component: AppartementDetailComponent },
        { path:':index/edit', component: AppartementCreateEditComponent }
    ]}
]

export const appartementRouting = RouterModule.forChild(APPARTEMENT_ROUTE);
