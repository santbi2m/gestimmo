import { Component, OnInit , ViewChild} from '@angular/core';
import { ReservationService } from './reservation.service';
import { Reservation } from '../shared/Models/reservation.model';
import { MatTableDataSource, MatPaginator, MatSort } from '@angular/material';
import { Client } from '../shared/Models/client.model';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-reservation',
  templateUrl: './reservation.component.html',
  styleUrls: ['./reservation.component.css']
})
export class ReservationComponent implements OnInit {

public dataSource = new MatTableDataSource<Reservation>();
public reservations: Reservation[];
public displayedColumns = ['Date Check In', 'Date Check Out', 'Note', 'Prix', 'Client'];
@ViewChild('paginator') public paginator: MatPaginator;
@ViewChild(MatSort) public sort: MatSort;

public client: Client;

  constructor(private reservationService: ReservationService, private activedRoute: ActivatedRoute) { }

  ngOnInit() {
    this.reservationService.getReservations().subscribe(reservations => {
      this.reservations = reservations;
      this.dataSource = new MatTableDataSource(this.reservations);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    });
    
  }

  public updateFilter(filter: string): void{
    filter = filter.trim().toLowerCase();
    this.dataSource.filter = filter;
  }

  navigate(client: Client): string[]{
    return ['/client', client.id.toString() ];
  }

}
