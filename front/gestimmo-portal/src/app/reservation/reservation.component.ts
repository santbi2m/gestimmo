import { Component, OnInit } from '@angular/core';
import { ReservationService } from './reservation.service';
import { Reservation } from '../shared/Models/reservation.model';
import { MatTableDataSource } from '@angular/material';

@Component({
  selector: 'app-reservation',
  templateUrl: './reservation.component.html',
  styleUrls: ['./reservation.component.css']
})
export class ReservationComponent implements OnInit {

public dataSource = new MatTableDataSource<Reservation>();
public reservations: Reservation[];
public displayedColumns = ['dateCheckin', 'dateCheckout', 'note', 'prix'];

  constructor(private reservationService: ReservationService) { }

  ngOnInit() {
    this.reservationService.getReservations().subscribe(reservations => {
      this.reservations = reservations;
      this.dataSource = new MatTableDataSource(this.reservations);
    });
    
  }

}
