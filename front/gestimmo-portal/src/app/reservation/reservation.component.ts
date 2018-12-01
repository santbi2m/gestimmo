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


  constructor() { }

  ngOnInit() {
    
    
  }

 

}
