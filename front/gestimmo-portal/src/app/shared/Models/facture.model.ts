import { Client } from "./client.model";
import { Adresse } from "./address.model";
import { Reservation } from "./reservation.model";

export class Facture{
    constructor(
        public client: Client,
        public taxeSejour: number,
        public adresseFacturation: Adresse,
        public tva: number,
        public remise: number,
        public numeroFacture: string,
        public dateCreation: Date,
        public reservations?: Reservation[]

    ){}
}