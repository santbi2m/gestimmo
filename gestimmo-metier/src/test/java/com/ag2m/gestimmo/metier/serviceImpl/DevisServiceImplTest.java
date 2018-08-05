/**
 * 
 */
package com.ag2m.gestimmo.metier.serviceImpl;



import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.empty;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.joda.time.LocalDateTime;

import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ag2m.gestimmo.metier.constants.TechnicalErrorMessageConstants;
import com.ag2m.gestimmo.metier.dto.AdresseDto;
import com.ag2m.gestimmo.metier.dto.AppartementDto;
import com.ag2m.gestimmo.metier.dto.BienDto;
import com.ag2m.gestimmo.metier.dto.ClientDto;
import com.ag2m.gestimmo.metier.dto.DevisDto;
import com.ag2m.gestimmo.metier.dto.FactureDto;
import com.ag2m.gestimmo.metier.dto.ReservationDto;
import com.ag2m.gestimmo.metier.enumeration.EnumOptionFormatDate;
import com.ag2m.gestimmo.metier.enumeration.EnumStatutReservation;
import com.ag2m.gestimmo.metier.enumeration.EnumTypeAppartement;
import com.ag2m.gestimmo.metier.enumeration.EnumTypePieceIdentite;
import com.ag2m.gestimmo.metier.exception.TechnicalException;
import com.ag2m.gestimmo.metier.ioparam.CommonFactureCriteria;
import com.ag2m.gestimmo.metier.service.DevisService;
import com.ag2m.gestimmo.metier.service.GestimmoServiceTest;
import com.ag2m.gestimmo.metier.utils.CustomDateUtil;

/**
 * @author mombaye
 *
 */
public class DevisServiceImplTest extends AbstractCommonTest{

	@Autowired
	DevisService devisService;
	
	@Autowired
	GestimmoServiceTest gestimmoServiceTest;
	
	/**
	 * Teste findDevisById avec un id null
	 * Une exception doit être levée  
	 * @throws TechnicalException 
	 */
	@Test
	public void findDevisByIdNull() throws TechnicalException {
		//Assert that an exception has thrown
		thrown.expect(TechnicalException.class);
		thrown.expectMessage(TechnicalErrorMessageConstants.ERREUR_ID_NULL);
		
		//Appel du service
		devisService.findDevisById(null);
	}
	
	
	/**
	 * Teste findDevisById 
	 * 
	 * @throws TechnicalException 
	 */
	@Test
	public void findDevisById() throws TechnicalException {
		
		//Préparation des données nécéssaire à la création du devis
		FactureDto facture = prepareDonneesDevis();
		//création d'un devis en BDD
		DevisDto devisDto = createDevis("Souane", "Amadou", "amadou.souane@gmail.com", "0645123658", LocalDateTime.now().plusDays(5), LocalDateTime.now().plusDays(10), facture);
	
		//Vérifier que le devis a bien été créé en BDD
		assertThat(devisDto, is(notNullValue()));
		assertThat(devisDto.getId(), is(notNullValue()));
		
		//Appel du service
		DevisDto devisFound = gestimmoServiceTest.findDevisById(devisDto.getId());
		
		assertThat(devisFound, is(notNullValue()));
		assertThat(devisFound.getId(), is(notNullValue()));
		assertThat(devisFound.getFacture(), is(notNullValue()));
		assertThat(devisFound.getFacture().getClient(), is(notNullValue()));
		assertThat(devisFound.getFacture().getAdresseFacturation(), is(notNullValue()));
	}
	
	
	/**
	 * Teste findAllDevis 
	 * 
	 * @throws TechnicalException 
	 */
	@Test
	public void findAllDevis() throws TechnicalException {
		
		/******************************
		 * 
		 * Création d'un premier devis
		 * 
		 * ****************************/
		//Préparation des données nécéssaire à la création du devis
		FactureDto facture = prepareDonneesDevis();
		//création d'un devis en BDD
		DevisDto devisDto = createDevis("Souane", "Amadou", "amadou.souane@gmail.com", "0645123658", LocalDateTime.now().plusDays(5), LocalDateTime.now().plusDays(10), facture);
		
		//Vérifier que le devis a bien été créé en BDD
		assertThat(devisDto, is(notNullValue()));
		assertThat(devisDto.getId(), is(notNullValue()));
		
		/******************************
		 * 
		 * Création d'un premier devis
		 * 
		 * ****************************/
		
		//Creation d'un objet transient Adresse pour le client demandant le devis
		AdresseDto adresseFacturation2 = initAdresse("1 av antoine", null, 44000, "Bordeaux", "France");
		
		//Creation d'un objet transient Client.
		ClientDto client2 = initClient("Maiga", "Amadou", "amadou.maiga@gmail.com", "A0987654321", EnumTypePieceIdentite.PASSEPORT.getType(), "0478542314", adresseFacturation2);
		
		
		//Préparation des données nécéssaire à la création du devis
		FactureDto factureSimule = prepareDonneesDevis();
		factureSimule.setClient(client2);
		facture.setAdresseFacturation(adresseFacturation2);
		factureSimule.setRemise(5D);
		factureSimule.setTva(20D);
		factureSimule.setTaxeSejour(5D);
		
		//création d'un devis en BDD
		DevisDto devisDto2 = createDevis("Maiga", "Amadou", "amadou.maiga@gmail.com", "0478542314", LocalDateTime.now().plusDays(2), LocalDateTime.now().plusDays(3), factureSimule);

		//Vérifier que le devis a bien été créé en BDD
		assertThat(devisDto2, is(notNullValue()));
		assertThat(devisDto2.getId(), is(notNullValue()));
		
		//Appel du service
		List<DevisDto> devisFound = gestimmoServiceTest.findAllDevis();
		
		assertThat(devisFound, is(notNullValue()));
		assertThat(devisFound, is(not(empty())));
		assertThat(devisFound.size(), is(greaterThanOrEqualTo(2)));
		
		devisFound.forEach(devis -> {
			
			assertThat(devis.getId(), is(notNullValue()));
			assertThat(devis.getFacture(), is(notNullValue()));
			assertThat(devis.getFacture().getClient(), is(notNullValue()));
			assertThat(devis.getFacture().getAdresseFacturation(), is(notNullValue()));
		});
	}
	
	/**
	 * Test du service createDevis
	 * 
	 * @throws TechnicalException
	 */
	@Test
	public void createDevis() throws TechnicalException {
		
		//Préparation des données nécéssaire à la création du devis
		FactureDto facture = prepareDonneesDevis();
		ClientDto client = facture.getClient();
		//Création de devis 
		DevisDto devisDto = createDevis(client.getNom(), client.getPrenom(), 
				client.getAdresseEmail(), client.getTelephone(), 
				new LocalDateTime(), new LocalDateTime().plusDays(3),
				facture);
		
		assertThat(devisDto, is(notNullValue()));
		assertThat(devisDto.getId(), is(notNullValue()));
		assertThat(devisDto.getDateCreation(), is(notNullValue()));
		assertThat(devisDto.getFacture(), is(notNullValue()));
		assertThat(devisDto.getFacture().getReservations(), is(notNullValue()));
		assertThat(devisDto.getFacture().getReservations(), is(not(empty())));
		assertThat(devisDto.getFacture().getReservations().size(), is(greaterThanOrEqualTo(2)));
		
		//Vérifier que la période de réservation enrégistrée correspond bien au min des dates check-in 
		// et au max des dates check-out (ça devrait correspondre à [aujourd'hui - aujourd'hui + 10 jours] 
		LocalDateTime today = CustomDateUtil.formatDateByFormatOption(LocalDateTime.now(), EnumOptionFormatDate.CHECKIN_FORMAT);
		LocalDateTime todayPlus10days = CustomDateUtil.formatDateByFormatOption(LocalDateTime.now().plusDays(10), EnumOptionFormatDate.CHECKOUT_FORMAT);
		assertThat(devisDto.getDateCheckin(), is(today));
		assertThat(devisDto.getDateCheckout(), is(todayPlus10days));
	}

	
	/**
	 * Teste updateDevis
	 * Cas où l'id du devis est null 
	 * 
	 * @throws TechnicalException 
	 */
	@Test
	public void updateDevisIdNull() throws TechnicalException {
		
		
		//Assert that an exception has thrown
		thrown.expect(TechnicalException.class);
		thrown.expectMessage(TechnicalErrorMessageConstants.ERREUR_ENTREE_MODIFICATION_NULL);
		
		//Appel du service
		devisService.updateDevis(new DevisDto());
	}
	
	/**
	 * Teste updateDevis
	 * 
	 * @throws TechnicalException 
	 */
	@Test
	public void updateDevis() throws TechnicalException {
		
		//Préparation des données nécéssaire à la création du devis
		FactureDto facture = prepareDonneesDevis();
		
		//création d'un devis en BDD
		DevisDto devisDto = createDevis("Souane", "Amadou", "amadou.souane@gmail.com", "0645123658", LocalDateTime.now().plusDays(5), LocalDateTime.now().plusDays(10), facture);
	
		//Vérifier que le devis a bien été créé en BDD
		assertThat(devisDto, is(notNullValue()));
		assertThat(devisDto.getId(), is(notNullValue()));
		
		//Appel du service
		DevisDto devisFound = gestimmoServiceTest.findDevisById(devisDto.getId());
		
		assertThat(devisFound, is(notNullValue()));
		assertThat(devisFound.getId(), is(notNullValue()));
		assertThat(devisFound.getFacture(), is(notNullValue()));
		assertThat(devisFound.getFacture().getClient(), is(notNullValue()));
		assertThat(devisFound.getFacture().getAdresseFacturation(), is(notNullValue()));
		
		devisFound.getFacture().getClient().setPrenom("Mohamet");
		devisFound.getFacture().getClient().setNom("MBAYE");
		devisFound.getFacture().getAdresseFacturation().setAdresse("3 avenue Guediawaye");
		
		devisService.updateDevis(devisFound);
		
		assertThat(devisFound, is(notNullValue()));
		assertThat(devisFound.getId(), is(notNullValue()));
		assertThat(devisFound.getFacture(), is(notNullValue()));
		assertThat(devisFound.getFacture().getClient(), is(notNullValue()));
		assertThat(devisFound.getFacture().getAdresseFacturation(), is(notNullValue()));
		assertThat(devisFound.getFacture().getClient().getPrenom(), is(("Mohamet")));
		assertThat(devisFound.getFacture().getClient().getNom(), is(("MBAYE")));
		assertThat(devisFound.getFacture().getAdresseFacturation().getAdresse(), is(("3 avenue Guediawaye")));
	}
	
	
	/**
	 * Teste deleteDevis
	 * 
	 * @throws TechnicalException 
	 */
	@Test
	public void deleteDevisEntiteNull() throws TechnicalException {
		//Assert that an exception has thrown
		thrown.expect(TechnicalException.class);
		thrown.expectMessage(TechnicalErrorMessageConstants.ERREUR_ENTREE_SUPP_NULL);
		
		devisService.deleteDevis(null);
	}
	
	/**
	 * Teste deleteDevis
	 * 
	 * @throws TechnicalException 
	 */
	@Test
	public void deleteDevis() throws TechnicalException {
		
		//Préparation des données nécéssaire à la création du devis
		FactureDto facture = prepareDonneesDevis();
		
		//création d'un devis en BDD
		DevisDto devisDto = createDevis("Souane", "Amadou", "amadou.souane@gmail.com", "0645123658", LocalDateTime.now().plusDays(5), LocalDateTime.now().plusDays(10), facture);
	
		//Vérifier que le devis a bien été créé en BDD
		assertThat(devisDto, is(notNullValue()));
		assertThat(devisDto.getId(), is(notNullValue()));
		
		//Appel du service
		DevisDto devisFound = gestimmoServiceTest.findDevisById(devisDto.getId());
		
		assertThat(devisFound, is(notNullValue()));
		assertThat(devisFound.getId(), is(notNullValue()));
		assertThat(devisFound.getFacture(), is(notNullValue()));
		assertThat(devisFound.getFacture().getClient(), is(notNullValue()));
		assertThat(devisFound.getFacture().getAdresseFacturation(), is(notNullValue()));
		
		
		boolean isDeleted = devisService.deleteDevis(devisFound);
		
		assertThat(isDeleted, is(true));
		
		devisFound = gestimmoServiceTest.findDevisById(devisFound.getId());
		assertThat(devisFound, is(nullValue()));
	}

	/**
	 * Test du serrvice findDevisByCriteria
	 * avec tous les critères de recherche
	 * @throws TechnicalException 
	 */
	@Test
	public void findDevisByCriteria() throws TechnicalException {
	
		//Préparation des données nécéssaires au devis.
		FactureDto facture = prepareDonneesDevis();
		ClientDto client = facture.getClient();
		
		//Création de devis 
		DevisDto devisDto = createDevis(client.getNom(), client.getPrenom(), 
				client.getAdresseEmail(), client.getTelephone(), 
				new LocalDateTime(), new LocalDateTime().plusDays(3),
				facture);
		
		/***************************************************
		 * 
		 * Recherche par nom Client
		 * 
		 ***************************************************/
		CommonFactureCriteria devisCriteria = new CommonFactureCriteria();
		devisCriteria.setNom(client.getNom());
		callAndVerifyForOneCriteria(client, devisDto, devisCriteria);
		
		/***************************************************
		 * 
		 * Recherche par prénom Client
		 * 
		 ***************************************************/
		devisCriteria = new CommonFactureCriteria();
		devisCriteria.setPrenom(client.getPrenom());
		callAndVerifyForOneCriteria(client, devisDto, devisCriteria);
		
		/***************************************************
		 * 
		 * Recherche par adresse email Client
		 * 
		 ***************************************************/
		devisCriteria = new CommonFactureCriteria();
		devisCriteria.setAdresseEmail(client.getAdresseEmail());
		callAndVerifyForOneCriteria(client, devisDto, devisCriteria);
		
		/***************************************************
		 * 
		 * Recherche par numéro de téléphone Client
		 * 
		 ***************************************************/
		devisCriteria = new CommonFactureCriteria();
		devisCriteria.setTelephone(client.getTelephone());
		callAndVerifyForOneCriteria(client, devisDto, devisCriteria);
		
		/***************************************************
		 * 
		 * Recherche par numéro de devis
		 * 
		 ***************************************************/
		devisCriteria = new CommonFactureCriteria();
		devisCriteria.setNumero(devisDto.getNumeroDevis());
		callAndVerifyForOneCriteria(client, devisDto, devisCriteria);
		
		/***************************************************
		 * 
		 * Recherche par avec combinaison l'ensemble des 
		 * critères précédents
		 * 
		 ***************************************************/
		devisCriteria = new CommonFactureCriteria();
		devisCriteria.setNom(client.getNom());
		devisCriteria.setPrenom(client.getPrenom());
		devisCriteria.setNumero(devisDto.getNumeroDevis());
		devisCriteria.setTelephone(client.getTelephone());
		devisCriteria.setAdresseEmail(client.getAdresseEmail());
		devisCriteria.setTelephone(client.getTelephone());
		callAndVerifyForOneCriteria(client, devisDto, devisCriteria);
	}


	/**
	 * Fait l'appel de findDevisByCriteria pour un critere donné
	 * et vérifie que le résultat est coorect.
	 * 
	 * 
	 * @param client
	 * @param devisDto
	 * @throws TechnicalException
	 */
	private void callAndVerifyForOneCriteria(ClientDto client, DevisDto devisDto, CommonFactureCriteria devisCriteria) throws TechnicalException {
		
		List<DevisDto> results = devisService.findDevisByCriteria(devisCriteria);
		
		//Vérification : 
			//Au moins un résultat est remonté
			assertThat(results, is(notNullValue()));
			assertThat(results, is(not(empty())));
			//Le devis précéedmment créé est bien retrouvé dans la liste retournée.
			List<DevisDto> devisTrouve = results.stream().filter(devis 
					-> devis.getId().compareTo(devisDto.getId()) == 0)
					.collect(Collectors.toList());
			assertThat(devisTrouve, is(not(empty())));
	}
	
	/**
	 * Préparation des données nécessaire à la 
	 * création d'un devis.
	 * 
	 * @return
	 * @throws TechnicalException
	 */
	private FactureDto prepareDonneesDevis() throws TechnicalException {
		// Adresse
		AdresseDto adresse = createAdresse("12 cité Fadia", null, 9900, "Sacré coeur", "Sénégal");
		AdresseDto adresse2 = createAdresse("18 rue carnot", null, 9900, "Plateau", "Sénégal");
		createAdresse("25 avec Jean Jaures", null, 9900, "Plateau", "Sénégal");
		
		// Bien
		BienDto bien = createBien("Wakeur Meissa", adresse);
		
		//Appartements
		AppartementDto app1 = createAppartement("Dalal Diam", bien, EnumTypeAppartement.T2.getType(), 50D);
		AppartementDto app2 = createAppartement("Dem Deloussi", bien, EnumTypeAppartement.T3.getType(), 70D);
		AppartementDto app3 = createAppartement("Tawfekh", bien, EnumTypeAppartement.STUDIO.getType(), 45D);
		
		//Clients
		ClientDto client = createClient("Maiga", "Amadou", "amai@gmail.com", 
				"123456789", EnumTypePieceIdentite.CARTE_IDENTITE.getType(), "+33645897456", adresse2);
		
		//Facture
		FactureDto facture = createFacture(client, 2D, adresse2, 20D, 15D);
		
		//Reservations
		ReservationDto reservation1 = createReservation(new LocalDateTime(), new LocalDateTime().plusDays(10), "Avec lit bébé svp", 
						true, EnumStatutReservation.ENREGISTREE.getStatut(), Arrays.asList(app1, app2, app3),
						50D, new LocalDateTime(), null, client, facture);
		
		ReservationDto reservation2 = createReservation(new LocalDateTime(), new LocalDateTime().plusDays(3), "RAS", 
				true, EnumStatutReservation.CONFIRMEE.getStatut(), Arrays.asList(app2, app3), 
				120D, new LocalDateTime(), null, client, facture);		
		
		
		app1.setReservations(Arrays.asList(reservation1));
		app2.setReservations(Arrays.asList(reservation1, reservation2));
		app3.setReservations(Arrays.asList(reservation1, reservation2));
		
		appartementService.createAppartement(app1);
		appartementService.createAppartement(app2);
		appartementService.createAppartement(app3);
		
		facture.setReservations(Arrays.asList(reservation1, reservation2));
		factureService.saveOrUpdate(facture);
		
		return facture;
	}
}
