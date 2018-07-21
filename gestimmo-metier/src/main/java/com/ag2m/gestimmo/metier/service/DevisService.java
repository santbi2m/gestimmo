package com.ag2m.gestimmo.metier.service;

import com.ag2m.gestimmo.metier.dto.DevisDto;

public interface DevisService {

	/**
	 * Retrouve l'entité dont l'id est passé en paramètre
	 * 
	 * @param id
	 * @return
	 */
//	public DevisDto findById(Long id);
	
	/**
	 * Retourne une liste d'entités
	 * 
	 * @return
	 */
//	public List<DevisDto> findAll();
	
	/**
	 * Sauvegarde ou met à jour l'entité en paramètre
	 * 
	 * @param entite
	 * @return
	 */
	public DevisDto saveOrUpdate(DevisDto entite);
	
	/**
	 * Permet de supprimer l'entité en entrée
	 * 
	 * @param entite
	 * @return
	 */
//	public boolean delete(DevisDto entite);
}
