package com.ag2m.gestimmo.metier.enumeration;

/**
 * 
 * @author mombaye
 *
 */
public enum EnumStatutAnomalie {

	DECLAREE("Déclarée"),
	EN_TRAITEMENT("En traitement"),
	REPAREE("Réparée"),
	ANNULEE("Annulée");
	
	
	private String statut;
	
	EnumStatutAnomalie(String statut) {
		this.statut = statut;
	}
	
	

	/**
	 * @return the type
	 */
	public String getStatut() {
		return statut;
	}
}
