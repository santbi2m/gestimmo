
export class ReservationCriteria{
    constructor(
        public dateCheckin: string,  
        public dateCheckout: string, 
        public petitDej: boolean = false,
        public statut: string[],
        public prix: number,
        public idClient: number,
        public idAppartements?: number[],
        public idFacture?: number
      ){ }
  }