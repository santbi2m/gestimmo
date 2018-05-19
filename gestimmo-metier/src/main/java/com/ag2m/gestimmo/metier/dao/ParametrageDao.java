package com.ag2m.gestimmo.metier.dao;

import com.ag2m.gestimmo.metier.entite.referentiel.Taxe;

/**
 * @author mombaye
 *
 * Dao pour Parametrage.
 * Contient tout le paramétrage à charger au démarage de l'application.
 */
public interface ParametrageDao {

	/**
	 * Charge l'objet taxe
	 * 
	 * @return La taxe paamétrée dans la table 
	 * de référence Taxe.
	 */
	Taxe loadTaxe();
}
