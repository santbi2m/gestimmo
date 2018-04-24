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
public enum EnumTypeAppartement {

	T1("T1"),
	T2("T2"),
	T3("T3"),
	T4("T4"),
	T5("T5"),
	T6("T6"),
	CHAMBRE("Chambre"),
	STUDIO("Studio");
	
	
	private String type;
}
