/**
 * 
 */
package com.ag2m.gestimmo.metier.serviceImpl;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.joda.time.LocalDateTime;
import org.junit.Test;

import com.ag2m.gestimmo.metier.dto.AdresseDto;
import com.ag2m.gestimmo.metier.dto.AppartementDto;
import com.ag2m.gestimmo.metier.dto.BienDto;
import com.ag2m.gestimmo.metier.dto.ClientDto;
import com.ag2m.gestimmo.metier.dto.ReservationDto;
import com.ag2m.gestimmo.metier.enumeration.EnumStatutReservation;
import com.ag2m.gestimmo.metier.enumeration.EnumTypeAppartement;
import com.ag2m.gestimmo.metier.exception.TechnicalException;
import com.ag2m.gestimmo.metier.ioparam.ClientCriteria;

/**
 * @author mombaye
 *
 */
public class ClientServiceImplTest extends AbstractCommonTest{

	

	@Test
	public void testloadAllClient() throws TechnicalException {

		//Adresse
		AdresseDto adresse = createAdresse("120 cité Azur", null, 9900, "Mermoz", "Sénégal");
		AdresseDto adresse2 = createAdresse("150 avenue Malick Sy", null, 9900, "Point E", "Sénégal");
		
		//Clients
		createClient("Boubakh", "Wadji", "wadji@gmail.com", "145ZERT", "passeport", "0625986550", adresse);
		createClient("Safal", "Adja", "adja@gmail.com", "425SD25", "CNI", "0748251540", adresse2);
		
		//Call services
		List<ClientDto> clients = clientService.loadAllClient();
		
		//Check results
		assertThat(clients, is(notNullValue()));
		assertThat(clients.size(), greaterThanOrEqualTo(2));
		
		//Check cache
		assertThat(cacheManager.getObject().getCache("gestimmo").getSize(), greaterThanOrEqualTo(2));
		
	}

	@Test
	public void testCreateClient() throws TechnicalException {
		
		int oldSize = cacheManager.getObject().getCache("gestimmo").getSize();
		
		// Adresse
		AdresseDto adresse = createAdresse("124 cité promocap", "2ème porte", 9900, "Petit Mbao", "Sénégal");
		
		//Client
		ClientDto client = createClient("Boubakh", "Wadji", "wadji@gmail.com", "145ZERT", "passeport", "0625986550", adresse);
		
		//Call service
		client = clientService.findClientById(client.getId());
		
		//Check result
		assertThat(client.getId(), is(notNullValue()));
		
		//Check cache
		int newSize = cacheManager.getObject().getCache("gestimmo").getSize();
		assertThat(newSize, greaterThan(oldSize));
	}
	
	@Test
	public void testUpdateClient() throws TechnicalException {

		ClientDto client = clientService.findClientById(222L);
		//Check results
		assertThat(client, is(notNullValue()));
		assertThat(client.getId(), is(222L));
		
		//Call service for updating client
		client.setNom("Dieng");
		client.setPrenom("Moussa");
		client.setAdresseEmail("moussa@hotmail.fr");
		client = clientService.updateClient(client);
		
		//Check results
		assertThat(client.getId(), is(222L));
		assertThat(client.getNom(), is("Dieng"));
		assertThat(client.getPrenom(), is("Moussa"));
		assertThat(client.getAdresseEmail(), is("moussa@hotmail.fr"));
	}
	

	
	@Test
	public void testDeleteClient() throws TechnicalException {
		
		// Adresse
		AdresseDto adresse = createAdresse("124 cité promocap", "2ème porte", 9900, "Petit Mbao", "Sénégal");
		
		//Client
		ClientDto client = createClient("Boubakh", "Wadji", "wadji@gmail.com", "145ZERT", "passeport", "0625986550", adresse);
		
		//Check result
		assertThat(client.getId(), is(notNullValue()));
		
		//Call service
		ClientDto entite = clientService.findClientById(client.getId());
		//Check result
		assertThat(entite, is(notNullValue()));
		
		//Call services
		clientService.deleteClient(entite);
		entite = clientService.findClientById(client.getId());
		
		//Check result
		assertThat(entite, is(nullValue()));
		
	}	
	
	
	@Test
	public void testDeleteClientWithAppart() throws TechnicalException {
		
		// Adresse
		AdresseDto adresse = createAdresse("12 cité Fadia", null, 9900, "Sacré coeur", "Sénégal");
				
		//Client
		ClientDto client = createClient("Boubakh", "Wadji", "wadji@gmail.com", "145ZERT", "passeport", "0625986550", adresse);
		
		// Bien
		BienDto bien = createBien("Wakeur Meissa", adresse);
		//Appartements
		AppartementDto app1 = createAppartement("Dalal Diam", bien, EnumTypeAppartement.T2.getType(), 50D);
		
		//Reservations
		ReservationDto reservation1 = createReservation(new LocalDateTime(), new LocalDateTime().plusDays(10), "Avec lit bébé svp", 
				true, EnumStatutReservation.ENREGISTREE.getStatut(), Arrays.asList(app1),
				50D, new LocalDateTime(), null, client, null);

		ReservationDto reservation2 = createReservation(new LocalDateTime(), new LocalDateTime().plusDays(3), "RAS", 
		true, EnumStatutReservation.CONFIRMEE.getStatut(), Arrays.asList(app1), 
		120D, new LocalDateTime(), null, client, null);


		
		//Call Services
		clientService.deleteClient(client);
		client = clientService.findClientById(client.getId());
		reservation1 = reservationService.findReservationById(reservation1.getId());
		reservation2 = reservationService.findReservationById(reservation2.getId());
		
		//Check results
		assertThat(client, is(nullValue()));
		assertThat(reservation1, is(nullValue()));
		assertThat(reservation2, is(nullValue()));
	}
	
	/**
	 * Tester le service testFindClientByCriteriaAllCriteriaNull
	 * sans critères d'entrée
	 * 
	 * @throws TechnicalException 
	 */

	@Test
	public void testFindClientByCriteriaAllCriteriaNull() throws TechnicalException {
		ClientCriteria clientCriteria = new ClientCriteria();
		//Adresse
		AdresseDto adresse = createAdresse("12 cité Fadia", null, 9900, "Sacré coeur", "Sénégal");
		
		//Client
		createClient("Boubakh", "Wadji", "wadji@gmail.com", "145ZERT", "passeport", "0625986550", adresse);
		createClient("Safal", "Adja", "adja@gmail.com", "425SD25", "CNI", "0748251540", adresse);
		
		
		//Call service
		clientCriteria = new ClientCriteria();;
		List<ClientDto> result = clientService.findClientByCriteria(clientCriteria);
		//Check
		assertThat(result, is(notNullValue()));
		assertThat(result, is(not(empty())));
		assertThat(result.size(), is(greaterThanOrEqualTo(2)));
	}
	
	/**
	 * Tester le service testFindClientByCriteria
	 * avec le libellé comme critère d'entrée
	 * 
	 * @throws TechnicalException 
	 */

	@Test
	public void testFindClientByCriteria() throws TechnicalException {
		ClientCriteria clientCriteria = new ClientCriteria();
		// Adresse
		AdresseDto adresse = createAdresse("12 cité Fadia", null, 9900, "Sacré coeur", "Sénégal");

		// Client
		createClient("Boubakh", "Wadji", "wadji@gmail.com", "145ZERT", "passeport", "0625986550", adresse);
		createClient("Safal", "Adja", "adja@gmail.com", "425SD25", "CNI", "0748251540", adresse);

		/************
		 * ************ Case parametter repected * *
		 ************/
		// Call service
		clientCriteria.setNom("Boubakh");
		List<ClientDto> result = clientService.findClientByCriteria(clientCriteria);
		// Check
		assertThat(result, is(notNullValue()));
		assertThat(result, is(not(empty())));
		assertThat(result.size(), is(greaterThanOrEqualTo(1)));

		result.forEach(app -> {
			assertThat(app.getNom(), is("Boubakh"));
		});

		/************
		 * ************ Parametter in Lower case * *
		 ************/
		// Call service
		clientCriteria = new ClientCriteria();
		clientCriteria.setPrenom("Adja");
		result = clientService.findClientByCriteria(clientCriteria);

		// Check
		assertThat(result, is(notNullValue()));
		assertThat(result, is(not(empty())));
		assertThat(result.size(), is(greaterThanOrEqualTo(1)));

		result.forEach(app -> {
			assertThat(app.getPrenom(), is("Adja"));
		});

		/************
		 * ************ Parametter in any case * *
		 ************/
		clientCriteria = new ClientCriteria();
		clientCriteria.setAdresseEmail("adja@gmail.com");;
		result = clientService.findClientByCriteria(clientCriteria);

		// Check
		assertThat(result, is(notNullValue()));
		assertThat(result, is(not(empty())));
		assertThat(result.size(), is(greaterThanOrEqualTo(1)));

		result.forEach(app -> {
			assertThat(app.getNom(), is("Safal"));
			assertThat(app.getPrenom(), is("Adja"));
			assertThat(app.getAdresseEmail(), is("adja@gmail.com"));
		});
		
		/************
		 * ************ Parametter in any case * *
		 ************/
		clientCriteria = new ClientCriteria();
		clientCriteria.setNom("Boubakh");
		result = clientService.findClientByCriteria(clientCriteria);

		// Check
		assertThat(result, is(notNullValue()));
		assertThat(result, is(not(empty())));
		assertThat(result.size(), is(greaterThanOrEqualTo(1)));

		result.forEach(app -> {
			assertThat(app.getNom(), is("Boubakh"));
			assertThat(app.getPrenom(), is("Wadji"));
			assertThat(app.getNumeroPieceIdentite(), is("145ZERT"));
		});
		
		clientCriteria = new ClientCriteria();
		clientCriteria.setTypePiece("CNI");;
		result = clientService.findClientByCriteria(clientCriteria);

		// Check
		assertThat(result, is(notNullValue()));
		assertThat(result, is(not(empty())));
		assertThat(result.size(), is(greaterThanOrEqualTo(1)));

		result.forEach(app -> {
			assertThat(app.getTypePieceIdentite(), is("CNI"));
		});
	}
	
	/**
	 * Tester le service testFindClientByCriteriaAdresse
	 * avec les champs dde l' adresse comme critère d'entrée
	 * 
	 * @throws TechnicalException 
	 */

	@Test
	public void testFindClientByCriteriaAdresse() throws TechnicalException {

		ClientCriteria clientCriteria = new ClientCriteria();
		// Adresse
		AdresseDto adresse = createAdresse("12 cité Fadia", null, 9900, "Sacré coeur", "Sénégal");
		assertThat(adresse.getId(), is(notNullValue()));
		// Client
		createClient("Boubakh", "Wadji", "wadji@gmail.com", "145ZERT", "passeport", "0625986550", adresse);



		/************
		 * ************ Case parametter repected * *
		 ************/
		// Call service
		clientCriteria.setAdresse(adresse.getAdresse());
		List<ClientDto> result = clientService.findClientByCriteria(clientCriteria);
		// Check
		assertThat(result, is(notNullValue()));
		assertThat(result, is(not(empty())));
		assertThat(result.size(), is(greaterThanOrEqualTo(1)));

		result.forEach(app -> {
			assertThat(app.getAdresse().getId(), is(notNullValue()));
			assertThat(app.getAdresse().getAdresse(), is("12 cité Fadia"));
		});

		/************
		 * ************ Parametter in Lower case * *
		 ************/
		// Call service
		clientCriteria = new ClientCriteria();
		clientCriteria.setCodePostal(9900);
		result = clientService.findClientByCriteria(clientCriteria);
		// Check
		assertThat(result, is(notNullValue()));
		assertThat(result, is(not(empty())));
		assertThat(result.size(), is(greaterThanOrEqualTo(1)));

		result.forEach(app -> {
			assertThat(app.getId(), is(notNullValue()));
			assertThat(app.getAdresse().getCodePostal(), is(9900));
		});

		/************
		 * ************ Parametter in any case * *
		 ************/
		clientCriteria = new ClientCriteria();
		clientCriteria.setVille("Sacré coeur");
		result = clientService.findClientByCriteria(clientCriteria);
		// Check
		assertThat(result, is(notNullValue()));
		assertThat(result, is(not(empty())));
		assertThat(result.size(), is(greaterThanOrEqualTo(1)));

		result.forEach(app -> {
			assertThat(app.getAdresse().getId(), is(notNullValue()));
			assertThat(app.getAdresse().getVille(), is("Sacré coeur"));
		});
		
		/************
		 * ************ Parametter in any case * *
		 ************/
		clientCriteria = new ClientCriteria();
		clientCriteria.setVille("Sacré coeur");
		clientCriteria.setPays("Sénégal");
		result = clientService.findClientByCriteria(clientCriteria);
		// Check
		assertThat(result, is(notNullValue()));
		assertThat(result, is(not(empty())));
		assertThat(result.size(), is(greaterThanOrEqualTo(1)));

		result.forEach(app -> {
			assertThat(app.getAdresse().getId(), is(notNullValue()));
			assertThat(app.getAdresse().getPays(), is("Sénégal"));
		});
	}

}
