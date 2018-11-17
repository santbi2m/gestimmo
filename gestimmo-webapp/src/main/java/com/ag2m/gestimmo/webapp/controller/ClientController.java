package com.ag2m.gestimmo.webapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ag2m.gestimmo.metier.dto.ClientDto;
import com.ag2m.gestimmo.metier.exception.FunctionalException;
import com.ag2m.gestimmo.metier.exception.TechnicalException;
import com.ag2m.gestimmo.metier.service.ClientService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class ClientController {

	@Autowired
	private ClientService clientService;

	/**
	 * Retourne toutes les clients sous format Json
	 * 
	 * @return
	 */
	@RequestMapping(value = "/clients", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<ClientDto> findAllClient() {
		List<ClientDto> clients = clientService.loadAllClient();
		return clients;
	}

	/**
	 * Retourne le client dont l'id est en paramètre, sous format Json
	 * 
	 * @return
	 * @throws FunctionalException
	 * @throws TechnicalException
	 */
	@RequestMapping(value = "/clients/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ClientDto getClient(@PathVariable("id") long id)
			throws FunctionalException, TechnicalException {

		ClientDto client = clientService.findClientById(id);

		return client;
	}

	/**
	 * Crée un nouvel client
	 * 
	 */
	// @RequestMapping(value = "/anomalies/save/idBien/{id}", method =
	// RequestMethod.POST)
	// public @ResponseBody void saveAppartement(@PathVariable("id") long
	// idBien) {
	//
	// Bien bien = anomalieService.findById(idBien);
	// Appartement appartement = new Appartement();
	// appartement.setLibelle("Gokhou Mbath");
	// appartement.setBien(bien);
	// appartement.setType(EnumTypeAppartement.T2.getType());
	// appartementService.saveOrUpdate(appartement);
	//
	// }

	/**
	 * supprime l'appartement dont l'id est en paramètre
	 * 
	 */
	// @RequestMapping(value = "/anomalies/delete/id/{id}", method =
	// RequestMethod.DELETE)
	// public @ResponseBody void deleteAppartement(@PathVariable("id") long id)
	// {
	// Appartement appartement = appartementService.findById(id);
	// appartementService.delete(appartement);
	//
	// }

}
