package com.ag2m.gestimmo.metier.dao;

import java.util.List;

import com.ag2m.gestimmo.metier.dto.BienDto;
import com.ag2m.gestimmo.metier.entite.Appartement;
import com.ag2m.gestimmo.metier.entite.Bien;
import com.ag2m.gestimmo.metier.exception.FunctionalException;

/**
 * 
 * @author mombaye
 *
 */
public interface BienDao extends CommonDao<Long, Bien> {
	
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
	List<Bien> findBienByCriteria(String libelle, 
			String adresse, String complement, Integer codePostal, String ville, String pays) throws FunctionalException;

}
