package com.ag2m.gestimmo.metier.service;

import java.util.List;

import com.ag2m.gestimmo.metier.dto.AppartementDto;
import com.ag2m.gestimmo.metier.exception.FunctionalException;

/**
 * Business service contenant
 * les services métiers pour la 
 * gestion des appartements
 * 
 * @author mombaye
 *
 */
public interface AppartementService {

	/**
	 * Retrouve l'entité dont l'id est passé en paramètre
	 * 
	 * @param id
	 * @return
	 * @throws FunctionalException 
	 */
	public AppartementDto findAppartementById(Long id) throws FunctionalException;
	
	/**
	 * Retourne une liste d'entités
	 * 
	 * @return
	 */
	public List<AppartementDto> loadAllAppartement();
	
	/**
	 * Sauvegarde ou l'entité en paramètre
	 * 
	 * @param entite
	 * @return
	 * @throws FunctionalException 
	 */
	public AppartementDto createAppartement(AppartementDto entite) throws FunctionalException;
	
	
	/**
	 * Met à jour l'entité en paramètre
	 * 
	 * @param entite
	 * @return
	 * @throws FunctionalException 
	 */
	AppartementDto updateAppartement(AppartementDto entiteDto) throws FunctionalException;
	
	/**
	 * Permet de supprimer l'entité en entrée
	 * 
	 * @param entite
	 * @return
	 * @throws FunctionalException 
	 */
	public boolean deleteAppartement(AppartementDto entite) throws FunctionalException;
	
	
	/**
	 *  Permet de combiner tous les critères possibles
	 *  de recherche d’appartements
	 *  
	 * @param libelle
	 * @param type
	 * @param idBien
	 * @return
	 * @throws FunctionalException 
	 */
	List<AppartementDto> findAppartementByCriteria(String libelle, 
			String type, Long idBien) throws FunctionalException;
}
