package com.ag2m.gestimmo.metier.service;

import com.ag2m.gestimmo.metier.exception.FunctionalException;

public interface ParametrageService {

	/**
	 * Charge l'objet taxe.
	 * Cette fonction est appelée au démarrage de 
	 * l'application afin de charger la Taxe en 
	 * mémoire.
	 * 
	 * @return La taxe paamétrée dans la table 
	 * de référence Taxe.
	 * @throws FunctionalException 
	 */
	void loadTaxe() throws FunctionalException;
}
