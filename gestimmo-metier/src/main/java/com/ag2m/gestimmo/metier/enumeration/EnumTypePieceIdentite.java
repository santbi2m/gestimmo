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

	CARTE_IDENTITE("Carte d'identité"),
	PERMIS_CONDUIRE("Permis de conduire"),
	PASSEPORT("Passeport");
	
	private String type;
}
