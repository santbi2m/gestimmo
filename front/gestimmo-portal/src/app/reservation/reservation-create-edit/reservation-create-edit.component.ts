import { Component, OnInit } from '@angular/core';
import { MatTableDataSource, NativeDateAdapter, MAT_DATE_FORMATS, DateAdapter } from '@angular/material';
import { Reservation } from 'src/app/shared/Models/reservation.model';
import { Appartement } from 'src/app/shared/Models/apartment.model';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ReservationService } from '../reservation.service';
import { ApartmentService } from 'src/app/apartment/apartment.service';

import * as moment from "moment";
import { Client } from 'src/app/shared/Models/client.model';
import { Adresse } from 'src/app/shared/Models/address.model';
import { error } from '@angular/compiler/src/util';
import { ActivatedRoute, Params } from '@angular/router';


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
const dateFormat = "DD-MM-YYYY hh:mm:ss";
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
  selector: 'app-reservation-create-edit',
  templateUrl: './reservation-create-edit.component.html',
  styleUrls: ['./reservation-create-edit.component.css'],
  providers: [
    { provide: MAT_DATE_FORMATS, useValue: CUSTOM_DATE_FORMATS },
    { provide: DateAdapter, useClass: AppDateAdapter }
  ]
})
export class ReservationCreateEditComponent implements OnInit {

  //Datasource appartement
  public dataSourceAppart = new MatTableDataSource<Appartement>();
  //Liste réservations
  public reservations: Reservation[];
  //Liste des appartements
  public appartements: Appartement[];
  public appartements2: Appartement[];

  //form 
  public resaForm: FormGroup;
  public clientForm: FormGroup;
  public adresseForm: FormGroup;
  // Objetc criteria
  public reservation: Reservation;
  public client: Client;
  public adresse: Adresse;

  //selecte tab
  private selectedTab = 0;

  //selecte tab
  public selectedTabAdress: boolean = true;
  public selectedTabClient: boolean = true;

  constructor(private activatedRoute: ActivatedRoute, private fb: FormBuilder, 
    private resaService: ReservationService, private appartService: ApartmentService, 
    private appartementService: ApartmentService) {


  }

  ngOnInit() {
    this.appartService.getAppartement().subscribe(appartements => {
      this.appartements = appartements,
      error => alert(error);
      this.dataSourceAppart = new MatTableDataSource(this.appartements);

    });
    
    this.activatedRoute.params.subscribe((params: Params)=>{
      if(params.index){
        //this.edit = true;
        console.log('test1 '+params.index)
        this.resaService.getReservationbyId(params.index).subscribe( (reservation: Reservation) => {
          this.reservation = reservation;
          //console.log('test0 '+reservation.appartements.length)
           this.listeAppartement(params.index)
          this.reservation.appartements = this.appartements2 
          
          this.initResaForm(this.reservation);
         
          console.log('test1111 '+this.resaForm.controls['dateCheckout'].value)
          this.initClientForm(this.reservation.client);
          this.initAdresseForm(this.reservation.client.adresse)
          
        });
        
      }else{
       // this.edit = false;
       console.log('test2')
       this.initResaForm();
       this.initClientForm();
       this.initAdresseForm();
      }
    })

   
  }

  initResaForm(reservation = { dateCheckin: '', dateCheckout: '', petitDej: false, prix: 0, statut: '', note: '', appartements: [] }) {
    this.resaForm = this.fb.group({
      dateCheckin: [reservation.dateCheckin, Validators.required],
      dateCheckout: [reservation.dateCheckout, Validators.required],
      petitDej: [reservation.petitDej],
      statut: [reservation.statut, Validators.required],
      prix: [reservation.prix],
      note: [reservation.note],
      appartements: [reservation.appartements, Validators.required],

    });
  }

  initAdresseForm(adresse = { adresse: '', complementAdresse: '', codePostal: 0, ville: '', pays: '' }) {
    this.adresseForm = this.fb.group({
      adresse: [adresse.adresse],
      complementAdresse: [adresse.complementAdresse],
      codePostal: [adresse.codePostal],
      ville: [adresse.ville],
      pays: [adresse.pays]

    });
  }

  initClientForm(client = { nom: '', prenom: '', adresseEmail: '', numeroPieceIdentite: '', typePieceIdentite: '', telephone: '' }) {
    this.clientForm = this.fb.group({
      nom: [client.nom, Validators.required],
      prenom: [client.prenom, Validators.required],
      adresseEmail: [client.adresseEmail],
      numeroPieceIdentite: [client.numeroPieceIdentite],
      typePieceIdentite: [client.typePieceIdentite],
      telephone: [client.telephone, Validators.required],

    });


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

  public suivantResa() {

    if (!this.resaForm.invalid) {
      this.selectedTabClient = true;
      this.changeTab();
    }

  }

  public suivantClient() {

    if (!this.clientForm.invalid) {
      this.selectedTabClient = true
      this.changeTab();
    }

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

  // changer Tab
  public changeTab() {

    this.selectedTab += 1;
    if (this.selectedTab >= 3) this.selectedTab = 0;

  }

  // retour dernier tab
  public backTabClient() {
   this.selectedTab = 0;
  }

  // retour dernier tab
  public backTabAddress() {
    console.log(this.selectedTab)
     this.selectedTab = 1;
  }

  

  public valider() {

    if (!this.resaForm.invalid && !this.clientForm.invalid && !this.adresseForm.invalid) {
      this.reservation = this.resaForm.value;
      console.log(moment(this.resaForm.controls['dateCheckin'].value).format(dateFormat));
      this.reservation.dateCheckin = moment(this.resaForm.controls['dateCheckin'].value).format(dateFormat);
      this.reservation.dateCheckout = moment(this.resaForm.controls['dateCheckout'].value).format(dateFormat);

      this.reservation.appartements = this.resaForm.controls['appartements'].value;

      this.client = this.clientForm.value;
      this.adresse = this.adresseForm.value;
      this.client.adresse = this.adresse;
      this.reservation.client = this.client;

      console.log(this.reservation)
      this.resaService.creationReservation(this.reservation).subscribe((reservation: Reservation)=>{
        this.reservation = reservation,
        error => alert(error)
      })
    }

  }

  public listeAppartement(id: number) {
    this.appartementService.getAppartementByReservation(id).subscribe(appartements1 => {
      this.appartements2 = appartements1;
      console.log(this.appartements2.length)
    });

   
   
  }

}

