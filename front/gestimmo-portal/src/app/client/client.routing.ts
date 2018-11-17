import { Route, RouterModule } from '@angular/router';
import { ClientComponent } from './client.component';
import { ClientDetailsComponent } from './client-details/client-details.component';

const CLIENT_ROUTE: Route[] = [
    {path: 'client', component: ClientComponent, children: [
        { path:'', component: ClientDetailsComponent },
        { path:':index', component: ClientDetailsComponent },
        { path:':index/edit', component: ClientDetailsComponent }
    ]}
]

export const clientRouting = RouterModule.forChild(CLIENT_ROUTE);