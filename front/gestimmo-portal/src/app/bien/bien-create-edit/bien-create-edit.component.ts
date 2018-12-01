import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Bien } from 'src/app/shared/Models/bien.model';
import { Adresse } from 'src/app/shared/Models/address.model';
import { ActivatedRoute, Params } from '@angular/router';
import { BienService } from '../bien.service';

@Component({
  selector: 'app-bien-create-edit',
  templateUrl: './bien-create-edit.component.html',
  styleUrls: ['./bien-create-edit.component.css']
})
export class BienCreateEditComponent implements OnInit {

  //form 
  public bienForm: FormGroup;
  public adresseForm: FormGroup;
  public bien: Bien;
  public adresse: Adresse;
  //selecte tab
  private selectedTab = 0;

  constructor(private activatedRoute: ActivatedRoute, private fb: FormBuilder,
    private bienService: BienService) { }

  ngOnInit() {
    this.activatedRoute.params.subscribe((params: Params) => {
      if (params.index) {
        //this.edit = true;
        
        this.bienService.getBienById(params.index).subscribe((bien: Bien) => {
          this.bien = bien;
          
          this.initBienForm(this.bien);
          this.initAdresseForm(this.bien.adresse)

        });

      } else {
        
        this.initBienForm();
        this.initAdresseForm();
      }
    })
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

  initBienForm(bien = { libelle: '' }) {
    this.bienForm = this.fb.group({
      libelle: [bien.libelle, Validators.required]

    });


  }

  public getErrorMessageLibelle() {
    if (this.bienForm.controls['libelle'].hasError('required')) {
      return "Le libellé est obligatoire";
    } else {
      return "";
    }

  }

  public valider() {

    if (!this.bienForm.invalid && !this.adresseForm.invalid) {
      this.bien = this.bienForm.value;

      this.adresse = this.adresseForm.value;
      this.bien.adresse = this.adresse;

      console.log(this.bien)
      this.bienService.createBien(this.bien).subscribe((bien: Bien) => {
        this.bien = bien,
          error => alert(error)
      })
    }
  }

  public resetForm(){
    this.bienForm.reset();
    this.adresseForm.reset();
  }
}
