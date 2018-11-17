import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule, FormsModule  } from '@angular/forms';

import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { AuthenticationComponent } from './authentication/authentication.component';
import { ReservationComponent } from './reservation/reservation.component';
import { ApartmentComponent } from './apartment/apartment.component';
import { AppRouting } from './app.routing';
import { MaterialModule } from './shared/material/material.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';
import { BienComponent } from './bien/bien.component';
import { ClientComponent } from './client/client.component';
import { ClientDetailsComponent } from './client/client-details/client-details.component';
import { clientRouting } from './client/client.routing';
import { bienRouting } from './bien/bien.routing';
import { reservationRouting } from './reservation/reservation.routing';
import { appartementRouting } from './apartment/appartement.routing';
import { ReservationRechercheComponent } from './reservation/reservation-recherche/reservation-recherche.component';
import { ReservationCreateEditComponent } from './reservation/reservation-create-edit/reservation-create-edit.component';
import { ReservationDetailsComponent } from './reservation/reservation-details/reservation-details.component';
import { DialogComponent } from './dialog/dialog.component';
import { ReservationDialogComponent } from './reservation/reservation-dialog/reservation-dialog.component';
import { BienCreateEditComponent } from './bien/bien-create-edit/bien-create-edit.component';
import { AppartementCreateEditComponent } from './apartment/appartement-create-edit/appartement-create-edit.component';


@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    AuthenticationComponent,
    ReservationComponent,
    ApartmentComponent,
    BienComponent,
    ClientComponent,
    ClientDetailsComponent,
    ReservationRechercheComponent,
    ReservationCreateEditComponent,
    ReservationDetailsComponent,
    DialogComponent,
    ReservationDialogComponent,
    BienCreateEditComponent,
    AppartementCreateEditComponent,
  ],
  imports: [
    BrowserModule,
    AppRouting,
    FormsModule,
    ReactiveFormsModule,
    MaterialModule,
    BrowserAnimationsModule,
    HttpClientModule,
    clientRouting,
    bienRouting,
    reservationRouting,
    appartementRouting
  ],
  entryComponents: [DialogComponent, ReservationDialogComponent],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
