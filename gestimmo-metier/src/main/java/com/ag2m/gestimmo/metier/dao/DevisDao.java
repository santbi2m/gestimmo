package com.ag2m.gestimmo.metier.dao;

import java.util.List;

import com.ag2m.gestimmo.metier.entite.Devis;
import com.ag2m.gestimmo.metier.exception.FunctionalException;
import com.ag2m.gestimmo.metier.exception.TechnicalException;
import com.ag2m.gestimmo.metier.ioparam.CommonFactureCriteria;

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

	/**
	 * <p>
	 * Permet de combiner tous les critères possibles
	 * de recherche de devis et de retourner les
	 * devis éligibles.
	 * </p>
	 * 
	 * <p>
	 * @param devisCriteria
	 * @return
	 * @throws FunctionalException
	 * @throws TechnicalException
	 * </p>
	 */
	List<Devis> findDevisByCriteria(CommonFactureCriteria devisCriteria);

}
