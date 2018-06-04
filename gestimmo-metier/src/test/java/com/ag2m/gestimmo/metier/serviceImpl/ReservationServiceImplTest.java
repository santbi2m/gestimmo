package com.ag2m.gestimmo.metier.serviceImpl;

import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.junit.Test;

import com.ag2m.gestimmo.metier.constants.FunctionnalErrorMessageConstants;
import com.ag2m.gestimmo.metier.constants.TechnicalErrorMessageConstants;
import com.ag2m.gestimmo.metier.dto.AdresseDto;
import com.ag2m.gestimmo.metier.dto.AppartementDto;
import com.ag2m.gestimmo.metier.dto.BienDto;
import com.ag2m.gestimmo.metier.dto.ClientDto;
import com.ag2m.gestimmo.metier.dto.FactureDto;
import com.ag2m.gestimmo.metier.dto.ReservationDto;
import com.ag2m.gestimmo.metier.entite.Reservation;
import com.ag2m.gestimmo.metier.enumeration.EnumOptionFormatDate;
import com.ag2m.gestimmo.metier.enumeration.EnumStatutReservation;
import com.ag2m.gestimmo.metier.enumeration.EnumTypeAppartement;
import com.ag2m.gestimmo.metier.enumeration.EnumTypePieceIdentite;
import com.ag2m.gestimmo.metier.exception.FunctionalException;
import com.ag2m.gestimmo.metier.exception.TechnicalException;
import com.ag2m.gestimmo.metier.ioparam.ReservationCriteria;
import com.ag2m.gestimmo.metier.ioparam.UniteReservation;
import com.ag2m.gestimmo.metier.utils.CustomDateUtil;

import static org.hamcrest.core.IsNull.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

/**
 * Classe de test pour les 
 * services de reservation
 * 
 * @author mombaye
 *
 */
public class ReservationServiceImplTest extends AbstractCommonTest{

	/****************************************************
	 * **************************************************
	 * 
	 *  Test du service métier findReservationById()
	 *                                             
	 *  ************************************************
	 ***************************************************/
	
	/**
	 * Rechercher une réservation via son id
	 * @throws TechnicalException
	 */
	@Test
	public void testFindReservationById() throws TechnicalException {
		
		// Call service
		ReservationDto reservation = reservationService.findReservationById(1L);
		
		//Check
		assertThat(reservation, is(notNullValue()));
		assertThat(reservation.getId(), is(1L));
		assertThat(reservation.getDateAnnulation(), is(nullValue()));
		assertThat(reservation.getFacture(), is(nullValue()));
		assertThat(reservation.getClient(), is(notNullValue()));
		assertThat(reservation.getClient().getId(), is(1L));
	}
	
	/**
	 * Rechercher une réservation avec un id null
	 * Une exception doit être levée.
	 * 
	 * @throws TechnicalException
	 */
	@Test
	public void testFindReservationByIdNull() throws TechnicalException {
		
		//Assert that an exception has thrown
		thrown.expect(FunctionalException.class);
		thrown.expectMessage(TechnicalErrorMessageConstants.ERREUR_ID_NULL);
		
		// Call service
		reservationService.findReservationById(null);
	}
	
	
	/****************************************************
	 * **************************************************
	 * 
	 *  Test du service métier loaddAllReservation()
	 *                                             
	 *  ************************************************
	 ***************************************************/
	
	
	/**
	 * Rechercher toutes les réservations en BDD
	 */
	@Test()
	public void testLoaddAllReservation() {

		// Call service
		List<ReservationDto> reservations = reservationService.loadAllReservation();
		
		//Check résults
		assertThat(reservations, is(notNullValue()));
		assertThat(reservations, is(not(empty())));
		assertThat(reservations.size(), greaterThanOrEqualTo(5));
		List<Integer> idList = reservations.stream().map(resa -> resa.getId().intValue()).collect(Collectors.toList());
		
		if(reservations.size() > 5) {
		assertThat(idList, containsInAnyOrder(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12));
		}else {
			assertThat(idList, containsInAnyOrder(1, 2, 3, 4, 5));
		}
	}

	/****************************************************
	 * **************************************************
	 * 
	 *  Test du service métier deleteReservation()
	 *                                             
	 *  ************************************************
	 ***************************************************/
	
	/**
	 * Supprimer une réservation
	 * Cas d'une entrée null
	 * @throws TechnicalException 
	 */
	@Test
	public void testDeleteReservationEntiteNull() throws TechnicalException {
		//Assert that an exception has thrown
		thrown.expect(FunctionalException.class);
		thrown.expectMessage(TechnicalErrorMessageConstants.ERREUR_ENTREE_SUPP_NULL);
		
		// Call service
		reservationService.deleteReservation(null);
	}
	
	@Test
	public void testDelete() throws TechnicalException, FunctionalException {
		
		// Adresse
		AdresseDto adresse = createAdresse("12 cité Fadia", null, 9900, "Sacré coeur", "Sénégal");
		AdresseDto adresse2 = createAdresse("18 rue carnot", null, 9900, "Plateau", "Sénégal");
		AdresseDto adresse3 = createAdresse("25 avec Jean Jaures", null, 9900, "Plateau", "Sénégal");
		
		// Bien
		BienDto bien = createBien("Wakeur Meissa", adresse);
		
		//Appartements
		AppartementDto app1 = createAppartement("Dalal Diam", bien, EnumTypeAppartement.T2.getType(), 50D);
		AppartementDto app2 = createAppartement("Dem Deloussi", bien, EnumTypeAppartement.T3.getType(), 70D);
		AppartementDto app3 = createAppartement("Tawfekh", bien, EnumTypeAppartement.STUDIO.getType(), 45D);
		
		//Clients
		ClientDto client = createClient("Maiga", "Amadou", "amai@gmail.com", 
				"123456789", EnumTypePieceIdentite.CARTE_IDENTITE.getType(), "+33645897456", adresse2);
		
		ClientDto client2 = createClient("Souane", "Amadou", "asoua@gmail.com", 
				"987654321", EnumTypePieceIdentite.PERMIS_CONDUIRE.getType(), "+33756487921", adresse3);
		
		//Facture
		FactureDto facture = createFacture(client2, 2D, adresse2, 20D, 15D);
		
		//Reservations
		ReservationDto reservation1 = createReservation(new LocalDateTime(), new LocalDateTime().plusDays(10), "Avec lit bébé svp", 
						true, EnumStatutReservation.ENREGISTREE.getStatut(), Arrays.asList(app1, app2, app3),
						50D, new LocalDateTime(), null, client, null);
		
		ReservationDto reservation2 = createReservation(new LocalDateTime(), new LocalDateTime().plusDays(3), "RAS", 
				true, EnumStatutReservation.CONFIRMEE.getStatut(), Arrays.asList(app2, app3), 
				120D, new LocalDateTime(), null, client2, facture);		
		
		
		app1.setReservations(Arrays.asList(reservation1));
		app2.setReservations(Arrays.asList(reservation1, reservation2));
		app3.setReservations(Arrays.asList(reservation1, reservation2));
		
		appartementService.createAppartement(app1);
		appartementService.createAppartement(app2);
		appartementService.createAppartement(app3);
		
		assertThat(reservation1.getId(), is(notNullValue()));
		assertThat(reservation2.getId(), is(notNullValue()));
		
		appartementService.deleteAppartement(app2);
		
		ReservationDto resa1 = reservationService.findReservationById(reservation2.getId());
		ReservationDto resa2 = reservationService.findReservationById(reservation2.getId());
		ReservationDto resa3 = reservationService.findReservationById(reservation1.getId());
		
		assertThat(resa1, is(nullValue()));
		assertThat(resa2, is(nullValue()));
		assertThat(resa3, is(nullValue()));
	}
	
	/**
	 * Supprimer une réservation
	 * physiquement de la BDD
	 * @throws TechnicalException 
	 */
	@Test
	public void testDeleteReservation() throws TechnicalException {
		//Charger la réservation à supprimer
		ReservationDto reservation = reservationService.findReservationById(1L);
				
		//Check résa est bien chargée
		assertThat(reservation, is(notNullValue()));
		assertThat(reservation.getId(), is(1L));

		// Call service
		boolean isDeleted = reservationService.deleteReservation(reservation);
		
		//Check
		assertThat(isDeleted, is(true));
		
		//Charger la réservation préalablement supprimée
		ReservationDto deletedResa = reservationService.findReservationById(1L);
		assertThat(deletedResa, is(nullValue()));
	}
	

	/****************************************************
	 * **************************************************
	 * 
	 *  Test du service métier createReservation()
	 *                                             
	 *  ************************************************
	 ***************************************************/
	
	
	/**
	 * Test la création de réservation
	 * Cas d'une entitée null
	 * 
	 * @throws FunctionalException
	 * @throws TechnicalException
	 */
	@Test
	public void testCreateReservationEntiteNull() throws FunctionalException, TechnicalException {
		//Assert that an exception has thrown
		thrown.expect(FunctionalException.class);
		thrown.expectMessage(TechnicalErrorMessageConstants.ERREUR_ENTREE_CREATION_NULL);
		
		// Call service
		reservationService.createReservation(null);
	}
	
	/**
	 * Test la création de réservation
	 * Cas d'un statut de réservation null.
	 * 
	 * @throws FunctionalException
	 * @throws TechnicalException
	 */
	@Test
	public void testCreateReservationStatutNull() throws FunctionalException, TechnicalException {
		//Assert that an exception has thrown
		thrown.expect(FunctionalException.class);
		thrown.expectMessage(FunctionnalErrorMessageConstants.ERREUR_RESERVATION_STATUT_NULL);
		
		
		//Init réservation
		
		AdresseDto adresse = createAdresse("25 avec Jean Jaures", null, 9900, "Plateau", "Sénégal");
		
		// Bien
		BienDto bien = createBien("Wakeur Meissa", adresse);
		
		//Appartements
		AppartementDto app1 = createAppartement("Dalal Diam", bien, EnumTypeAppartement.T2.getType(), 50D);
		
		//Clients
		ClientDto client = createClient("Maiga", "Amadou", "amai@gmail.com", 
				"123456789", EnumTypePieceIdentite.CARTE_IDENTITE.getType(), "+33645897456", adresse);
		
		//Création de la réservation avec un statut null
		ReservationDto resa = createReservation(new LocalDateTime(), new LocalDateTime().plusDays(10), "Avec lit bébé svp", 
						true, null, Arrays.asList(app1),
						50D, new LocalDateTime(), null, client, null);
		
		// Call service
		reservationService.createReservation(resa);
	}
	
	/**
	 * Test la création de réservation
	 * Cas d'une réservation sans date 
	 * checkin.
	 * 
	 * @throws FunctionalException
	 * @throws TechnicalException
	 */
	@Test
	public void testCreateReservationDateCheckinNull() throws FunctionalException, TechnicalException {
		//Assert that an exception has thrown
		thrown.expect(FunctionalException.class);
		thrown.expectMessage(FunctionnalErrorMessageConstants.ERREUR_RESERVATION_DATE_CHECKIN_NULL);
		
		
		//Init réservation
		
		AdresseDto adresse = createAdresse("25 avec Jean Jaures", null, 9900, "Plateau", "Sénégal");
		
		// Bien
		BienDto bien = createBien("Wakeur Meissa", adresse);
		
		//Appartements
		AppartementDto app1 = createAppartement("Dalal Diam", bien, EnumTypeAppartement.T2.getType(), 50D);
		
		//Clients
		ClientDto client = createClient("Maiga", "Amadou", "amai@gmail.com", 
				"123456789", EnumTypePieceIdentite.CARTE_IDENTITE.getType(), "+33645897456", adresse);
		
		//Création de la réservation avec une date checkin null
		ReservationDto resa = createReservation(null, new LocalDateTime().plusDays(10), "Avec lit bébé svp", 
						true, EnumStatutReservation.ENREGISTREE.getStatut(), Arrays.asList(app1),
						50D, new LocalDateTime(), null, client, null);
		
		// Call service
		reservationService.createReservation(resa);
	}
	
	
	/**
	 * Test la création de réservation
	 * Cas d'une réservation sans date 
	 * checkout.
	 * 
	 * @throws FunctionalException
	 * @throws TechnicalException
	 */
	@Test
	public void testCreateReservationDateCheckoutNull() throws FunctionalException, TechnicalException {
		//Assert that an exception has thrown
		thrown.expect(FunctionalException.class);
		thrown.expectMessage(FunctionnalErrorMessageConstants.ERREUR_RESERVATION_DATE_CHECKOUT_NULL);
		
		
		//Init réservation
		
		AdresseDto adresse = createAdresse("25 avec Jean Jaures", null, 9900, "Plateau", "Sénégal");
		
		// Bien
		BienDto bien = createBien("Wakeur Meissa", adresse);
		
		//Appartements
		AppartementDto app1 = createAppartement("Dalal Diam", bien, EnumTypeAppartement.T2.getType(), 50D);
		
		//Clients
		ClientDto client = createClient("Maiga", "Amadou", "amai@gmail.com", 
				"123456789", EnumTypePieceIdentite.CARTE_IDENTITE.getType(), "+33645897456", adresse);
		
		//Création de la réservation avec une date checkin null
		ReservationDto resa = createReservation(new LocalDateTime(), null, "Avec lit bébé svp", 
						true, EnumStatutReservation.ENREGISTREE.getStatut(), Arrays.asList(app1),
						50D, new LocalDateTime(), null, client, null);
		
		// Call service
		reservationService.createReservation(resa);
	}
	
	
	/**
	 * Test la création de réservation
	 * Cas d'une réservation avec une date
	 * annulation non null.
	 * 
	 * @throws FunctionalException
	 * @throws TechnicalException
	 */
	@Test
	public void testCreateReservationDateAnnulationNonNull() throws FunctionalException, TechnicalException {
		//Assert that an exception has thrown
		thrown.expect(FunctionalException.class);
		thrown.expectMessage(FunctionnalErrorMessageConstants.ERREUR_RESERVATION_DATE_ANNULATION_NON_NULL);
		
		
		//Init réservation
		
		AdresseDto adresse = createAdresse("25 avec Jean Jaures", null, 9900, "Plateau", "Sénégal");
		
		// Bien
		BienDto bien = createBien("Wakeur Meissa", adresse);
		
		//Appartements
		AppartementDto app1 = createAppartement("Dalal Diam", bien, EnumTypeAppartement.T2.getType(), 50D);
		
		//Clients
		ClientDto client = createClient("Maiga", "Amadou", "amai@gmail.com", 
				"123456789", EnumTypePieceIdentite.CARTE_IDENTITE.getType(), "+33645897456", adresse);
		
		//Création de la réservation avec une date checkin null
		ReservationDto resa = createReservation(new LocalDateTime(), new LocalDateTime().plusDays(10), "Avec lit bébé svp", 
						true, EnumStatutReservation.ENREGISTREE.getStatut(), Arrays.asList(app1),
						50D, new LocalDateTime(), new LocalDateTime(), client, null);
		
		// Call service
		reservationService.createReservation(resa);
	}
	
	
	/**
	 * Test la création de réservation
	 * Cas d'une réservation avec un mauvais staut.
	 * Statut "FACTUREE" par exemple.
	 * 
	 * @throws FunctionalException
	 * @throws TechnicalException
	 */
	@Test
	public void testCreateReservationAvecUnStatutKO() throws FunctionalException, TechnicalException {
		//Assert that an exception has thrown
		thrown.expect(FunctionalException.class);
		thrown.expectMessage(FunctionnalErrorMessageConstants.ERREUR_RESERVATION_STATUT_INCORRECT);
		
		//Init réservation
		
		AdresseDto adresse = createAdresse("25 avec Jean Jaures", null, 9900, "Plateau", "Sénégal");
		
		// Bien
		BienDto bien = createBien("Wakeur Meissa", adresse);
		
		//Appartements
		AppartementDto app1 = createAppartement("Dalal Diam", bien, EnumTypeAppartement.T2.getType(), 50D);
		
		//Clients
		ClientDto client = createClient("Maiga", "Amadou", "amai@gmail.com", 
				"123456789", EnumTypePieceIdentite.CARTE_IDENTITE.getType(), "+33645897456", adresse);
		
		//Création de la réservation avec une date checkin null
		ReservationDto resa = createReservation(new LocalDateTime(), new LocalDateTime().plusDays(10), "Avec lit bébé svp", 
						true, EnumStatutReservation.FACTUREE.getStatut(), Arrays.asList(app1),
						50D, new LocalDateTime(), null, client, null);
		
		// Call service
		reservationService.createReservation(resa);
	}
	
	
	/**
	 * Test la création de réservation
	 * Cas d'une réservation avec une date
	 * de checkin positionnée dans le passée.
	 * 
	 * @throws FunctionalException
	 * @throws TechnicalException
	 */
	@Test
	public void testCreateReservationAvecDateCkeckinAuPassee() throws FunctionalException, TechnicalException {
		//Assert that an exception has thrown
		thrown.expect(FunctionalException.class);
		thrown.expectMessage(FunctionnalErrorMessageConstants.ERREUR_RESERVATION_DATE_CHECKIN_INCORRECT);
		
		//Init réservation
		
		AdresseDto adresse = createAdresse("25 avec Jean Jaures", null, 9900, "Plateau", "Sénégal");
		
		// Bien
		BienDto bien = createBien("Wakeur Meissa", adresse);
		
		//Appartements
		AppartementDto app1 = createAppartement("Dalal Diam", bien, EnumTypeAppartement.T2.getType(), 50D);
		
		//Clients
		ClientDto client = createClient("Maiga", "Amadou", "amai@gmail.com", 
				"123456789", EnumTypePieceIdentite.CARTE_IDENTITE.getType(), "+33645897456", adresse);
		
		//Création de la réservation avec une date checkin null
		ReservationDto resa = createReservation(new LocalDateTime().minusDays(1), new LocalDateTime().plusDays(10), "Avec lit bébé svp", 
						true, EnumStatutReservation.ENREGISTREE.getStatut(), Arrays.asList(app1),
						50D, new LocalDateTime(), null, client, null);
		
		// Call service
		reservationService.createReservation(resa);
	}
	
	/**
	 * Teste la création de réservation
	 * Cas d'une réservation avec une date
	 * de checkout antérieurs à date checkin.
	 * 
	 * @throws FunctionalException
	 * @throws TechnicalException
	 */
	@Test
	public void testCreateReservationDateCkeckoutAnterieureDateCheckin() throws FunctionalException, TechnicalException {
		//Assert that an exception has thrown
		thrown.expect(FunctionalException.class);
		thrown.expectMessage(FunctionnalErrorMessageConstants.ERREUR_RESERVATION_DATE_CHECKOUT_INCORRECT);
		
		//Init réservation
		
		AdresseDto adresse = createAdresse("25 avec Jean Jaures", null, 9900, "Plateau", "Sénégal");
		
		// Bien
		BienDto bien = createBien("Wakeur Meissa", adresse);
		
		//Appartements
		AppartementDto app1 = createAppartement("Dalal Diam", bien, EnumTypeAppartement.T2.getType(), 50D);
		
		//Clients
		ClientDto client = createClient("Maiga", "Amadou", "amai@gmail.com", 
				"123456789", EnumTypePieceIdentite.CARTE_IDENTITE.getType(), "+33645897456", adresse);
		
		//Création de la réservation avec une date checkin null
		ReservationDto resa = createReservation(new LocalDateTime(), new LocalDateTime().minusDays(10), "Avec lit bébé svp", 
						true, EnumStatutReservation.ENREGISTREE.getStatut(), Arrays.asList(app1),
						50D, new LocalDateTime(), null, client, null);
		
		// Call service
		reservationService.createReservation(resa);
	}
	
	
	/**
	 * Teste la création de réservation
	 * Cas d'une réservation avec une réduction 
	 * sans justificatif
	 * 
	 * @throws FunctionalException
	 * @throws TechnicalException
	 */
	@Test
	public void testCreateReservationTarifNonCoherentSansNoteExplicative() throws FunctionalException, TechnicalException {
		//Assert that an exception has thrown
		thrown.expect(FunctionalException.class);
		thrown.expectMessage(FunctionnalErrorMessageConstants.ERREUR_RESERVATION_REMISE_NON_JUSTIFIEE);
		
		// Adresse
		AdresseDto adresse = createAdresse("12 cité Fadia", null, 9900, "Sacré coeur", "Sénégal");
		AdresseDto adresse3 = createAdresse("25 avec Jean Jaures", null, 9900, "Plateau", "Sénégal");
		
		// Bien
		BienDto bien = createBien("Wakeur Meissa", adresse);
		
		//Appartements
		AppartementDto app1 = createAppartement("Dalal Diam", bien, EnumTypeAppartement.T2.getType(), 50D);
		AppartementDto app2 = createAppartement("Dem Deloussi", bien, EnumTypeAppartement.T3.getType(), 70D);
		AppartementDto app3 = createAppartement("Tawfekh", bien, EnumTypeAppartement.STUDIO.getType(), 45D);
		
		//Clients
		ClientDto client2 = createClient("Souane", "Amadou", "asoua@gmail.com", 
				"987654321", EnumTypePieceIdentite.PERMIS_CONDUIRE.getType(), "+33756487921", adresse3);
	
		//Le tarif total des 3 appartements en cours de réservation est de:
		//50 + 70 + 45 = 165 euros
		//Si on essaie de reserver avec 100 euros par exemple comme tarif, sans saisir de note
		//Une exception fonctionnele doit être lancée
		createReservation(new LocalDateTime(), new LocalDateTime().plusDays(3), null, 
				true, EnumStatutReservation.ENREGISTREE.getStatut(), Arrays.asList(app1, app2, app3), 
				100D, new LocalDateTime(), null, client2, null);
	}
	
	
	/**
	 * Teste la création de réservation
	 * Cas d'une présence de réservation dans la période.
	 * La réservation en cours doit passer en liste d'attente.
	 * 
	 * @throws FunctionalException
	 * @throws TechnicalException
	 */
	@Test
	public void testCreateReservationConflitDeReservation() throws FunctionalException, TechnicalException {
		
		// Adresse
		AdresseDto adresse = createAdresse("12 cité Fadia", null, 9900, "Sacré coeur", "Sénégal");
		AdresseDto adresse3 = createAdresse("25 avec Jean Jaures", null, 9900, "Plateau", "Sénégal");
		
		// Bien
		BienDto bien = createBien("Wakeur Meissa", adresse);
		
		//Appartements
		AppartementDto app1 = createAppartement("Dalal Diam", bien, EnumTypeAppartement.T2.getType(), 50D);
		AppartementDto app2 = createAppartement("Dem Deloussi", bien, EnumTypeAppartement.T3.getType(), 70D);
		AppartementDto app3 = createAppartement("Tawfekh", bien, EnumTypeAppartement.STUDIO.getType(), 45D);
		
		//Clients
		ClientDto client2 = createClient("Souane", "Amadou", "asoua@gmail.com", 
				"987654321", EnumTypePieceIdentite.PERMIS_CONDUIRE.getType(), "+33756487921", adresse3);
		
		//Première réservation
		ReservationDto resa1 = createReservation(new LocalDateTime(), new LocalDateTime().plusDays(3), null, 
				true, EnumStatutReservation.ENREGISTREE.getStatut(), Arrays.asList(app1, app2, app3), 
				165D, new LocalDateTime(), null, client2, null);
		
		//Check results
		assertThat(resa1.getId(), is(notNullValue()));
		
		app1.setReservations(Arrays.asList(resa1));
		app2.setReservations(Arrays.asList(resa1));
		app3.setReservations(Arrays.asList(resa1));
		
		//Associations appart -> Resa resauvegarde de l'appartement
		appartementService.createAppartement(app1);
		appartementService.createAppartement(app2);
		appartementService.createAppartement(app3);
		
		//Deuxième réservation dans la même période sur un des appartement occupés.
		ReservationDto resa2 = createReservation(new LocalDateTime(), new LocalDateTime().plusDays(3), null, 
				true, EnumStatutReservation.ENREGISTREE.getStatut(), Arrays.asList(app1), 
				165D, new LocalDateTime(), null, client2, null);
		
		//Check results
		
		assertThat(resa2.getId(), is(notNullValue()));
		assertThat(resa2.getDateCreation(), is(notNullValue()));
		assertThat(CustomDateUtil.isSameDay(resa2.getDateCreation(), LocalDateTime.now()), is(true));
		assertThat(resa2.getAppartements(), is(not(empty())));
		assertThat(resa2.getStatut(), is(EnumStatutReservation.EN_ATTENTE.getStatut()));
	}
	
	
	@Test
	public void testCreateReservation() throws TechnicalException, FunctionalException {
		
		// Adresse
		AdresseDto adresse = createAdresse("12 cité Fadia", null, 9900, "Sacré coeur", "Sénégal");
		AdresseDto adresse2 = createAdresse("18 rue carnot", null, 9900, "Plateau", "Sénégal");
		AdresseDto adresse3 = createAdresse("25 avec Jean Jaures", null, 9900, "Plateau", "Sénégal");
		
		// Bien
		BienDto bien = createBien("Wakeur Meissa", adresse);
		
		//Appartements
		AppartementDto app1 = createAppartement("Dalal Diam", bien, EnumTypeAppartement.T2.getType(), 50D);
		AppartementDto app2 = createAppartement("Dem Deloussi", bien, EnumTypeAppartement.T3.getType(), 70D);
		AppartementDto app3 = createAppartement("Tawfekh", bien, EnumTypeAppartement.STUDIO.getType(), 45D);
		
		//Clients
		ClientDto client = createClient("Maiga", "Amadou", "amai@gmail.com", 
				"123456789", EnumTypePieceIdentite.CARTE_IDENTITE.getType(), "+33645897456", adresse2);
		
		ClientDto client2 = createClient("Souane", "Amadou", "asoua@gmail.com", 
				"987654321", EnumTypePieceIdentite.PERMIS_CONDUIRE.getType(), "+33756487921", adresse3);
		
		//Facture
		FactureDto facture = createFacture(client2, 2D, adresse2, 20D, 15D);
		
		//Reservations
		ReservationDto reservation1 = createReservation(new LocalDateTime().plusDays(10), new LocalDateTime().plusDays(15), "Avec lit bébé svp", 
						true, EnumStatutReservation.ENREGISTREE.getStatut(), Arrays.asList(app1),
						50D, new LocalDateTime(), null, client, null);
		
		ReservationDto reservation2 = createReservation(new LocalDateTime(), new LocalDateTime().plusDays(3), "RAS", 
				true, EnumStatutReservation.EN_ATTENTE.getStatut(), Arrays.asList(app2, app3), 
				165D, new LocalDateTime(), null, client2, facture);
		
		
		app1.setReservations(Arrays.asList(reservation1));
		app2.setReservations(Arrays.asList(reservation1, reservation2));
		app3.setReservations(Arrays.asList(reservation1, reservation2));
		
		//Call services
		appartementService.createAppartement(app1);
		appartementService.createAppartement(app2);
		appartementService.createAppartement(app3);
		
		//Check results
		assertThat(reservation1.getId(), is(notNullValue()));
		assertThat(reservation2.getId(), is(notNullValue()));
		assertThat(reservation1.getStatut(), is(EnumStatutReservation.ENREGISTREE.getStatut()));
		assertThat(reservation2.getStatut(), is(EnumStatutReservation.EN_ATTENTE.getStatut()));
		assertThat(true, is(CustomDateUtil.isSameDay(reservation1.getDateCreation(), LocalDateTime.now())));
		assertThat(true, is(CustomDateUtil.isSameDay(reservation2.getDateCreation(), LocalDateTime.now())));
		
	}
	
	
	/**
	 * Teste la création de réservation
	 * Cas d'une réservation n'ayant que des nuitées
	 * 
	 * @throws FunctionalException
	 * @throws TechnicalException
	 */
	@Test
	public void testCreateReservationNuitees() throws FunctionalException, TechnicalException {
		
		// Adresse
		AdresseDto adresse = createAdresse("12 cité Fadia", null, 9900, "Sacré coeur", "Sénégal");
		AdresseDto adresse3 = createAdresse("25 avec Jean Jaures", null, 9900, "Plateau", "Sénégal");
		
		// Bien
		BienDto bien = createBien("Wakeur Meissa", adresse);
		
		//Appartements
		AppartementDto app1 = createAppartement("Dalal Diam", bien, EnumTypeAppartement.T2.getType(), 50D);
		AppartementDto app2 = createAppartement("Dem Deloussi", bien, EnumTypeAppartement.T3.getType(), 70D);
		AppartementDto app3 = createAppartement("Tawfekh", bien, EnumTypeAppartement.STUDIO.getType(), 45D);
		
		//Clients
		ClientDto client2 = createClient("Souane", "Amadou", "asoua@gmail.com", 
				"987654321", EnumTypePieceIdentite.PERMIS_CONDUIRE.getType(), "+33756487921", adresse3);
		
		//Première réservation
		ReservationDto resa1 = createReservation(new LocalDateTime(), new LocalDateTime().plusDays(1), null, 
				true, EnumStatutReservation.ENREGISTREE.getStatut(), Arrays.asList(app1, app2, app3), 
				165D, new LocalDateTime(), null, client2, null);
		
		//Check results
		assertThat(resa1.getId(), is(notNullValue()));
		
		app1.setReservations(Arrays.asList(resa1));
		app2.setReservations(Arrays.asList(resa1));
		app3.setReservations(Arrays.asList(resa1));
		
		//Associations appart -> Resa resauvegarde de l'appartement
		appartementService.createAppartement(app1);
		appartementService.createAppartement(app2);
		appartementService.createAppartement(app3);
		
		//Check results
		assertThat(resa1.getId(), is(notNullValue()));
		assertThat(resa1.getDateCreation(), is(notNullValue()));
		assertThat(CustomDateUtil.isSameDay(resa1.getDateCreation(), LocalDateTime.now()), is(true));
		assertThat(resa1.getAppartements(), is(not(empty())));
		assertThat(resa1.getStatut(), is(EnumStatutReservation.ENREGISTREE.getStatut()));
		//Les date de checkin et checkout sont réspectivement à 12h et 11h (Aujourd'hui et demain)
		assertThat(CustomDateUtil.formatDateByFormatOption(LocalDateTime.now(), 
				EnumOptionFormatDate.CHECKIN_FORMAT), 
			is(resa1.getDateCheckin()));
		assertThat(CustomDateUtil.formatDateByFormatOption(LocalDateTime.now().plusDays(1), 
				EnumOptionFormatDate.CHECKOUT_FORMAT), 
			is(resa1.getDateCheckout()));	
		}
	
	
	/**
	 * Teste la création de réservation
	 * Cas d'une réservation n'ayant que des day-uses
	 * 
	 * @throws FunctionalException
	 * @throws TechnicalException
	 */
	@Test
	public void testCreateReservationDayUses() throws FunctionalException, TechnicalException {
		
		// Adresse
		AdresseDto adresse = createAdresse("12 cité Fadia", null, 9900, "Sacré coeur", "Sénégal");
		AdresseDto adresse3 = createAdresse("25 avec Jean Jaures", null, 9900, "Plateau", "Sénégal");
		
		// Bien
		BienDto bien = createBien("Wakeur Meissa", adresse);
		
		//Appartements
		AppartementDto app1 = createAppartement("Dalal Diam", bien, EnumTypeAppartement.T2.getType(), 50D);
		AppartementDto app2 = createAppartement("Dem Deloussi", bien, EnumTypeAppartement.T3.getType(), 70D);
		AppartementDto app3 = createAppartement("Tawfekh", bien, EnumTypeAppartement.STUDIO.getType(), 45D);
		
		//Clients
		ClientDto client2 = createClient("Souane", "Amadou", "asoua@gmail.com", 
				"987654321", EnumTypePieceIdentite.PERMIS_CONDUIRE.getType(), "+33756487921", adresse3);
		
		//Première réservation
		ReservationDto resa1 = createReservation(new LocalDateTime(), new LocalDateTime(), null, 
				true, EnumStatutReservation.ENREGISTREE.getStatut(), Arrays.asList(app1, app2, app3), 
				165D, new LocalDateTime(), null, client2, null);
		
		//Check results
		assertThat(resa1.getId(), is(notNullValue()));
		
		app1.setReservations(Arrays.asList(resa1));
		app2.setReservations(Arrays.asList(resa1));
		app3.setReservations(Arrays.asList(resa1));
		
		//Associations appart -> Resa resauvegarde de l'appartement
		appartementService.createAppartement(app1);
		appartementService.createAppartement(app2);
		appartementService.createAppartement(app3);
		
		//Check results
		assertThat(resa1.getId(), is(notNullValue()));
		assertThat(resa1.getDateCreation(), is(notNullValue()));
		assertThat(CustomDateUtil.isSameDay(resa1.getDateCreation(), LocalDateTime.now()), is(true));
		assertThat(resa1.getAppartements(), is(not(empty())));
		assertThat(resa1.getStatut(), is(EnumStatutReservation.ENREGISTREE.getStatut()));
		//Les date de checkin et checkout sont réspectivement à 12h et 11h (Aujourd'hui et demain)
		assertThat(true, is(CustomDateUtil.isSameDay(resa1.getDateCheckin(), LocalDateTime.now())));
		assertThat(resa1.getDateCheckin().plusHours(5), is(resa1.getDateCheckout()));	
		
	}
	

	/****************************************************
	 * **************************************************
	 * 
	 *  Test du service métier updateReservation()
	 *                                             
	 *  ************************************************
	 ***************************************************/

	/**
	 * Mise à jour d'une réservation 
	 * et pas encore créée en BDD.
	 * Vérifier qu'une exception technique est lancée.
	 * 
	 * @throws TechnicalException 
	 */
	@Test
	public void testUpdateReservationIdNull() throws TechnicalException {

		//Assert that an exception has thrown
		thrown.expect(TechnicalException.class);
		thrown.expectMessage(TechnicalErrorMessageConstants.ERREUR_ENTREE_MODIFICATION_NULL);

		ReservationDto resa = new ReservationDto();
		// Call service
		reservationService.updateReservation(resa);
	}
	
	
	/**
	 * Mise à jour d'une réservation 
	 * nulle.
	 * Vérifier qu'une exception technique est lancée.
	 * 
	 * @throws TechnicalException 
	 */
	@Test
	public void testUpdateReservationEntiteNull() throws TechnicalException {

		//Assert that an exception has thrown
		thrown.expect(TechnicalException.class);
		thrown.expectMessage(TechnicalErrorMessageConstants.ERREUR_ENTREE_MODIFICATION_NULL);

		// Call service
		reservationService.updateReservation(null);
	}
	
	/**
	 * Mise à jour d'une réservation 
	 * et vérification que les modifications
	 * ont bien été prises en compte.
	 * 
	 * @throws TechnicalException 
	 */
	@Test
	public void testUpdateReservation() throws TechnicalException {
		final String note = "Réduction de 5 euros pour un habitué";
		//Charger la réservation à mettre à jour
		ReservationDto reservation = reservationService.findReservationById(5L);
				
		//Check résa est bien chargée
		assertThat(reservation, is(notNullValue()));
		assertThat(reservation.getId(), is(5L));
		assertThat(reservation.getStatut(), is(EnumStatutReservation.ENREGISTREE.getStatut()));
		assertThat(reservation.getNote(), is(nullValue()));
		
		//Modification de la réserevation
		reservation.setNote(note);
		reservation.setStatut(EnumStatutReservation.CONFIRMEE.getStatut());
		
		// Adresse
		AdresseDto adresse = createAdresse("12 cité Fadia", null, 9900, "Sacré coeur", "Sénégal");

		// Bien
		BienDto bien = createBien("Wakeur Meissa", adresse);
		
		//Appartements
		AppartementDto app1 = createAppartement("Dalal Diam", bien, EnumTypeAppartement.T2.getType(), 50D);
		AppartementDto app2 = createAppartement("Dem Deloussi", bien, EnumTypeAppartement.T3.getType(), 70D);
		AppartementDto app3 = createAppartement("Tawfekh", bien, EnumTypeAppartement.STUDIO.getType(), 45D);
		
		reservation.setAppartements(Arrays.asList(app1, app2, app3));
		// Call service
		ReservationDto reservationUpdated = reservationService.updateReservation(reservation);
		
		//Check
		assertThat(reservationUpdated, is(notNullValue()));
		assertThat(reservationUpdated.getId(), is(5L));
		assertThat(reservationUpdated.getStatut(), is(EnumStatutReservation.CONFIRMEE.getStatut()));
		assertThat(reservationUpdated.getNote(), is(note));
		
	}
	

	/****************************************************
	 * **************************************************
	 * 
	 *  Test du service métier findReservationByCriteria()
	 *                                             
	 *  ************************************************
	 ***************************************************/
	
	/**
	 * Teste la recherche de réservation 
	 * par critères.
	 * Cas où aucun critère n'est saisi.
	 * 
	 * @throws TechnicalException 
	 */
	@Test
	public void testfindReservationByCriteriaAllCriteriaNull() throws TechnicalException {
		//Assert that an exception has thrown
		thrown.expect(FunctionalException.class);
		thrown.expectMessage(FunctionnalErrorMessageConstants.ERREUR_RESERVATION_PERIODE_INCORRECTE);

		// Call service : Une exception doit être lancée car la période 
		//de réservation est obligatoire
		reservationService.findReservationByCriteria(new ReservationCriteria());
		
	}
	
	
	/**
	 * Teste la recherche de réservation 
	 * par critères.
	 * Cas date checkin et date checkout
	 * 
	 * @throws TechnicalException 
	 */
	@Test
	public void testfindReservationByCriteriaPeriodeReservation() throws TechnicalException {
		ReservationCriteria criteria = new ReservationCriteria();
		
		// Call service : Periode dans reservations
		criteria.setDateCheckin(new LocalDateTime(2018, 9, 1, 0, 0));
		criteria.setDateCheckout(new LocalDateTime(2018, 12, 31, 0, 0));
		List<ReservationDto> results = reservationService.findReservationByCriteria(criteria);
		
		//Vérification des résultats
		assertThat(results, is(notNullValue()));
		assertThat(results, is(empty()));
		
		
		// Call service : Periode avec2 reservations
		criteria = new ReservationCriteria();
		criteria.setDateCheckin(new LocalDateTime(2018, 8, 1, 0, 0));
		criteria.setDateCheckout(new LocalDateTime(2018, 12, 31, 0, 0));
		results = reservationService.findReservationByCriteria(criteria);
		
		//Vérification des résultats
		assertThat(results, is(notNullValue()));
		assertThat(results, is(not(empty())));
		assertThat(results.size(), is(2));
		
		
		// Call service : Periode avec au moins 3 reservations
		criteria = new ReservationCriteria();
		criteria.setDateCheckin(new LocalDateTime(2018, 6, 3, 0, 0));
		criteria.setDateCheckout(new LocalDateTime(2018, 12, 31, 0, 0));
		results = reservationService.findReservationByCriteria(criteria);
		
		//Vérification des résultats
		assertThat(results, is(notNullValue()));
		assertThat(results, is(not(empty())));
		assertThat(results.size(), greaterThanOrEqualTo(3));
	}
	
	
	/**
	 * Teste la recherche de réservation 
	 * par critères.
	 * Cas reservations avec petit dej
	 * 
	 * @throws TechnicalException 
	 */
	@Test
	public void testfindReservationByCriteriaPetitDej() throws TechnicalException {
		ReservationCriteria criteria = new ReservationCriteria();
		
		criteria.setDateCheckin(new LocalDateTime(2018, 8, 1, 0, 0));
		criteria.setDateCheckout(new LocalDateTime(2018, 12, 31, 0, 0));
		criteria.setPetitDej(true);
	
		// Call service : Periode avec une seule reservation
		List<ReservationDto> results = reservationService.findReservationByCriteria(criteria);
		
		//Vérification des résultats
		assertThat(results, is(notNullValue()));
		assertThat(results, is(not(empty())));
		assertThat(results.size(), is(2));

		results.forEach(resa -> {
			
			//Vérification des résultats
			assertThat(resa, is(notNullValue()));
			assertThat(resa.getPetitDej(), is(true));
			assertThat(resa.getDateCheckin(), lessThanOrEqualTo(criteria.getDateCheckout()));
			assertThat(resa.getDateCheckout(), greaterThanOrEqualTo(criteria.getDateCheckin()));
		});
	}
	
	/**
	 * Teste la recherche de réservation 
	 * par critères.
	 * Cas recherche par statut reservation
	 * 
	 * @throws TechnicalException 
	 */
	@Test
	public void testfindReservationByCriteriaStatut() throws TechnicalException {
		ReservationCriteria criteria = new ReservationCriteria();
		
		criteria.setDateCheckin(new LocalDateTime(2018, 8, 1, 0, 0));
		criteria.setDateCheckout(new LocalDateTime(2018, 12, 31, 0, 0));
		criteria.setStatut(Arrays.asList(EnumStatutReservation.FACTUREE.getStatut()));
	
		List<ReservationDto> results = reservationService.findReservationByCriteria(criteria);
		
		//Vérification des résultats
		//Pas de reservation facturée dans cette période
		assertThat(results, is(notNullValue()));
		assertThat(results, is(empty()));
		
		//Appel sur une large période et les statuts "annulée facturée" et confirmée
		criteria = new ReservationCriteria();
		List<String> statuts = Arrays.asList(EnumStatutReservation.ANNULEE_FACTUREE.getStatut(),
				EnumStatutReservation.CONFIRMEE.getStatut());
		criteria.setDateCheckin(new LocalDateTime(2018, 6, 1, 0, 0));
		criteria.setDateCheckout(new LocalDateTime(2018, 12, 31, 0, 0));
		criteria.setStatut(statuts);
		
		// Call service : Une seule réservation "annulée facturée" existe dans la péridoe 
		results = reservationService.findReservationByCriteria(criteria);
		//Vérification des résultats
		assertThat(results, is(notNullValue()));
		assertThat(results, is(not(empty())));
		assertThat(results.size(), greaterThanOrEqualTo(2));

		results.forEach(resa -> {
			
			//Vérification des résultats
			assertThat(resa, is(notNullValue()));
			assertThat(statuts.contains(resa.getStatut()), is(true));
		});
	}
	
	
	/**
	 * Teste la recherche de réservation 
	 * par critères.
	 * Cas recherche par prix de la réservation
	 * 
	 * @throws TechnicalException 
	 */
	@Test
	public void testfindReservationByCriteriaPrix() throws TechnicalException {
		ReservationCriteria criteria = new ReservationCriteria();
		
		criteria = new ReservationCriteria();
		criteria.setDateCheckin(new LocalDateTime(2018, 6, 1, 0, 0));
		criteria.setDateCheckout(new LocalDateTime(2018, 12, 31, 0, 0));
		criteria.setPrix(900D);
		
		// Call service : Une seule réservation "annulée facturée" existe dans la péridoe 
		List<ReservationDto> results = reservationService.findReservationByCriteria(criteria);
		//Vérification des résultats
		assertThat(results, is(notNullValue()));
		assertThat(results, is(not(empty())));
		assertThat(results.size(), is(1));

		results.forEach(resa -> {
			
			//Vérification des résultats
			assertThat(resa, is(notNullValue()));
			assertThat(resa.getPrix(), is(900D));
		});
	}
	
	
	/**
	 * Teste la recherche de réservation 
	 * par critères.
	 * Cas recherche par identifiants appartements
	 * 
	 * @throws TechnicalException 
	 */
	@Test
	public void testfindReservationByCriteriaIdAppart() throws TechnicalException {
		ReservationCriteria criteria = new ReservationCriteria();
		
		criteria.setDateCheckin(new LocalDateTime(2018, 8, 1, 0, 0));
		criteria.setDateCheckout(new LocalDateTime(2018, 12, 31, 0, 0));
		criteria.setIdAppartements(Arrays.asList(100L, 800L));
	
		List<ReservationDto> results = reservationService.findReservationByCriteria(criteria);
		
		//Vérification des résultats
		//Pas d'id appartements existant
		assertThat(results, is(notNullValue()));
		assertThat(results, is(empty()));
		
		//Appel sur une large période avec un id appartement connu
		criteria = new ReservationCriteria();
		List<Long> appart = Arrays.asList(9999L);
		criteria.setDateCheckin(new LocalDateTime(2018, 6, 1, 0, 0));
		criteria.setDateCheckout(new LocalDateTime(2018, 12, 31, 0, 0));
		criteria.setIdAppartements(appart);
		
		// Call service : Une seule réservation "annulée facturée" existe dans la péridoe 
		results = reservationService.findReservationByCriteria(criteria);
		//Vérification des résultats
		assertThat(results, is(notNullValue()));
		assertThat(results, is(not(empty())));
		assertThat(results.size(), greaterThanOrEqualTo(5));
	}
	
	
	/**
	 * Teste la recherche de réservation 
	 * par critères.
	 * Cas recherche par identifiant client
	 * 
	 * @throws TechnicalException 
	 */
	@Test
	public void testfindReservationByCriteriaIdClient() throws TechnicalException {
		
		ReservationCriteria criteria = new ReservationCriteria();
		criteria.setDateCheckin(new LocalDateTime(2018, 1, 1, 0, 0));
		criteria.setDateCheckout(new LocalDateTime(2018, 12, 31, 0, 0));
		criteria.setIdClient(100L);
	
		//Lancr la recherche avec un client inconnu
		List<ReservationDto> results = reservationService.findReservationByCriteria(criteria);
		
		//Vérification des résultats
		assertThat(results, is(notNullValue()));
		assertThat(results, is(empty()));
		
		//Appel sur une large période avec un id appartement connu
		criteria = new ReservationCriteria();
		criteria.setDateCheckin(new LocalDateTime(2018, 6, 1, 0, 0));
		criteria.setDateCheckout(new LocalDateTime(2018, 12, 31, 0, 0));
		criteria.setIdClient(1L);
		
		// Call service 
		results = reservationService.findReservationByCriteria(criteria);
		//Vérification des résultats
		assertThat(results, is(notNullValue()));
		assertThat(results, is(not(empty())));
		assertThat(results.size(), greaterThanOrEqualTo(5));
	}
	
	
	/**
	 * Teste la recherche de réservation 
	 * par critères.
	 * Cas recherche par identifiant facture
	 * 
	 * @throws TechnicalException 
	 */
	@Test
	public void testfindReservationByCriteriaIdFacture() throws TechnicalException {
		
		// Adresse
		AdresseDto adresse = createAdresse("12 cité Fadia", null, 9900, "Sacré coeur", "Sénégal");
		AdresseDto adresse2 = createAdresse("18 rue carnot", null, 9900, "Plateau", "Sénégal");
		
		// Bien
		BienDto bien = createBien("Wakeur Meissa", adresse);
		
		//Appartements
		AppartementDto app1 = createAppartement("Dalal Diam", bien, EnumTypeAppartement.T2.getType(), 50D);
		AppartementDto app2 = createAppartement("Dem Deloussi", bien, EnumTypeAppartement.T3.getType(), 70D);
		AppartementDto app3 = createAppartement("Tawfekh", bien, EnumTypeAppartement.STUDIO.getType(), 45D);
		
		//Clients
		ClientDto client2 = createClient("Souane", "Amadou", "asoua@gmail.com", 
				"987654321", EnumTypePieceIdentite.PERMIS_CONDUIRE.getType(), "+33756487921", adresse2);
		
		//Facture
		FactureDto facture = createFacture(client2, 2D, adresse2, 20D, 15D);
		
		ReservationDto reservation = createReservation(new LocalDateTime(), new LocalDateTime().plusDays(3), "RAS", 
				true, EnumStatutReservation.CONFIRMEE.getStatut(), Arrays.asList(app2, app3), 
				165D, new LocalDateTime(), null, client2, facture);
		
		
		app1.setReservations(Arrays.asList(reservation));
		app2.setReservations(Arrays.asList(reservation));
		app3.setReservations(Arrays.asList(reservation));
		
		//Call services
		appartementService.createAppartement(app1);
		appartementService.createAppartement(app2);
		appartementService.createAppartement(app3);
		
		//Check results
		assertThat(reservation.getId(), is(notNullValue()));
		assertThat(reservation.getStatut(), is(EnumStatutReservation.CONFIRMEE.getStatut()));
		assertThat(true, is(CustomDateUtil.isSameDay(reservation.getDateCreation(), LocalDateTime.now())));
		
		//Recherche de la réservation facturée couvrant la période
		ReservationCriteria criteria = new ReservationCriteria();
		
		criteria.setDateCheckin(new LocalDateTime().minusDays(10));
		criteria.setDateCheckout(new LocalDateTime().plusDays(25));
		criteria.setIdFacture(facture.getId());
	
		List<ReservationDto> results = reservationService.findReservationByCriteria(criteria);
		
		//Vérification des résultats
		//seule la réservation liée à la facture créée ci-dessus est remontée.
		assertThat(results, is(notNullValue()));
		assertThat(results, is(not(empty())));
		assertThat(results.size(), is(1));
		ReservationDto resa = results.get(0);
		assertThat(resa.getFacture(), is(notNullValue()));
		assertThat(resa.getFacture().getId(), is(facture.getId()));
	}
	
	
	/**********************************************************
	 * ********************************************************
	 * 
	 *  Test du service métier findReservationByPeriodAndBien()
	 *                                             
	 *  *******************************************************
	 **********************************************************/
	
	
	/**
	 * Test du service de recherche de réservation 
	 * d'un Bien par période.
	 * Cas d'une date de début nulle.
	 * 
	 * @throws TechnicalException 
	 * @throws FunctionalException 
	 */
	@Test
	public void testFindReservationByPeriodAndBienDateDebutNull() throws FunctionalException, TechnicalException {
		//Assert that an exception has thrown
		thrown.expect(FunctionalException.class);
		thrown.expectMessage(FunctionnalErrorMessageConstants.ERREUR_RESERVATION_DATE_CHECKIN_NULL);
		
		//Call service: la date de début étant obligatoie, une exception doit être lancée
		reservationService.findReservationByPeriodAndBien(null, new LocalDateTime(), 1L);

	}
	
	
	/**
	 * Test du service de recherche de réservation 
	 * d'un Bien par période.
	 * Cas d'une date de fin nulle.
	 * 
	 * @throws TechnicalException 
	 * @throws FunctionalException 
	 */
	@Test
	public void testFindReservationByPeriodAndBienDateFinNull() throws FunctionalException, TechnicalException {
		//Assert that an exception has thrown
		thrown.expect(FunctionalException.class);
		thrown.expectMessage(FunctionnalErrorMessageConstants.ERREUR_RESERVATION_DATE_CHECKOUT_NULL);
		
		//Call service: la date de début étant obligatoie, une exception doit être lancée
		reservationService.findReservationByPeriodAndBien(new LocalDateTime(), null, 1L);

	}
	
	
	/**
	 * Test du service de recherche de réservation 
	 * d'un Bien par période.
	 * Cas d'une date de début postérieure à la date de fin.
	 * 
	 * @throws TechnicalException 
	 * @throws FunctionalException 
	 */
	@Test
	public void testFindReservationByPeriodAndBienDateDebutPosterieureDateFin() throws FunctionalException, TechnicalException {
		//Assert that an exception has thrown
		thrown.expect(FunctionalException.class);
		thrown.expectMessage(FunctionnalErrorMessageConstants.ERREUR_RESERVATION_DATE_CHECKOUT_INCORRECT);
		
		//Call service: la date de début étant obligatoie, une exception doit être lancée
		reservationService.findReservationByPeriodAndBien(new LocalDateTime(), new LocalDateTime().minusDays(5), 1L);
	}
	
	/**
	 * Test du service de recherche de réservation 
	 * d'un Bien par période.
	 * Cas d'une d'un bien null.
	 * 
	 * @throws TechnicalException 
	 * @throws FunctionalException 
	 */
	@Test
	public void testFindReservationByPeriodAndBienIdBienNull() throws FunctionalException, TechnicalException {
		//Assert that an exception has thrown
		thrown.expect(FunctionalException.class);
		thrown.expectMessage(FunctionnalErrorMessageConstants.ERREUR_RESERVATION_BIEN_NULL);
		
		//Call service: la date de début étant obligatoie, une exception doit être lancée
		reservationService.findReservationByPeriodAndBien(new LocalDateTime(), new LocalDateTime().plusDays(5), null);
	}
	
	/**
	 * Test du service de recherche de réservation 
	 * d'un Bien par période.
	 * Cas d'une période couvrant plus de 30 jours.
	 * 
	 * @throws TechnicalException 
	 * @throws FunctionalException 
	 */
	@Test
	public void testFindReservationByPeriodAndBienPeriodeTropLarge() throws FunctionalException, TechnicalException {
		//Assert that an exception has thrown
		thrown.expect(FunctionalException.class);
		thrown.expectMessage(FunctionnalErrorMessageConstants.ERREUR_RESERVATION_PERIODE_SUP_30_JOURS);
		
		//Call service: la date de début étant obligatoie, une exception doit être lancée
		reservationService.findReservationByPeriodAndBien(new LocalDateTime(), new LocalDateTime().plusDays(31), 1L);

	}
	
	
	/**
	 * Test du service de recherche de réservation 
	 * d'un Bien par période.
	 * 
	 * @throws TechnicalException 
	 * @throws FunctionalException 
	 */
	@Test
	public void testFindReservationByPeriodAndBien() throws FunctionalException, TechnicalException {
		//Call service
		Map<Long, List<Reservation>> mapAppartementsReservations = reservationService.findReservationByPeriodAndBien(new LocalDateTime(2019, 1, 1, 0, 0),
				new LocalDateTime(2019, 1, 30, 0, 0), 2L);

		//Vérification des résultats
		
		assertThat(mapAppartementsReservations, is(notNullValue()));
		assertThat(mapAppartementsReservations.isEmpty(), is(false));
		assertThat(mapAppartementsReservations.size(), is(4));
		assertThat(mapAppartementsReservations.keySet(), containsInAnyOrder(1L, 2L, 3L, 4L));
		
		//Appartement 1
		List<Reservation> reservations = mapAppartementsReservations.get(1L);
		assertThat(reservations, is(notNullValue()));
		assertThat(reservations, is(not(empty())));
		assertThat(reservations.size(), is(1));
		assertThat(reservations.get(0).getDateCheckin(), is(new LocalDateTime(2019, 1, 1, 12, 0)));
		assertThat(reservations.get(0).getDateCheckout(), is(new LocalDateTime(2019, 2, 28, 11, 0)));
		
		//Appartement 2
		reservations = mapAppartementsReservations.get(2L);
		assertThat(reservations, is(notNullValue()));
		assertThat(reservations, is(not(empty())));
		assertThat(reservations.size(), is(2));
		assertThat(reservations.get(0).getDateCheckin(), is(new LocalDateTime(2019, 1, 8, 12, 0)));
		assertThat(reservations.get(0).getDateCheckout(), is(new LocalDateTime(2019, 1, 10, 11, 0)));
		assertThat(reservations.get(1).getDateCheckin(), is(new LocalDateTime(2019, 1, 9, 12, 0)));
		assertThat(reservations.get(1).getDateCheckout(), is(new LocalDateTime(2019, 1, 11, 11, 0)));
		
		//Appartement 3
		reservations = mapAppartementsReservations.get(3L);
		assertThat(reservations, is(notNullValue()));
		assertThat(reservations, is(not(empty())));
		assertThat(reservations.size(), is(1));
		assertThat(reservations.get(0).getDateCheckin(), is(new LocalDateTime(2019, 1, 9, 12, 0)));
		assertThat(reservations.get(0).getDateCheckout(), is(new LocalDateTime(2019, 1, 9, 17, 0)));
		
		//Appartement 4
		reservations = mapAppartementsReservations.get(4L);
		assertThat(reservations, is(notNullValue()));
		assertThat(reservations, is(not(empty())));
		assertThat(reservations.size(), is(1));
		assertThat(reservations.get(0).getDateCheckin(), is(new LocalDateTime(2019, 1, 15, 17, 0)));
		assertThat(reservations.get(0).getDateCheckout(), is(new LocalDateTime(2019, 1, 15, 22, 0)));
		
	}
	
	
	/****************************************************
	 * **************************************************
	 * 
	 *  Test du service métier loadSchedule()
	 *                                             
	 *  ************************************************
	 ***************************************************/
	
	
	/**
	 * Test du service de chargement du planning de réservation 
	 * pour un Bien par période.
	 * 
	 * @throws TechnicalException 
	 * @throws FunctionalException 
	 * 
	 */
	@Test
	public void testLoadSchedule() throws FunctionalException, TechnicalException {
		//Call service
		Map<Long, List<UniteReservation>> planning = reservationService.loadSchedule(new LocalDateTime(2019, 1, 1, 0, 0),
				new LocalDateTime(2019, 1, 30, 0, 0), 2L);
		
		//Vérification des résultats
		
		//Le bien porte 5 appartements, un planning doit être affiché
		//impliquant ces 5 appartements
		assertThat(planning, is(notNullValue()));
		assertThat(planning.isEmpty(), is(false));
		assertThat(planning.size(), is(6));
		assertThat(planning.keySet(), containsInAnyOrder(1L, 2L, 3L, 4L, 5L, 6L));
		
		//Appartement 1
		List<UniteReservation> journeeReservation = planning.get(1L);
		assertThat(journeeReservation, is(notNullValue()));
		assertThat(journeeReservation, is(not(empty())));
		assertThat(journeeReservation.size(), is(30));
		
		//L'appel a été effectué avec une période de 30 jours 
		//Il faut donc vérifier qu'une cellule de réservation est créée pour chaque journée
		for(int index = 0; index < 30; index++){
			assertThat(journeeReservation.get(index).getJournee(), is(notNullValue()));
			assertThat(journeeReservation.get(index).getJournee(), is(new LocalDate(2019, 1, index +1)));
			assertThat(journeeReservation.get(index).getNuite(), is(notNullValue()));
			assertThat(journeeReservation.get(index).getNuite().dateCheckin(), is(notNullValue()));
			assertThat(journeeReservation.get(index).getNuite().dateCheckin(), is(new LocalDateTime(2019, 1, index +1, 12, 0)));
			assertThat(journeeReservation.get(index).getNuite().dateCheckout(), is(notNullValue()));
			assertThat(journeeReservation.get(index).getNuite().dateCheckout(), is(new LocalDateTime(2019, 1, index +2, 11, 0)));
			
			//Pour l'appart 1, il existe une réservation pour tous les 30 jours
			assertThat(journeeReservation.get(index).getIdReservations(), is(not(empty())));
		}
		
		//Appartement 2
		journeeReservation = planning.get(2L);
		assertThat(journeeReservation, is(notNullValue()));
		assertThat(journeeReservation, is(not(empty())));
		assertThat(journeeReservation.size(), is(30));

		for(int index = 0; index < 30; index++){
			assertThat(journeeReservation.get(index).getJournee(), is(notNullValue()));
			assertThat(journeeReservation.get(index).getJournee(), is(new LocalDate(2019, 1, index +1)));
			assertThat(journeeReservation.get(index).getNuite(), is(notNullValue()));
			assertThat(journeeReservation.get(index).getNuite().dateCheckin(), is(notNullValue()));
			assertThat(journeeReservation.get(index).getNuite().dateCheckin(), is(new LocalDateTime(2019, 1, index +1, 12, 0)));
			assertThat(journeeReservation.get(index).getNuite().dateCheckout(), is(notNullValue()));
			assertThat(journeeReservation.get(index).getNuite().dateCheckout(), is(new LocalDateTime(2019, 1, index +2, 11, 0)));
		
			//Pour l'appart 2, il existe une réservation du 08 au 11 uniquement
			if(Arrays.asList(7, 8, 9).contains(index)) {
			assertThat(journeeReservation.get(index).getIdReservations(), is(not(empty())));
			}else {
				assertThat(journeeReservation.get(index).getIdReservations(), is(empty()));
			}
		}
		
		//Appartement 3
		journeeReservation = planning.get(3L);
		assertThat(journeeReservation, is(notNullValue()));
		assertThat(journeeReservation, is(not(empty())));
		assertThat(journeeReservation.size(), is(30));

		for(int index = 0; index < 30; index++){
			assertThat(journeeReservation.get(index).getJournee(), is(notNullValue()));
			assertThat(journeeReservation.get(index).getJournee(), is(new LocalDate(2019, 1, index +1)));
			assertThat(journeeReservation.get(index).getNuite(), is(notNullValue()));
			assertThat(journeeReservation.get(index).getNuite().dateCheckin(), is(notNullValue()));
			assertThat(journeeReservation.get(index).getNuite().dateCheckin(), is(new LocalDateTime(2019, 1, index +1, 12, 0)));
			assertThat(journeeReservation.get(index).getNuite().dateCheckout(), is(notNullValue()));
			assertThat(journeeReservation.get(index).getNuite().dateCheckout(), is(new LocalDateTime(2019, 1, index +2, 11, 0)));
			
			//Pour l'appart 3, il existe une réservation uniquement le 9
			if(index == 8) {
			assertThat(journeeReservation.get(index).getIdReservations(), is(not(empty())));
			}else {
				assertThat(journeeReservation.get(index).getIdReservations(), is(empty()));
			}
		}
		
		//Appartement 4
		journeeReservation = planning.get(4L);
		assertThat(journeeReservation, is(notNullValue()));
		assertThat(journeeReservation, is(not(empty())));
		assertThat(journeeReservation.size(), is(30));
		
		for(int index = 0; index < 30; index++){
			assertThat(journeeReservation.get(index).getJournee(), is(notNullValue()));
			assertThat(journeeReservation.get(index).getJournee(), is(new LocalDate(2019, 1, index +1)));
			assertThat(journeeReservation.get(index).getNuite(), is(notNullValue()));
			assertThat(journeeReservation.get(index).getNuite().dateCheckin(), is(notNullValue()));
			assertThat(journeeReservation.get(index).getNuite().dateCheckin(), is(new LocalDateTime(2019, 1, index +1, 12, 0)));
			assertThat(journeeReservation.get(index).getNuite().dateCheckout(), is(notNullValue()));
			assertThat(journeeReservation.get(index).getNuite().dateCheckout(), is(new LocalDateTime(2019, 1, index +2, 11, 0)));
			
			//Pour l'appart 4, il existe une réservation uniquement le 15
			if(index == 14) {
			assertThat(journeeReservation.get(index).getIdReservations(), is(not(empty())));
			}else {
				assertThat(journeeReservation.get(index).getIdReservations(), is(empty()));
			}
		}
		
		//Appartement 5
		journeeReservation = planning.get(5L);
		assertThat(journeeReservation, is(notNullValue()));
		assertThat(journeeReservation, is(not(empty())));
		assertThat(journeeReservation.size(), is(30));

		for(int index = 0; index < 30; index++){
			assertThat(journeeReservation.get(index).getJournee(), is(notNullValue()));
			assertThat(journeeReservation.get(index).getJournee(), is(new LocalDate(2019, 1, index +1)));
			assertThat(journeeReservation.get(index).getNuite(), is(notNullValue()));
			assertThat(journeeReservation.get(index).getNuite().dateCheckin(), is(notNullValue()));
			assertThat(journeeReservation.get(index).getNuite().dateCheckin(), is(new LocalDateTime(2019, 1, index +1, 12, 0)));
			assertThat(journeeReservation.get(index).getNuite().dateCheckout(), is(notNullValue()));
			assertThat(journeeReservation.get(index).getNuite().dateCheckout(), is(new LocalDateTime(2019, 1, index +2, 11, 0)));
			
			//Pour l'appart 5, il existe une réservation du 30 au 31 uniquement
			//Mais ne fait pas partie de la plage du planning à afficher. Ainsi pour toutes,
			//la période aucune reservation n'est trouver.
			assertThat(journeeReservation.get(index).getIdReservations(), is(empty()));
		}	
		
		//Appartement 6
		journeeReservation = planning.get(6L);
		assertThat(journeeReservation, is(notNullValue()));
		assertThat(journeeReservation, is(not(empty())));
		assertThat(journeeReservation.size(), is(30));

		for(int index = 0; index < 30; index++){
			assertThat(journeeReservation.get(index).getJournee(), is(notNullValue()));
			assertThat(journeeReservation.get(index).getJournee(), is(new LocalDate(2019, 1, index +1)));
			assertThat(journeeReservation.get(index).getNuite(), is(notNullValue()));
			assertThat(journeeReservation.get(index).getNuite().dateCheckin(), is(notNullValue()));
			assertThat(journeeReservation.get(index).getNuite().dateCheckin(), is(new LocalDateTime(2019, 1, index +1, 12, 0)));
			assertThat(journeeReservation.get(index).getNuite().dateCheckout(), is(notNullValue()));
			assertThat(journeeReservation.get(index).getNuite().dateCheckout(), is(new LocalDateTime(2019, 1, index +2, 11, 0)));
			
			//Pour l'appart 6, il existe une réservation du 30 au 31 uniquement
			assertThat(journeeReservation.get(index).getIdReservations(), is(empty()));
		}
	}
}
