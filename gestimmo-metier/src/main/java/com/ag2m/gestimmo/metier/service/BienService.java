package com.ag2m.gestimmo.metier.service;

import java.util.List;

import com.ag2m.gestimmo.metier.dto.BienDto;
import com.ag2m.gestimmo.metier.exception.TechnicalException;
import com.ag2m.gestimmo.metier.ioparam.BienCriteria;

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
	 * @throws TechnicalException 
	 */
	public BienDto findBienById(Long id) throws TechnicalException;
	
	/**
	 * Retourne une liste d'entités
	 * 
	 * @return
	 */
	public List<BienDto> loadAllBien();
	
	/**
	 * Sauvegarde ou l'entité en paramètre
	 * 
	 * @param entite
	 * @return
	 * @throws TechnicalException 
	 */
	public  BienDto createBien(BienDto bienDto) throws TechnicalException;
	
	
	/**
	 * Met à jour l'entité en paramètre
	 * 
	 * @param entite
	 * @return
	 * @throws TechnicalException 
	 */
	BienDto updateBien(BienDto bienDt) throws TechnicalException;
	
	/**
	 * Permet de supprimer l'entité en entrée
	 * 
	 * @param entite
	 * @return
	 * @throws TechnicalException 
	 */
	public boolean deleteBien(BienDto bienDto) throws TechnicalException;
	
	/**
	 *  Permet de combiner tous les critères possibles
	 *  de recherche d’appartements, de retourner 
	 *  le résultat et de le mettre dans le cache.
	 * @param bienCriteria
	 * @return
	 */
	List<BienDto> findBienByCriteria(BienCriteria bienCriteria);

}
