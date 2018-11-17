import { Component, OnInit, ViewChild } from '@angular/core';
import { Reservation } from 'src/app/shared/Models/reservation.model';
import { ActivatedRoute, Params } from '@angular/router';
import { ReservationService } from '../reservation.service';
import { MatTableDataSource, MatSort, MatPaginator, MatDialog } from '@angular/material';
import { Appartement } from 'src/app/shared/Models/apartment.model';
import { ApartmentService } from 'src/app/apartment/apartment.service';
import { ReservationDialogComponent } from '../reservation-dialog/reservation-dialog.component';

@Component({
  selector: 'app-reservation-details',
  templateUrl: './reservation-details.component.html',
  styleUrls: ['./reservation-details.component.css']
})
export class ReservationDetailsComponent implements OnInit {

  public index: number;
  public reservation: Reservation;
  public petitDej: string;

  public dataSource = new MatTableDataSource<Appartement>();
  //Liste des appartements
  public appartements: Appartement[];
  //Entêtes
  public displayedColumns = ['Libellé', 'Type', 'Bien', 'Prix'];
  @ViewChild('paginator') public paginator: MatPaginator;
  @ViewChild(MatSort) public sort: MatSort;

  public isNotAnnulee: boolean = true;

  constructor(private _router: ActivatedRoute, private reservationService: ReservationService,
    private appartementService: ApartmentService, private dialog: MatDialog) { }


  ngOnInit() {
    this._router.params.subscribe((params: Params) => {
      if (params.index)
        this.index = params.index;
      else
        this.index = 0;

      this.reservationService.getReservationbyId(this.index).subscribe((reservation: Reservation) => {
        this.reservation = reservation
        this.petitDej = this.reservation.petitDej ? "Oui" : "Non";

        if ((this.reservation.statut == 'Annulée') || (this.reservation.statut == 'Facturée')) {
          this.isNotAnnulee = false;
        } else {
          this.isNotAnnulee = true;
        }
      });

      this.appartementService.getAppartementByReservation(this.index).subscribe(appartements => {
        this.appartements = appartements;
        this.dataSource = new MatTableDataSource(this.appartements);
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
      })



    });



  }

  public getUrl(): string[] {
    return ['/reservation', this.index + '', 'edit'];
  }


  public openDialog(): void {
    if (confirm("Êtes-vous sûr d'annuler cette réservation? ")) {
      let dialogRef = this.dialog.open(ReservationDialogComponent, {
        width: '450px', height: '350px',
        data: this.reservation, disableClose: true
      });

      dialogRef.afterClosed().subscribe((result: any) => {
        if (result) {
          console.log(result);
          this.reservation.note = result;
         
          this.reservationService.annulerReservation(this.reservation).subscribe(
            (reservation: Reservation) => {
              //reservation.note= result;
              this.reservation = reservation;
              this.isNotAnnulee = false;
            });
        }
      })
    }
  }

  public annuler(reservation: Reservation): void {

    this.reservationService.annulerReservation(reservation).subscribe(
      (reservation: Reservation) => {
        this.reservation = reservation;
        this.isNotAnnulee = false;
      });


  }

}
