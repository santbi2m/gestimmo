package com.ag2m.gestimmo.metier.service;

import java.util.List;

import com.ag2m.gestimmo.metier.dto.ClientDto;
import com.ag2m.gestimmo.metier.exception.TechnicalException;
import com.ag2m.gestimmo.metier.ioparam.ClientCriteria;

public interface ClientService {

	/**
	 * Retrouve l'entité dont l'id est passé en paramètre
	 * 
	 * @param id
	 * @return
	 * @throws TechnicalException 
	 */
	public ClientDto findClientById(Long id) throws TechnicalException;
	
	/**
	 * Retourne une liste d'entités
	 * 
	 * @return
	 */
	public List<ClientDto> loadAllClient();
	
	/**
	 * Sauvegarde ou l'entité en paramètre
	 * 
	 * @param entite
	 * @return
	 * @throws TechnicalException 
	 */
	public  ClientDto createClient(ClientDto clientDto) throws TechnicalException;
	
	
	/**
	 * Met à jour l'entité en paramètre
	 * 
	 * @param entite
	 * @return
	 * @throws TechnicalException 
	 */
	ClientDto updateClient(ClientDto clientDto) throws TechnicalException;
	
	/**
	 * Permet de supprimer l'entité en entrée
	 * 
	 * @param entite
	 * @return
	 * @throws TechnicalException 
	 */
	public boolean deleteClient(ClientDto entite) throws TechnicalException;
	/**
	 *  Permet de combiner tous les critères possibles
	 *  de recherche de client, de retourner 
	 *  le résultat et de le mettre dans le cache.
	 * @param clientCriteria
	 * @return
	 * @throws TechnicalException
	 */
	List<ClientDto> findClientByCriteria(ClientCriteria clientCriteria) throws TechnicalException;

}
