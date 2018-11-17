import { Component, OnInit, Inject } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Reservation } from 'src/app/shared/Models/reservation.model';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

@Component({
  selector: 'app-reservation-dialog',
  templateUrl: './reservation-dialog.component.html',
  styleUrls: ['./reservation-dialog.component.css']
})
export class ReservationDialogComponent implements OnInit {



  public resaAnnulForm: FormGroup;
  public reservation: Reservation;
  private edit: boolean;
  public note: string;

  constructor( private dialog: MatDialogRef<ReservationDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) { }

  ngOnInit() {

    this.reservation = this.data
    this.note = this.reservation.note;

  }

  onNoClick(): void {
    this.dialog.close();
  }



}
