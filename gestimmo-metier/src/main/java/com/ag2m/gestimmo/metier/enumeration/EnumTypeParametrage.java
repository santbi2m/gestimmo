package com.ag2m.gestimmo.metier.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 
 * @author mombaye
 * 
 * Enumérateur des différents types
 * de paramétrage en BDD.
 * 
 */
@Getter
@AllArgsConstructor
public enum EnumTypeParametrage {
	
	/** 
	 * Enum POURCENTAGE_PENALITE 
	 * Pourcentage de pénalité à appliquer lors d'une annulation tardive
	 * de réservation. exemple 50 représente 50% de la première nuitée.
	 * */
	POURCENTAGE_PENALITE("PENALITE"),
	
	/** 
	 * Enum SEUIL_NON_PENALISABLE 
	 * Délai limite (en heure) avant lequel l'annulation d'une réservation est totalement
	 * gratuite, et sans aucune pénalité.
	 * 
	 * Ce délai représente l'écart entre la date d'annulation et la date de checkin prévue
	 * à la base.
	 * 
	 * */
	SEUIL_NON_PENALISABLE("SEUIL_NON_PENALISABLE");
	
	private String type;
}
