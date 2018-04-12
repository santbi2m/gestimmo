/**
 * 
 */
package com.ag2m.gestimmo.metier.enumeration;

/**
 * @author mombaye
 *
 */
public enum EnumStatutReservation {

	ENREGISTREE("Enregistrée"),
	CONFIRMEE("Confirmée"),
	PAYEE("Payée"),
	ANNULEE("Annulée");
	
	
	private String statut;
	
	EnumStatutReservation(String statut) {
		this.statut = statut;
	}
	
	

	/**
	 * @return the type
	 */
	public String getStatut() {
		return statut;
	}
}
