package com.ag2m.gestimmo.metier.dao;

import java.util.List;

import com.ag2m.gestimmo.metier.entite.Facture;
import com.ag2m.gestimmo.metier.exception.FunctionalException;
import com.ag2m.gestimmo.metier.ioparam.FactureCriteria;

/**
 * @author mombaye
 * @author asouane
 *
 * Dao pour Facture
 */
public interface FactureDao extends CommonDao<Long, Facture> {

	/**
	 *  Permet de combiner tous les critères possibles
	 *  de recherche de facture, de retourner 
	 *  le résultat et de le mettre dans le cache.
	 *  
	 * 
	 * Méthde laissée de coté pour motif de doublon avec recherche réservations
	 *
	 * @param factureCriteria
	 * @return
	 * @throws FunctionalException
	 */
	List<Facture> findFactureByCriteria(FactureCriteria factureCriteria) throws FunctionalException;

	/**
	 * Recherche le numéro de la dernière facture enregistrée en BDD
	 * @return
	 */
	String findLastNumFacture();

}
