package com.ag2m.gestimmo.metier.service;

import java.util.List;

import com.ag2m.gestimmo.metier.dto.ClientDto;
import com.ag2m.gestimmo.metier.exception.FunctionalException;

public interface ClientService {

	/**
	 * Retrouve l'entité dont l'id est passé en paramètre
	 * 
	 * @param id
	 * @return
	 * @throws FunctionalException 
	 */
	public ClientDto findClientById(Long id) throws FunctionalException;
	
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
	 * @throws FunctionalException 
	 */
	public  ClientDto createClient(ClientDto clientDto) throws FunctionalException;
	
	
	/**
	 * Met à jour l'entité en paramètre
	 * 
	 * @param entite
	 * @return
	 * @throws FunctionalException 
	 */
	ClientDto updateClient(ClientDto clientDto) throws FunctionalException;
	
	/**
	 * Permet de supprimer l'entité en entrée
	 * 
	 * @param entite
	 * @return
	 * @throws FunctionalException 
	 */
	public boolean deleteClient(ClientDto entite) throws FunctionalException;
	/**
	 *  Permet de combiner tous les critères possibles
	 *  de recherche de client, de retourner 
	 *  le résultat et de le mettre dans le cache.
	 * @param nom
	 * @param prenom
	 * @param adresseEmail
	 * @param numeroPiece
	 * @param typePiece
	 * @param telephone
	 * @param adresse
	 * @param complement
	 * @param codePostal
	 * @param ville
	 * @param pays
	 * @return
	 * @throws FunctionalException
	 */
	List<ClientDto> findClientByCriteria(String nom, 
			String prenom, String adresseEmail, String numeroPiece, String typePiece, String telephone,
			String adresse, String complement, Integer codePostal, String ville, String pays) throws FunctionalException;

}
