package com.ag2m.gestimmo.metier.dao;

import java.util.List;

import com.ag2m.gestimmo.metier.entite.Appartement;
import com.ag2m.gestimmo.metier.exception.FunctionalException;

/**
 * Dao contenant les services
 * d'accès à la base de données
 * pour la gestion des appartements
 * 
 * @author mombaye
 *
 */
public interface AppartementDao extends CommonDao<Long, Appartement>{

	/**
	 *  Permet de combiner tous les critères possibles
	 *  de recherche d’appartements, de retourner 
	 *  le résultat et de le mettre dans le cache.
	 *  
	 * @param libelle
	 * @param type
	 * @param idBien
	 * @return
	 * @throws FunctionalException 
	 */
	List<Appartement> findAppartementByCriteria(String libelle, 
			String type, Long idBien);
}
