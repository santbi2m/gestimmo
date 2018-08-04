package com.ag2m.gestimmo.metier.dao;

import com.ag2m.gestimmo.metier.entite.Devis;

/**
 * @author mombaye
 *
 * Dao pour Devis
 */
public interface DevisDao extends CommonDao<Long, Devis> {

	/**
	 * Recherche le numéro du dernier devis enregistré en BDD
	 * @return
	 */
	String findLastNumDevis();

}
