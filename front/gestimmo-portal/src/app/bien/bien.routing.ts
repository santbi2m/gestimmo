import { Route, RouterModule } from '@angular/router';
import { BienComponent } from './bien.component';
import { BienCreateEditComponent } from './bien-create-edit/bien-create-edit.component';
import { BienListComponent } from './bien-list/bien-list.component';

const BIEN_ROUTE: Route[] = [
    {path: 'bien', component: BienComponent , children: [
        { path:'', component: BienListComponent },
        { path:'new', component: BienCreateEditComponent },
        { path:':index/edit', component: BienCreateEditComponent },
    ]}
]

export const bienRouting = RouterModule.forChild(BIEN_ROUTE);