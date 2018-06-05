package com.ag2m.gestimmo.metier.serviceImpl;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ag2m.gestimmo.metier.constants.MessageErrorConstants;
import com.ag2m.gestimmo.metier.dao.ClientDao;
import com.ag2m.gestimmo.metier.dto.BienDto;
import com.ag2m.gestimmo.metier.dto.ClientDto;
import com.ag2m.gestimmo.metier.entite.Bien;
import com.ag2m.gestimmo.metier.entite.Client;
import com.ag2m.gestimmo.metier.exception.FunctionalException;
import com.ag2m.gestimmo.metier.mapper.Mapper;
import com.ag2m.gestimmo.metier.service.ClientService;

import lombok.extern.log4j.Log4j;

@Service("clientService")
@Log4j
public class ClientServiceImpl implements ClientService {

	@Autowired
	ClientDao clientDao;
	
	@Autowired
	Mapper mapper;
	
	@Transactional(readOnly = true)
	public ClientDto findClientById(Long id) throws FunctionalException {
		
		Client client = null;
		ClientDto clientDto = null;

		log.info("Methode de recherche de client par id " + id);
		if (id == null) {
			throw new FunctionalException(MessageErrorConstants.ERREUR_ID_NULL);
		}
		try {

			client = clientDao.findById(Client.class, id);

			if (client != null) {
				clientDto = mapper.clientToClientDto(client);
			}

		} catch (Exception e) {
			StringBuilder message = new StringBuilder(MessageErrorConstants.ERREUR_AU_CHARGEMENT);
			message.append(id);
			log.error(message.toString(), e);
		}
		return clientDto;
	}

	@Transactional(readOnly = true)
	public List<ClientDto> loadAllClient() {
		List<Client> results = clientDao.findAll(Client.class);
		return results.stream().map(client -> 
			mapper.clientToClientDto(client))
				.collect(Collectors.<ClientDto> toList());
	}

	

	@Transactional
	public boolean deleteClient(ClientDto entiteDto) throws FunctionalException {
		
		log.debug("Suppression Client");

		boolean result = false;

		// Bien à supprimer ne peut pas être null
		if (entiteDto == null) {
			throw new FunctionalException(MessageErrorConstants.ERREUR_ENTREE_SUPP_NULL);
		}

		// Transformation en entité Bien
		Client entite = mapper.clientDtoToClient(entiteDto);

		// Appel du service
		try {
			result = clientDao.delete(entite);
		} catch (Exception e) {
			log.error(MessageErrorConstants.ERREUR_A_LA_SUPPRESSION, e);
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see com.ag2m.gestimmo.metier.service.ClientService#createClient(com.ag2m.gestimmo.metier.dto.ClientDto)
	 */
	@Override
	@Transactional
	public ClientDto createClient(ClientDto clientDto) throws FunctionalException {
		log.debug("Creation client");

		ClientDto cDto = null;
		// Le client à créer ne peut pas être null
		if (clientDto == null) {
			throw new FunctionalException(MessageErrorConstants.ERREUR_ENTREE_CREATION_NULL);
		}

		// map and save
		return mapAndSave(clientDto, cDto);
	}

	/*
	 * (non-Javadoc)
	 * @see com.ag2m.gestimmo.metier.service.ClientService#updateClient(com.ag2m.gestimmo.metier.dto.ClientDto)
	 */
	@Override
	@Transactional
	public ClientDto updateClient(ClientDto clientDto) throws FunctionalException {
		log.debug("Mise à jour Bien");

		ClientDto result = null;
		// Le client à modifier doit exister en BDD
		if (clientDto == null || clientDto.getId() == null) {
			throw new FunctionalException(MessageErrorConstants.ERREUR_ENTREE_MODIFICATION_NULL);
		}

		// map and save
		return mapAndSave(clientDto, result);
	}

	/*
	 * (non-Javadoc)
	 * @see com.ag2m.gestimmo.metier.service.ClientService#findClientByCriteria(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Integer, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	public List<ClientDto> findClientByCriteria(String nom, String prenom, String adresseEmail, String numeroPiece,
			String typePiece, String telephone, String adresse, String complement, Integer codePostal, String ville,
			String pays) throws FunctionalException {
		log.debug("Recherche Par critere ce client");
		
		List<ClientDto> listeClientDto = new ArrayList<>();
		
		try {
		//Chargement des biens en fonction des critères d'entrée.
		List<Client> clients = clientDao.findClientByCriteria(nom, prenom, adresseEmail, numeroPiece, typePiece, telephone, adresse, complement, codePostal, ville, pays);
		//Transformation de tous les biens en BienDto
		listeClientDto = clients.stream()
							.map(client 
							-> mapper.clientToClientDto(client))
							.collect(Collectors.<ClientDto> toList());
		} catch (Exception e) {
			log.error(MessageErrorConstants.ERREUR_BDD  , e);
		}
		
		return listeClientDto;
	}
	
	private ClientDto mapAndSave(ClientDto clientDto, ClientDto cDto) {
		try {
			// Transformation en entité Bien
			Client client = mapper.clientDtoToClient(clientDto);
			// Appel du service
			clientDao.saveOrUpdate(client);
			cDto = mapper.clientToClientDto(client);

		} catch (Exception e) {
			log.error(MessageErrorConstants.ERREUR_A_LA_SAUVEGARDE, e);
		}

		return cDto;
	}

}
