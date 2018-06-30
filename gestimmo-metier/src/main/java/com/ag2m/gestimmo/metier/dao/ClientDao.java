package com.ag2m.gestimmo.metier.dao;

import java.util.List;

import com.ag2m.gestimmo.metier.entite.Client;
import com.ag2m.gestimmo.metier.exception.FunctionalException;
import com.ag2m.gestimmo.metier.ioparam.ClientCriteria;

/**
 * @author mombaye
 *
 * Dao pour Client
 */
public interface ClientDao extends CommonDao<Long, Client> {

	/**
	 *  Permet de combiner tous les critères possibles
	 *  de recherche de client, de retourner 
	 *  le résultat et de le mettre dans le cache.
	 * @param clientCriteria
	 * @return
	 * @throws FunctionalException
	 */
	List<Client> findClientByCriteria(ClientCriteria clientCriteria) throws FunctionalException;

}
