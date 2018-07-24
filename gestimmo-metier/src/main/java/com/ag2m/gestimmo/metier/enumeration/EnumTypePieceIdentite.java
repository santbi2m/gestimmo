package com.ag2m.gestimmo.metier.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 
 * @author mombaye
 * 
 * Enumérateur des types
 * de pièces de d'identité.
 * 
 */
@Getter
@AllArgsConstructor
public enum EnumTypePieceIdentite {

	/** Enum CARTE_IDENTITE*/
	CARTE_IDENTITE("Carte d'identité"),
	
	/** Enum PERMIS_CONDUIRE*/
	PERMIS_CONDUIRE("Permis de conduire"),
	
	/** Enum PASSEPORT*/
	PASSEPORT("Passeport");
	
	private String type;
}
