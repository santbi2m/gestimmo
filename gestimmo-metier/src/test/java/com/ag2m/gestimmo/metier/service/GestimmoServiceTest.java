package com.ag2m.gestimmo.metier.service;

import java.util.List;

import com.ag2m.gestimmo.metier.dto.DevisDto;
import com.ag2m.gestimmo.metier.exception.FunctionalException;
import com.ag2m.gestimmo.metier.exception.TechnicalException;
import com.ag2m.gestimmo.metier.ioparam.CommonFactureCriteria;

public interface GestimmoServiceTest {

	/**
	 * Retrouve l'entité dont l'id est passé en paramètre
	 * 
	 * @param id
	 * @return
	 */
	DevisDto findDevisById(Long id) throws TechnicalException;
	
	
	/**
	 * Retourne une liste d'entités
	 * 
	 * @return tous les devis en BDD
	 */
	List<DevisDto> findAllDevis();
	
	/**
	 * Permet de supprimer l'entité en entrée
	 * 
	 * @param entite
	 * @return
	 * @throws TechnicalException 
	 */
	boolean deleteDevis(DevisDto entiteDto) throws TechnicalException;

	
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
	List<DevisDto> findDevisByCriteria(CommonFactureCriteria devisCriteria) throws FunctionalException, TechnicalException;

}
