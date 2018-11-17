import { Component, OnInit, ViewChild } from '@angular/core';
import { BienService } from './bien.service';
import { MatTableDataSource, MatSort, MatPaginator } from '@angular/material';
import { Bien } from '../shared/Models/bien.model';

@Component({
  selector: 'app-bien',
  templateUrl: './bien.component.html',
  styleUrls: ['./bien.component.css']
})
export class BienComponent implements OnInit {

  public dataSource = new MatTableDataSource<Bien>();
  public biens: Bien[];
  public displayedColumns = ['libelle', 'adresse'];
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
