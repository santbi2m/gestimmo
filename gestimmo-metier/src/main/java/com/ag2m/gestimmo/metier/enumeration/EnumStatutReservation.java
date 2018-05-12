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

	ENREGISTREE("Enregistrée"),
	CONFIRMEE("Confirmée"),
	FACTUREE("Facturée"),
	ANNULEE("Annulée"),
	ANNULEE_FACTUREE("Annulée et facturée");
	
	private String statut;
}
