/**
 * 
 */
package com.ag2m.gestimmo.metier.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author mombaye
 *
 */
@Getter
@AllArgsConstructor
public enum EnumStatutReservation {

	/** Une réservation enregistrée, à confirmer */
	ENREGISTREE("Enregistrée"),
	/** Une réservation confirmée, dont le checkout n'est pas encore effectué */
	CONFIRMEE("Confirmée"),
	/** Une réservation en sur une ayant déjà une réservation enregistrée ou confirmée*/
	EN_ATTENTE("En attente"),
	/** Une réservation facturée, le séjour et le paiement ont donc déjà eu lieu*/
	FACTUREE("Facturée"),
	/** Une réservation sur un appartement plein, transféré chez un concurrent et donc facturée*/
	EN_ATTENTE_FACTUREE("En attente et facturée"),
	/** Une réservation annulée*/
	ANNULEE("Annulée"),
	/** Une réservation annulée tardivement et donc facturée*/
	ANNULEE_FACTUREE("Annulée et facturée");
	
	private String statut;
}
