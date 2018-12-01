import { Bien } from "./bien.model";
import { Reservation } from "./reservation.model";

export class Appartement{
    constructor(
        public id: number,
        public libelle: string,
        public type: string,
        public bien: Bien,
        public prix: number,
        public reservations?: Reservation[],
       // public anomalies: Anomalies[]
    ){}
}