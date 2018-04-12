/**
 * 
 */
package com.ag2m.gestimmo.metier.service;

import java.util.List;

import com.ag2m.gestimmo.metier.dto.ReservationDto;

/**
 * @author mombaye
 *
 */
public interface ReservationService {

	/**
	 * Retrouve l'entité dont l'id est passé en paramètre
	 * 
	 * @param id
	 * @return
	 */
	public ReservationDto findById(Long id);
	
	/**
	 * Retourne une liste d'entités
	 * 
	 * @return
	 */
	public List<ReservationDto> findAll();
	
	/**
	 * Sauvegarde ou met à jour l'entité en paramètre
	 * 
	 * @param entite
	 * @return
	 */
	public ReservationDto saveOrUpdate(ReservationDto entite);
	
	/**
	 * Permet de supprimer l'entité en entrée
	 * 
	 * @param entite
	 * @return
	 */
	public boolean delete(ReservationDto entite);
}
