import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource, MatPaginator, MatSort } from '@angular/material';
import { Bien } from 'src/app/shared/Models/bien.model';
import { BienService } from '../bien.service';

@Component({
  selector: 'app-bien-list',
  templateUrl: './bien-list.component.html',
  styleUrls: ['./bien-list.component.css']
})
export class BienListComponent implements OnInit {

  public dataSource = new MatTableDataSource<Bien>();
  public biens: Bien[];
  public displayedColumns = ['libelle', 'adresse', 'modifier'];
  @ViewChild('paginator') public paginator: MatPaginator;
  @ViewChild(MatSort) public sort: MatSort;
  

  constructor(private bienService: BienService) { }

  ngOnInit() {
   
    this.bienService.getBiens().subscribe(biens => {
      this.biens = biens;
      this.dataSource = new MatTableDataSource(this.biens);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    });
  }


}
