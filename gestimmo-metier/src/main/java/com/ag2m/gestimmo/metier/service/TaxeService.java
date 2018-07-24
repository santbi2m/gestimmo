package com.ag2m.gestimmo.metier.service;

import org.joda.time.LocalDateTime;

import com.ag2m.gestimmo.metier.entite.referentiel.Taxe;
import com.ag2m.gestimmo.metier.exception.FunctionalException;

public interface TaxeService {

	/**
	 * Charge l'objet taxe.
	 * Cette fonction est appelée au démarrage de 
	 * l'application afin de charger la Taxe en 
	 * en cours de validité en mémoire.
	 * 
	 * @return La taxe paamétrée dans la table 
	 * de référence des Taxes.
	 * 
	 * @throws FunctionalException 
	 */
	void loadCurrentTaxe() throws FunctionalException;
	
	/**
	 * Retourne la taxe valide à la date en entrée en paramètre.
	 * 
	 * @param date
	 * @return La taxe valide à date
	 * 
	 * * @throws FunctionalException 
	 */
	Taxe loadTaxeByDate(LocalDateTime date) throws FunctionalException;
}
