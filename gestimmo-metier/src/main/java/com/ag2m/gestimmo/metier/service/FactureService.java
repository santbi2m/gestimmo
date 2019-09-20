package com.ag2m.gestimmo.metier.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

import com.ag2m.gestimmo.metier.dto.FactureDto;
import com.ag2m.gestimmo.metier.exception.FunctionalException;
import com.ag2m.gestimmo.metier.exception.TechnicalException;
import com.ag2m.gestimmo.metier.ioparam.FactureCriteria;

public interface FactureService {

	/**
	 * Retrouve l'entité dont l'id est passé en paramètre
	 * 
	 * @param id
	 * @return
	 * @throws FunctionalException 
	 * @throws TechnicalException 
	 */
	public FactureDto findFactureById(Long id) throws FunctionalException, TechnicalException;
	
	/**
	 * Retourne une liste d'entités
	 * 
	 * @return
	 */
	public List<FactureDto> loadAllFactures();
	
	/**
	 * Créer l'entité en paramètre
	 * 
	 * @param entite
	 * @return
	 * @throws FunctionalException 
	 * @throws TechnicalException 
	 */
	public FactureDto createFacture(FactureDto entite) throws FunctionalException, TechnicalException;
	
	/**
	 * Permet de générer une facture au format PDF
	 * 
	 * @param entite
	 * @return
	 * @throws FunctionalException 
	 * @throws TechnicalException 
	 */
	public ByteArrayOutputStream genererFacture(FactureDto entite) throws FunctionalException, TechnicalException;
	
	/**
	 *  Permet de combiner tous les critères possibles
	 *  de recherche de facture, de retourner 
	 *  le résultat et de le mettre dans le cache.
	 * @param factureCriteria
	 * @return
	 */
	List<FactureDto> findFactureByCriteria(FactureCriteria factureCriteria);
	
}
