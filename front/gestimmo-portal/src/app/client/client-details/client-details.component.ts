import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { Client } from 'src/app/shared/Models/client.model';
import { ClientService } from '../client.service';

@Component({
  selector: 'app-client-details',
  templateUrl: './client-details.component.html',
  styleUrls: ['./client-details.component.css']
})
export class ClientDetailsComponent implements OnInit {

  public index: number;
  public client: Client;

  constructor(private _router: ActivatedRoute, private clientService: ClientService) { }

  ngOnInit() {
    this._router.params.subscribe((params: Params) => {
      if (params.index)
        this.index = params.index;
      else
        this.index = 0;

      this.clientService.getClient(this.index).subscribe((client: Client) => {
        this.client = client
        console.log(this.client.prenom);
      });

    });

  }

  getUrl(): string[] {
    return ['/clients', this.index + '', 'edit'];
  }

}