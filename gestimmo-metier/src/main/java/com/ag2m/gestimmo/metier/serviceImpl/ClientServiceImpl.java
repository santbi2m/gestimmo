package com.ag2m.gestimmo.metier.serviceImpl;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ag2m.gestimmo.metier.dao.ClientDao;
import com.ag2m.gestimmo.metier.dto.ClientDto;
import com.ag2m.gestimmo.metier.entite.Client;
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
	public ClientDto findById(Long id) {
		
		//TODO Ajouter les logs
		// ----------   Exemple d'utilisation du logger avec lombok   ---------- 
		//		log.info(message);
		
		Client client =  clientDao.findById(Client.class, id);
		ClientDto clientDto = mapper.clientToClientDto(client);
		return clientDto;
	}

	@Transactional(readOnly = true)
	public List<ClientDto> findAll() {
		List<Client> results = clientDao.findAll(Client.class);
		return results.stream().map(client -> 
			mapper.clientToClientDto(client))
				.collect(Collectors.<ClientDto> toList());
	}

	@Transactional
	public ClientDto saveOrUpdate(ClientDto entiteDto) {
		Client entite = mapper.clientDtoToClient(entiteDto);
		clientDao.saveOrUpdate(entite);
		return mapper.clientToClientDto(entite);
	}

	@Transactional
	public boolean delete(ClientDto entiteDto) {
		Client entite = mapper.clientDtoToClient(entiteDto);
		return clientDao.delete(entite);
	}

}
