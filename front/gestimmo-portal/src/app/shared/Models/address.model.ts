import { Client } from "./client.model";

export class Adresse {
    constructor(
        public adresse: string,
        public complementAdresse: string,
        public codePostal: number,
        public ville: string,
        public pays: string,
        public clients: Client[]

    ) { }
}