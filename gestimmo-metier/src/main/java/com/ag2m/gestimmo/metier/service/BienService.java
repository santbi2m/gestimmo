package com.ag2m.gestimmo.metier.service;

import java.util.List;

import com.ag2m.gestimmo.metier.dto.BienDto;

/**
 * 
 * @author mombaye
 *
 */
public interface BienService {

	/**
	 * Retrouve l'entité dont l'id est passé en paramètre
	 * 
	 * @param id
	 * @return
	 */
	public BienDto findById(Long id);
	
	/**
	 * Retourne une liste d'entités
	 * 
	 * @return
	 */
	public List<BienDto> findAll();
	
	/**
	 * Sauvegarde ou met à jour l'entité correspondant au DTO en paramètre
	 * 
	 * @param entite
	 * @return
	 */
	public BienDto saveOrUpdate(BienDto entite);
	
	/**
	 * Permet de supprimer l'entité correspondant au DTO en entrée
	 * 
	 * @param entite
	 * @return
	 */
	public boolean delete(BienDto entite);
}
