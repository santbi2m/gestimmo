import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule,
        MatStepperModule,
        MatIconModule,
        MatIconRegistry,
        MatTableModule
         } from '@angular/material';

const Material = [
  MatCardModule,
  MatStepperModule,
  MatIconModule,
  MatTableModule
]

@NgModule({
  imports: [
    CommonModule,
    ...Material
  ],
  declarations: [],
  providers:[
  MatIconRegistry    
  ],
  exports: Material
})
export class MaterialModule { }
