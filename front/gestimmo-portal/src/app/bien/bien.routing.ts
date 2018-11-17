import { Route, RouterModule } from '@angular/router';
import { BienComponent } from './bien.component';

const BIEN_ROUTE: Route[] = [
    {path: 'bien', component: BienComponent }/*, children: [
        { path:'', component: ClientDetailsComponent },
        { path:':index', component: ClientDetailsComponent },
    ]}*/
]

export const bienRouting = RouterModule.forChild(BIEN_ROUTE);