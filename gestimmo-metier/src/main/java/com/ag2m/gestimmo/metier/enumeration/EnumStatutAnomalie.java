package com.ag2m.gestimmo.metier.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 
 * @author mombaye
 *
 */
@Getter
@AllArgsConstructor
public enum EnumStatutAnomalie {

	DECLAREE("Déclarée"),
	EN_TRAITEMENT("En traitement"),
	REPAREE("Réparée"),
	ANNULEE("Annulée");
	
	private String statut;
	
}
