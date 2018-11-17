import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule,
        MatStepperModule,
        MatIconModule,
        MatIconRegistry,
        MatTableModule,
        MatPaginatorModule,
        MatSortModule,
        MatInputModule,
        MatTabsModule,
        MatFormFieldModule,
        MatSelectModule,
        MatDatepickerModule,
        MatNativeDateModule,
        MatCheckboxModule,
        MAT_DATE_LOCALE,
        MatDialogModule,
        MatButtonModule
         } from '@angular/material';

  import { MatMomentDateModule } from '@angular/material-moment-adapter'
  import { FlexLayoutModule } from '@angular/flex-layout';

const Material = [
  MatCardModule,
  MatStepperModule,
  MatIconModule,
  MatTableModule,
  MatPaginatorModule,
  MatSortModule,
  MatInputModule,
  MatTabsModule,
  MatFormFieldModule,
  MatSelectModule,
  MatDatepickerModule,
  MatNativeDateModule,
  MatCheckboxModule,
  MatMomentDateModule,
  MatDialogModule,
  MatButtonModule,
  FlexLayoutModule
]

@NgModule({
  imports: [
    CommonModule,
    ...Material
  ],
  declarations: [],
  providers:[
  MatIconRegistry,
  {provide: MAT_DATE_LOCALE, useValue: 'fr-FR'}   
  ],
  exports: Material
})
export class MaterialModule { }
