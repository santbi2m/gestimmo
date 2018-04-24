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
	PAYEE("Payée"),
	ANNULEE("Annulée");
	
	private String statut;
}
