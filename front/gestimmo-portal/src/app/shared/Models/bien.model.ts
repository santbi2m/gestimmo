import { Adresse } from "./address.model";
import { Appartement } from "./apartment.model";

export class Bien{
    constructor(
        public libelle: string,
        public adresse: Adresse,
        public appartement?: Appartement[]
    ){}
}