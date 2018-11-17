import { Adresse } from "./address.model";
import { Reservation } from "./reservation.model";
import { Facture } from "./facture.model";

export class Client {
    constructor(
        public id: number,
        public nom: string,
        public prenom: string,
        public adresseEmail: string,
        public numeroPieceIdentite: string,
        public typePieceIdentite: string,
        public telephone: string,
        public adresse: Adresse,
        public reservations?: Reservation[],
        public factures?: Facture[]

    ) { }
}