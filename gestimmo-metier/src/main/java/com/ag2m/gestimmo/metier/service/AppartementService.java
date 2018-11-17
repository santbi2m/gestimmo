package com.ag2m.gestimmo.metier.service;

import java.util.List;

import com.ag2m.gestimmo.metier.dto.AppartementDto;
import com.ag2m.gestimmo.metier.entite.Appartement;
import com.ag2m.gestimmo.metier.exception.TechnicalException;

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
	 * @throws TechnicalException 
	 */
	public AppartementDto findAppartementById(Long id) throws TechnicalException;
	
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
	 * @throws TechnicalException 
	 */
	public AppartementDto createAppartement(AppartementDto entite) throws TechnicalException;
	
	
	/**
	 * Met à jour l'entité en paramètre
	 * 
	 * @param entite
	 * @return
	 * @throws TechnicalException 
	 */
	AppartementDto updateAppartement(AppartementDto entiteDto) throws TechnicalException;
	
	/**
	 * Permet de supprimer l'entité en entrée
	 * 
	 * @param entite
	 * @return
	 * @throws TechnicalException 
	 */
	public boolean deleteAppartement(AppartementDto entite) throws TechnicalException;
	
	
	/**
	 *  Permet de combiner tous les critères possibles
	 *  de recherche d’appartements
	 *  
	 * @param libelle
	 * @param type
	 * @param idBien
	 * @return
	 */
	List<AppartementDto> findAppartementByCriteria(String libelle, 
			String type, Long idBien);
	
	/**
	 * Recherche les appartements liés 
	 * à la réservation passée en paramètres
	 * 
	 * @param idReservation
	 * @return
	 */
	List<AppartementDto> findAppartByReservation(Long idReservation);
}
