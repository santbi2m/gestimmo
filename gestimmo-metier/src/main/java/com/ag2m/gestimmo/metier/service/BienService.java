package com.ag2m.gestimmo.metier.service;

import java.util.List;

import com.ag2m.gestimmo.metier.dto.BienDto;
import com.ag2m.gestimmo.metier.exception.FunctionalException;

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
	 * @throws FunctionalException 
	 */
	public BienDto findBienById(Long id) throws FunctionalException;
	
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
	 * @throws FunctionalException 
	 */
	public  BienDto createBien(BienDto bienDto) throws FunctionalException;
	
	
	/**
	 * Met à jour l'entité en paramètre
	 * 
	 * @param entite
	 * @return
	 * @throws FunctionalException 
	 */
	BienDto updateBien(BienDto bienDt) throws FunctionalException;
	
	/**
	 * Permet de supprimer l'entité en entrée
	 * 
	 * @param entite
	 * @return
	 * @throws FunctionalException 
	 */
	public boolean deleteBien(BienDto bienDto) throws FunctionalException;
	
	/**
	 *  Permet de combiner tous les critères possibles
	 *  de recherche d’appartements, de retourner 
	 *  le résultat et de le mettre dans le cache.
	 *  
	 * @param libelle
	 * @param adresse
	 * @param complement
	 * @param codePostal
	 * @param ville
	 * @param pays
	 * @return
	 * @throws FunctionalException
	 */
	List<BienDto> findBienByCriteria(String libelle, 
			String adresse, String complement, Integer codePostal, String ville, String pays) throws FunctionalException;

}
