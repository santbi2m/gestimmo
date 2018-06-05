package com.ag2m.gestimmo.metier.dao;

import java.util.List;

import com.ag2m.gestimmo.metier.dto.ClientDto;
import com.ag2m.gestimmo.metier.entite.Client;
import com.ag2m.gestimmo.metier.exception.FunctionalException;

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
	List<Client> findClientByCriteria(String nom, 
			String prenom, String adresseEmail, String numeroPiece, String typePiece, String telephone,
			String adresse, String complement, Integer codePostal, String ville, String pays) throws FunctionalException;

}
