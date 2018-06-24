package com.ag2m.gestimmo.metier.serviceImpl;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ag2m.gestimmo.metier.constants.TechnicalErrorMessageConstants;
import com.ag2m.gestimmo.metier.dao.ClientDao;
import com.ag2m.gestimmo.metier.dto.ClientDto;
import com.ag2m.gestimmo.metier.entite.Client;
import com.ag2m.gestimmo.metier.exception.TechnicalException;
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
	public ClientDto findClientById(Long id) throws TechnicalException {
		
		log.info("Methode de recherche de client par id " + id);
		
		if (id == null) {
			throw new TechnicalException(TechnicalErrorMessageConstants.ERREUR_ID_NULL);
		}

		final Client client = clientDao.findById(Client.class, id);
	
		if (client != null) {
			return mapper.clientToClientDto(client);
		}
		return null;
	}

	@Transactional(readOnly = true)
	public List<ClientDto> loadAllClient() {
		
		List<Client> results = clientDao.findAll(Client.class);
		
		return results.stream().map(client -> 
			mapper.clientToClientDto(client))
				.collect(Collectors.<ClientDto> toList());
	}

	

	@Transactional
	public boolean deleteClient(ClientDto entiteDto) throws TechnicalException {
		
		log.debug("Suppression Client");

		// Bien à supprimer ne peut pas être null
		if (entiteDto == null) {
			throw new TechnicalException(TechnicalErrorMessageConstants.ERREUR_ENTREE_SUPP_NULL);
		}
		// Transformation en entité Bien
		Client entite = mapper.clientDtoToClient(entiteDto);
		// Appel du service
		return clientDao.delete(entite);
	}

	/*
	 * (non-Javadoc)
	 * @see com.ag2m.gestimmo.metier.service.ClientService#createClient(com.ag2m.gestimmo.metier.dto.ClientDto)
	 */
	@Override
	@Transactional
	public ClientDto createClient(ClientDto clientDto) throws TechnicalException {
		
		log.debug("Creation client");
		// Le client à créer ne peut pas être null
		if (clientDto == null) {
			throw new TechnicalException(TechnicalErrorMessageConstants.ERREUR_ENTREE_CREATION_NULL);
		}
		// map and save
		return mapAndSave(clientDto);
	}

	/*
	 * (non-Javadoc)
	 * @see com.ag2m.gestimmo.metier.service.ClientService#updateClient(com.ag2m.gestimmo.metier.dto.ClientDto)
	 */
	@Override
	@Transactional
	public ClientDto updateClient(ClientDto clientDto) throws TechnicalException {
	
		log.debug("Mise à jour Client");
		// Le client à modifier doit exister en BDD
		if (clientDto == null || clientDto.getId() == null) {
			throw new TechnicalException(TechnicalErrorMessageConstants.ERREUR_ENTREE_MODIFICATION_NULL);
		}
		// map and save
		return mapAndSave(clientDto);
	}

	/*
	 * (non-Javadoc)
	 * @see com.ag2m.gestimmo.metier.service.ClientService#findClientByCriteria(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Integer, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	public List<ClientDto> findClientByCriteria(String nom, String prenom, String adresseEmail, String numeroPiece,
			String typePiece, String telephone, String adresse, String complement, Integer codePostal, String ville,
			String pays) throws TechnicalException {
		
		log.debug("Recherche Par critere ce client");
		//Chargement des biens en fonction des critères d'entrée.
		List<Client> clients = clientDao.findClientByCriteria(nom, prenom, adresseEmail, numeroPiece, typePiece, telephone, adresse, complement, codePostal, ville, pays);
		//Transformation de tous les biens en BienDto
		return clients.stream()
							.map(client 
							-> mapper.clientToClientDto(client))
							.collect(Collectors.<ClientDto> toList());
	}
	
	/**
	 * Permet de faire le mapping dto - entite 
	 * puis sauvegarde l'entité
	 * 
	 * @param clientDto
	 * @return
	 */
	private ClientDto mapAndSave(ClientDto clientDto) {
			// Transformation en entité Bien
			Client client = mapper.clientDtoToClient(clientDto);
			// Appel du service
			clientDao.saveOrUpdate(client);
			return mapper.clientToClientDto(client);

	}

}
