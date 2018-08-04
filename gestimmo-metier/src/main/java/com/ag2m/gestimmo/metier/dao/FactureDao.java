package com.ag2m.gestimmo.metier.dao;

import com.ag2m.gestimmo.metier.entite.Facture;

/**
 * @author mombaye
 *
 * Dao pour Facture
 */
public interface FactureDao extends CommonDao<Long, Facture> {

	/**
	 * Recherche le numéro de la dernière facture enregistrée en BDD
	 * @return
	 */
	String findLastNumFacture();

}
