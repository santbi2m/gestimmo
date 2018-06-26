package com.ag2m.gestimmo.metier.dao;

import org.joda.time.LocalDateTime;
import java.util.List;
import com.ag2m.gestimmo.metier.entite.Anomalie;
import com.ag2m.gestimmo.metier.exception.FunctionalException;

/**
 * DAO contenant les services d'accés à la base de données 
 * pour la gestion desnomalies. 
 * @author mombaye
 *
 */
public interface AnomalieDao extends CommonDao<Long, Anomalie> {
	/**
	 *  Permet de combiner tous les critères possibles
	 *  de recherche d’anomalie, de retourner 
	 *  le résultat et le met dans le cache.
	 *  
	 * @param titre
	 * @param statut
	 * @param dateOuverture
	 * @param dateTraitement
	 * @param idAppartement
	 * @return
	 * @throws FunctionalException 
	 */
	
	
	List<Anomalie> findAnomalieByCriteria(String titre, 
			String statut, LocalDateTime dateOuverture, LocalDateTime dateTraitement,
			Long idAppartement);
	
}
