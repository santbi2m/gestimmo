import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource, MatSort, MatPaginator } from '@angular/material';
import { Appartement } from 'src/app/shared/Models/apartment.model';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ApartmentService } from '../apartment.service';
import { AppartementCriteria } from 'src/app/shared/Models/appartementCriteria.model';
import { Bien } from 'src/app/shared/Models/bien.model';
import { BienService } from 'src/app/bien/bien.service';

@Component({
  selector: 'app-appartement-recherche',
  templateUrl: './appartement-recherche.component.html',
  styleUrls: ['./appartement-recherche.component.css']
})
export class AppartementRechercheComponent implements OnInit {


  //Datasource appartement
  public dataSourceAppart = new MatTableDataSource<Appartement>();

  //Liste des appartements
  public appartements: Appartement[];

   public biens: Bien[];
  //Entêtes
  public displayedColumns = ['libelle', 'type', 'prix', 'bien', 'details'];
  @ViewChild('paginator') public paginator: MatPaginator;
  @ViewChild(MatSort) public sort: MatSort;
  //form 
  public appartForm: FormGroup;

  public appartCriteria: AppartementCriteria;

  //selecte tab
  private selectedTab = 0;

  constructor(private fb: FormBuilder, private appartementService: ApartmentService, private bienService: BienService) { }

  ngOnInit() {
    this.appartementService.getAppartement().subscribe(appartements => {
      this.appartements = appartements;
      this.dataSourceAppart = new MatTableDataSource(this.appartements);
      this.dataSourceAppart.paginator = this.paginator;
      this.dataSourceAppart.sort = this.sort;
    });

    this.bienService.getBiens().subscribe(biens => {
      this.biens = biens;
    });

    this.appartForm = this.fb.group({
      libelle: [''],
      type: [''],
      idBien: ['']
    });
  }


  types: string[] = [
    "T1",
    "T2",
    "T3",
    "T4",
    "T5",
    "T6",
    "Chambre",
    "Maison",
    "Studio"
  ]

  public rechercher() {

    if (!this.appartForm.invalid) {
       this.appartCriteria = this.appartForm.value;
       if(this.appartCriteria.type == 'undefined') 
       this.appartCriteria.type = '';
       console.log(this.appartCriteria)
       this.appartementService.getAppartementCriteria(this.appartCriteria).subscribe(appartements => {
         this.appartements = appartements;
         this.dataSourceAppart = new MatTableDataSource(this.appartements);
         this.dataSourceAppart.paginator = this.paginator;
         this.dataSourceAppart.sort = this.sort;
       });
       this.changeTab();
    }

  }

  // changer Tab
  public changeTab() {
    setTimeout(() => {    //<<<---    using ()=> syntax
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
    this.dataSourceAppart.filter = filter;
  }



}
