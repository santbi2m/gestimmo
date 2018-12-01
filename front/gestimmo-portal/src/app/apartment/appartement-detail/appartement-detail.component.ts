import { Component, OnInit, ViewChild } from '@angular/core';
import { Appartement } from 'src/app/shared/Models/apartment.model';
import { MatTableDataSource, MatSort } from '@angular/material';
import { ActivatedRoute, Params } from '@angular/router';
import { ApartmentService } from '../apartment.service';

@Component({
  selector: 'app-appartement-detail',
  templateUrl: './appartement-detail.component.html',
  styleUrls: ['./appartement-detail.component.css']
})
export class AppartementDetailComponent implements OnInit {

  public index: number;
  public appartement: Appartement;

  //Liste des appartements
  public appartements: Appartement[];
  //Entêtes


  public isNotAnnulee: boolean = true;


  constructor(private _router: ActivatedRoute,
    private appartementService: ApartmentService) { }

  ngOnInit() {

    this._router.params.subscribe((params: Params) => {
      if (params.index)
        this.index = params.index;
      else
        this.index = 0;

      this.appartementService.getAppartementById(this.index).subscribe((appartement: Appartement) => {
        this.appartement = appartement
      });

    })
}

public getUrl(): string[] {
  return ['/appartement', this.index + '', 'edit'];
}

}
