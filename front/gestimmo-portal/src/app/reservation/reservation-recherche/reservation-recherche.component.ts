import { Component, OnInit, ViewChild } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ReservationService } from '../reservation.service';
import { ReservationCriteria } from 'src/app/shared/Models/reservationCriteria.model';
import { MatTableDataSource, MatPaginator, MatSort, NativeDateAdapter } from '@angular/material';
import { Reservation } from 'src/app/shared/Models/reservation.model';
import { DateAdapter, MAT_DATE_FORMATS } from "@angular/material/core";
import * as moment from "moment";
import { ApartmentService } from 'src/app/apartment/apartment.service';
import { Appartement } from 'src/app/shared/Models/apartment.model';


const CUSTOM_DATE_FORMATS = {
  parse: {
    dateInput: { month: "short", year: "numeric", day: "numeric" }
  },
  display: {
    dateInput: "input",
    monthYearLabel: { year: "numeric", month: "short" },
    dateA11yLabel: { year: "numeric", month: "long", day: "numeric" },
    monthYearA11yLabel: { year: "numeric", month: "long" }
  }
};
const dateFormat = "YYYY-MM-DD HH:mm:ss";
// date adapter formatting material2 datepickers label when a date is selected
class AppDateAdapter extends NativeDateAdapter {
  format(date: Date, displayFormat: Object): string {
    if (displayFormat === "input") {
      return moment(date).format(dateFormat);
    } else {
      return date.toDateString();
    }
  }
}

@Component({
  selector: 'app-reservation-recherche',
  templateUrl: './reservation-recherche.component.html',
  styleUrls: ['./reservation-recherche.component.css'],
  providers: [
    { provide: MAT_DATE_FORMATS, useValue: CUSTOM_DATE_FORMATS },
    { provide: DateAdapter, useClass: AppDateAdapter }
  ]
})
export class ReservationRechercheComponent implements OnInit {

  //Datasource reservations
  public dataSource = new MatTableDataSource<Reservation>();
  //Datasource appartement
  public dataSourceAppart = new MatTableDataSource<Appartement>();
  //Liste réservations
  public reservations: Reservation[];
  //Liste des appartements
  public appartements: Appartement[];
  //Entêtes
  public displayedColumns = ['Date Check In', 'Date Check Out', 'Prix', 'Client', 'Details'];
  @ViewChild('paginator') public paginator: MatPaginator;
  @ViewChild(MatSort) public sort: MatSort;
  //form 
  public resaForm: FormGroup;
  
  // Objetc criteria
  public resaCriteria: ReservationCriteria;

  //selecte tab
  private selectedTab = 0;

  constructor(fb: FormBuilder, private resaService: ReservationService, private appartService: ApartmentService) {
    this.resaService.getReservations().subscribe(reservations => {
      this.reservations = reservations;
      this.dataSource = new MatTableDataSource(this.reservations);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    });

   /* this.appartService.getAppartement().subscribe(appartements => {
      this.appartements = appartements;
      this.dataSourceAppart = new MatTableDataSource(this.appartements);
      this.dataSourceAppart.paginator = this.paginator;
      this.dataSourceAppart.sort = this.sort;
    });*/

    this.resaForm = fb.group({
      dateCheckin: ['', Validators.required],
      dateCheckout: ['', Validators.required],
      petitDej: [false,],
      statut: [''],
      prix: [''],
      appartements: ['']
    });

  }
  ngOnInit() {
  }

  statuts: string[] = [
    'Enregistrée',
    'Confirmée',
    'En Attente',
    'Facturée',
    'En Attente et facturée',
    'Annulée',
    'Annulée et facturée'
  ];

  public rechercher() {

    if (!this.resaForm.invalid) {
      this.resaCriteria = this.resaForm.value;
      console.log(moment(this.resaForm.controls['dateCheckin'].value).format(dateFormat));
      this.resaCriteria.dateCheckin = moment(this.resaForm.controls['dateCheckin'].value).format(dateFormat);
      this.resaCriteria.dateCheckout = moment(this.resaForm.controls['dateCheckout'].value).format(dateFormat);

      this.resaService.getReservationsCriteria(this.resaCriteria).subscribe(reservations => {
        this.reservations = reservations;
        this.dataSource = new MatTableDataSource(this.reservations);
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
      });
      this.changeTab();
    }

  }

  // changer Tab
  public changeTab() {
    setTimeout(()=>{    //<<<---    using ()=> syntax
      this.selectedTab += 1;
    if (this.selectedTab >= 2) this.selectedTab = 0;
 }, 1500);
    
  }
  // retour dernier tab
  public backTab() {
    this.selectedTab += 1;
    if (this.selectedTab >= 2) this.selectedTab = 0;
  }

  public updateFilter(filter: string): void {
    filter = filter.trim().toLowerCase();
    this.dataSource.filter = filter;
  }

  public getErrorMessageDateIN() {
    if (this.resaForm.controls['dateCheckin'].hasError('required')) {
      return "La date de check in est requis";
    } else {
      return "";
    }

  }
  public getErrorMessageDateOut() {
    if (this.resaForm.controls['dateCheckout'].hasError('required')) {
      return "La date de check out est requis";
    } else {
      return "";
    }
  }

}
