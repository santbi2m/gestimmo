package com.ag2m.gestimmo.metier.dao;

import org.joda.time.LocalDateTime;

import com.ag2m.gestimmo.metier.entite.referentiel.Taxe;
import com.ag2m.gestimmo.metier.exception.FunctionalException;

/**
 * @author mombaye
 *
 * Dao pour Parametrage.
 * Contient tout le paramétrage à charger au démarage de l'application.
 */
public interface ParametrageDao {

	/**
	 * Charge l'objet taxe valide à la date
	 * courante.
	 * 
	 * @return La taxe paramétrée dans la table 
	 * de référence Taxe.
	 */
	Taxe loadCurrentTaxe();
	
	
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
