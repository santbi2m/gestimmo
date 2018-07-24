package com.ag2m.gestimmo.metier.service;

import com.ag2m.gestimmo.metier.exception.TechnicalException;

public interface ParametrageService {

	/**
	 * Charge toutes les remises en BDD.
	 * 
	 */
	void loadAllRemise();
	
	
	/**
	 * Recherche et charge le pourcentage de pénalité à appliquer lors d'une 
	 * annulation tardive de réservations.
	 * @throws TechnicalException 
	 * 
	 */
	void loadPourcentagePenanlite() throws TechnicalException;


	/**
	 * Recherche et charge le délai limite au-delà duquel, 
	 * tout annulation de réservation est pénalisable.
	 * 
	 * @throws TechnicalException
	 */
	void loadSeuilAnnulationGratuite() throws TechnicalException;
}
