package com.ag2m.gestimmo.metier.service;

import java.util.List;

import com.ag2m.gestimmo.metier.dto.FactureDto;
import com.ag2m.gestimmo.metier.exception.TechnicalException;

public interface FactureService {

	/**
	 * Retrouve l'entité dont l'id est passé en paramètre
	 * 
	 * @param id
	 * @return
	 */
	public FactureDto findById(Long id);
	
	/**
	 * Retourne une liste d'entités
	 * 
	 * @return
	 */
	public List<FactureDto> findAll();
	
	/**
	 * Sauvegarde ou met à jour l'entité en paramètre
	 * 
	 * @param entite
	 * @return
	 * @throws TechnicalException 
	 */
	public FactureDto saveOrUpdate(FactureDto entite) throws TechnicalException;
	
	/**
	 * Permet de supprimer l'entité en entrée
	 * 
	 * @param entite
	 * @return
	 */
	public boolean delete(FactureDto entite);
}
