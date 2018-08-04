package com.ag2m.gestimmo.metier.config;

import java.util.List;

import com.ag2m.gestimmo.metier.entite.referentiel.Remise;

/**
 * Classe contenant les constante et valeurs paramétrées en base
 * et initialisée au démarrarage de l'application.
 * 
 * @author mombaye
 *
 */
public  class ParamConfig {

	/** La TVA paramétrable */
	public static Double TVA;
	
	/** La taxe de séjour paramétrable*/
	public static Double TAXE_SEJOUR;
	
	/** La liste des remises paramétrées en BDD. */
	public static List<Remise> REMISES;
	
	/** Le pourcentage de pénalité paramétré en BDD. */
	public static Integer POURCENTAGE_PENALITE;
	
	/** Le délai à ne pas dépasser pour annuler gratuitement une réservation */
	public static Integer SEUIL_ANNULATION_GRATUITE;
	
}
