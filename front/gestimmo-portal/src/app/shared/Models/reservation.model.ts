
export class Reservation{
    constructor(
        public dateCheckin: Date,  
        public dateCheckout: Date, 
        public note: string,
        public petitDej: boolean,
        public statut: string,
        public prix: number,
        public dateCreation: Date,
        public dateAnnulation: Date
       
      ){ }
  }