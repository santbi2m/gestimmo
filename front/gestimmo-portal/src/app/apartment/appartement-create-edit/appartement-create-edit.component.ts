import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Params } from '@angular/router';
import { ApartmentService } from '../apartment.service';
import { Appartement } from 'src/app/shared/Models/apartment.model';
import { Bien } from 'src/app/shared/Models/bien.model';
import { MatTableDataSource } from '@angular/material';
import { BienService } from 'src/app/bien/bien.service';

@Component({
  selector: 'app-appartement-create-edit',
  templateUrl: './appartement-create-edit.component.html',
  styleUrls: ['./appartement-create-edit.component.css']
})
export class AppartementCreateEditComponent implements OnInit {

  //Datasource appartement
  public dataSourceBien = new MatTableDataSource<Bien>();
  //form 
  public appartementForm: FormGroup;
  public appartement: Appartement;

  public biens: Bien[];

  constructor(private activatedRoute: ActivatedRoute, private fb: FormBuilder,
    private appartementService: ApartmentService, private bienService: BienService) { }

  ngOnInit() {

    this.bienService.getBiens().subscribe(biens => {
      this.biens = biens,
        error => alert(error);
      this.dataSourceBien = new MatTableDataSource(this.biens);

    });

    this.activatedRoute.params.subscribe((params: Params) => {
      if (params.index) {
        //this.edit = true;
        console.log('test1 ' + params.index)
        this.appartementService.getAppartementById(params.index).subscribe((appartement: Appartement) => {
          this.appartement = appartement;

          this.initAppartForm(this.appartement)

        });

      } else {
        // this.edit = false;
        console.log('test2')
        this.initAppartForm();
      }
    })
  }

  initAppartForm(appartement = { libelle: '', type: '', prix: null, bien: null }) {
    this.appartementForm = this.fb.group({
      libelle: [appartement.libelle, Validators.length],
      type: [appartement.type],
      prix: [appartement.prix],
      bien: [appartement.bien]

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

  public getErrorMessageLibelle() {
    if (this.appartementForm.controls['libelle'].hasError('required')) {
      return "Le libellé est obligatoire";
    } else {
      return "";
    }

  }

  public valider() {

    if (!this.appartementForm.invalid) {
      this.appartement = this.appartementForm.value;


      console.log(this.appartement)
      this.appartementService.createAppartement(this.appartement).subscribe((appartement: Appartement) => {
        this.appartement = appartement,
          error => alert(error)
      })
    }
  }

  public reset(): void {
    this.appartementForm.reset();
  }

}
