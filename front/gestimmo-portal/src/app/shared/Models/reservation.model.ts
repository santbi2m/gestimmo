import { Client } from "./client.model";
import { Facture } from "./facture.model";
import { Appartement } from "./apartment.model";

export class Reservation{
    constructor(
        public id: number,
        public dateCheckin: string,  
        public dateCheckout: string, 
        public note: string,
        public petitDej: boolean,
        public statut: string,
        public prix: number,
        public appartements: Appartement[],
        public client?: Client,
        public facture?: Facture,
        
      ){ }
  }