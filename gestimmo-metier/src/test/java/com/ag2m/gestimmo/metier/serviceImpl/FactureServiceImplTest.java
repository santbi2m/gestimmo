/**
 * 
 */
package com.ag2m.gestimmo.metier.serviceImpl;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joda.time.LocalDateTime;
import org.junit.Test;

import com.ag2m.gestimmo.metier.dto.AdresseDto;
import com.ag2m.gestimmo.metier.dto.AppartementDto;
import com.ag2m.gestimmo.metier.dto.BienDto;
import com.ag2m.gestimmo.metier.dto.ClientDto;
import com.ag2m.gestimmo.metier.dto.FactureDto;
import com.ag2m.gestimmo.metier.dto.ReservationDto;
import com.ag2m.gestimmo.metier.enumeration.EnumStatutReservation;
import com.ag2m.gestimmo.metier.enumeration.EnumTypeAppartement;
import com.ag2m.gestimmo.metier.enumeration.EnumTypePieceIdentite;
import com.ag2m.gestimmo.metier.exception.FunctionalException;
import com.ag2m.gestimmo.metier.exception.TechnicalException;
import com.ag2m.gestimmo.metier.ioparam.FactureCriteria;
import com.ag2m.gestimmo.metier.ioparam.ReservationCriteria;

/**
 * @author mombaye
 *
 */
public class FactureServiceImplTest extends AbstractCommonTest {

	@Test
	public void testFindAll() throws TechnicalException {

		// Adresse
		AdresseDto adresse = createAdresse("120 cité Azur", null, 9900, "Mermoz", "Sénégal");
		AdresseDto adresse2 = createAdresse("150 avenue Malick Sy", null, 9900, "Point E", "Sénégal");

		ClientDto client2 = createClient("Safal", "Adja", "adja@gmail.com", "425SD25", "CNI", "0748251540", adresse2);

		// Bien
		BienDto bien = createBien("Wakeur Meissa", adresse);
		// Appartements
		AppartementDto app1 = createAppartement("Dalal Diam", bien, EnumTypeAppartement.T2.getType(), 50D);
		AppartementDto app2 = createAppartement("Dem Deloussi", bien, EnumTypeAppartement.T3.getType(), 70D);
		AppartementDto app3 = createAppartement("Tawfekh", bien, EnumTypeAppartement.STUDIO.getType(), 45D);

		ReservationDto reservation = createReservation(new LocalDateTime(), new LocalDateTime().plusDays(3), "RAS",
				true, EnumStatutReservation.CONFIRMEE.getStatut(), Arrays.asList(app2, app3), 165D, new LocalDateTime(),
				null, client2, null);
		ReservationDto reservation1 = createReservation(new LocalDateTime().plusDays(3),
				new LocalDateTime().plusDays(5), "RAS", true, EnumStatutReservation.EN_ATTENTE.getStatut(),
				Arrays.asList(app2, app3), 165D, new LocalDateTime(), null, client2, null);
		ReservationDto reservation2 = createReservation(new LocalDateTime().plusDays(5),
				new LocalDateTime().plusDays(8), "RAS", true, EnumStatutReservation.ENREGISTREE.getStatut(),
				Arrays.asList(app1, app3), 165D, new LocalDateTime(), null, client2, null);
		app1.setReservations(Arrays.asList(reservation2));
		app2.setReservations(Arrays.asList(reservation, reservation1));
		app3.setReservations(Arrays.asList(reservation1, reservation2));

		// Call services
		appartementService.createAppartement(app1);
		appartementService.createAppartement(app2);
		appartementService.createAppartement(app3);

		// Facture
		FactureDto facture = new FactureDto();
		FactureDto facture1 = new FactureDto();
		FactureDto facture2 = new FactureDto();

		facture.setReservations(Arrays.asList(reservation));
		facture.setClient(client2);
		facture.setAdresseFacturation(adresse);
		facture.setRemise(2D);
		facture.setTaxeSejour(20D);
		factureService.createFacture(facture);
		facture1.setReservations(Arrays.asList(reservation1));
		facture1.setClient(client2);
		facture1.setAdresseFacturation(adresse);
		facture1.setRemise(2D);
		facture1.setTaxeSejour(20D);
		factureService.createFacture(facture1);
		facture2.setReservations(Arrays.asList(reservation2));
		facture2.setClient(client2);
		facture2.setAdresseFacturation(adresse);
		facture2.setRemise(2D);
		facture2.setTaxeSejour(20D);
		factureService.createFacture(facture2);
		// Call services
		List<FactureDto> factures = factureService.loadAllFactures();

		// Check results
		assertThat(factures, is(notNullValue()));
		assertThat(factures.size(), greaterThanOrEqualTo(2));

		// Check cache
		assertThat(cacheManager.getObject().getCache("gestimmo").getSize(), greaterThanOrEqualTo(2));

	}

	@Test
	public void testCreateFacture() throws TechnicalException {

		// Adresse
		AdresseDto adresse = createAdresse("124 cité promocap", "2ème porte", 9900, "Petit Mbao", "Sénégal");

		// Bien
		BienDto bien = createBien("Wakeur Meissa", adresse);
		// Appartements
		AppartementDto app1 = createAppartement("Dalal Diam", bien, EnumTypeAppartement.T2.getType(), 50D);
		AppartementDto app2 = createAppartement("Dem Deloussi", bien, EnumTypeAppartement.T3.getType(), 70D);
		AppartementDto app3 = createAppartement("Tawfekh", bien, EnumTypeAppartement.STUDIO.getType(), 45D);

		// Clients
		ClientDto client2 = createClient("Souane", "Amadou", "asoua@gmail.com", "987654321",
				EnumTypePieceIdentite.PERMIS_CONDUIRE.getType(), "+33756487921", adresse);

		// Facture
		FactureDto facture = new FactureDto();
		FactureDto facture1 = new FactureDto();
		FactureDto facture2 = new FactureDto();

		ReservationDto reservation = createReservation(new LocalDateTime(), new LocalDateTime().plusDays(3), "RAS",
				true, EnumStatutReservation.CONFIRMEE.getStatut(), Arrays.asList(app2, app3), 165D, new LocalDateTime(),
				null, client2, null);
		ReservationDto reservation1 = createReservation(new LocalDateTime().plusDays(3),
				new LocalDateTime().plusDays(5), "RAS", true, EnumStatutReservation.EN_ATTENTE.getStatut(),
				Arrays.asList(app2, app3), 165D, new LocalDateTime(), null, client2, null);
		ReservationDto reservation2 = createReservation(new LocalDateTime().plusDays(5),
				new LocalDateTime().plusDays(8), "RAS", true, EnumStatutReservation.ENREGISTREE.getStatut(),
				Arrays.asList(app1, app3), 165D, new LocalDateTime(), null, client2, null);

		reservationService.cancelReservation(reservation2);

		app1.setReservations(Arrays.asList(reservation2));
		app2.setReservations(Arrays.asList(reservation, reservation1));
		app3.setReservations(Arrays.asList(reservation1, reservation2));

		// Call services
		appartementService.createAppartement(app1);
		appartementService.createAppartement(app2);
		appartementService.createAppartement(app3);

		/*
		 * facture = createFacture(client2, 2D, adresse, 20D, 15D); facture1 =
		 * createFacture(client2, 2D, adresse, 20D, 15D); facture2 =
		 * createFacture(client2, 2D, adresse, 20D, 15D);
		 */

		facture.setReservations(Arrays.asList(reservation));
		facture.setClient(client2);
		facture.setAdresseFacturation(adresse);
		facture.setRemise(2D);
		facture.setTaxeSejour(20D);
		FactureDto fact = factureService.createFacture(facture);
		facture1.setReservations(Arrays.asList(reservation1));
		facture1.setClient(client2);
		facture1.setAdresseFacturation(adresse);
		facture1.setRemise(2D);
		facture1.setTaxeSejour(20D);
		FactureDto fact1 = factureService.createFacture(facture1);
		facture2.setReservations(Arrays.asList(reservation2));
		facture2.setClient(client2);
		facture2.setAdresseFacturation(adresse);
		facture2.setRemise(2D);
		facture2.setTaxeSejour(20D);
		FactureDto fact2 = factureService.createFacture(facture2);

		// Call service
		// facture = factureService.findFactureById(facture.getId());
		// Call services
		List<FactureDto> factures = new ArrayList<>();
		factures.add(factureService.findFactureById(fact.getId()));
		factures.add(factureService.findFactureById(fact1.getId()));
		factures.add(factureService.findFactureById(fact2.getId()));

		/// Check results
		assertThat(factures, is(notNullValue()));
		assertThat(factures.size(), greaterThanOrEqualTo(3));
		List<ReservationDto> reservationDtos = new ArrayList<>();
		factures.forEach(factureDto -> {
			List<ReservationDto> reservationDtos2 = new ArrayList<>();
			ReservationCriteria reservationCriteria = new ReservationCriteria();
			reservationCriteria.setIdFacture(factureDto.getId());
			reservationCriteria.setDateCheckin(new LocalDateTime());
			reservationCriteria.setDateCheckout(new LocalDateTime().plusDays(10));
			try {
				reservationDtos2.addAll(reservationService.findReservationByCriteria(reservationCriteria));
			} catch (FunctionalException e) {
				e.printStackTrace();
			} catch (TechnicalException e) {
				e.printStackTrace();
			}

			reservationDtos.add(reservationDtos2.get(0));
		});

		assertThat(reservationDtos, is(notNullValue()));
		assertThat(reservationDtos.size(), greaterThanOrEqualTo(3));

		assertThat(reservationDtos.get(0).getStatut(), is(EnumStatutReservation.FACTUREE.getStatut()));
		assertThat(reservationDtos.get(1).getStatut(), is(EnumStatutReservation.EN_ATTENTE_FACTUREE.getStatut()));
		assertThat(reservationDtos.get(2).getStatut(), is(EnumStatutReservation.ANNULEE_FACTUREE.getStatut()));

		// Check cache
		assertThat(cacheManager.getObject().getCache("gestimmo").getSize(), greaterThanOrEqualTo(3));

	}

	/**
	 * Tester le service findFactureByCriteria sans critères d'entrée
	 * 
	 * @throws TechnicalException
	 * 
	 * @throws FunctionalException
	 */

	@Test
	public void testFindFactureById() throws TechnicalException {

		// Adresse
		AdresseDto adresse = createAdresse("12 cité Fadia", null, 9900, "Sacré coeur", "Sénégal");
		AdresseDto adresse2 = createAdresse("18 rue carnot", null, 9900, "Plateau", "Sénégal");

		// Bien
		BienDto bien = createBien("Wakeur Meissa", adresse);
		// Appartements
		AppartementDto app1 = createAppartement("Dalal Diam", bien, EnumTypeAppartement.T2.getType(), 50D);
		AppartementDto app2 = createAppartement("Dem Deloussi", bien, EnumTypeAppartement.T3.getType(), 70D);
		AppartementDto app3 = createAppartement("Tawfekh", bien, EnumTypeAppartement.STUDIO.getType(), 45D);

		// Clients
		ClientDto client2 = createClient("Souane", "Amadou", "asoua@gmail.com", "987654321",
				EnumTypePieceIdentite.PERMIS_CONDUIRE.getType(), "+33756487921", adresse2);

		// Facture
		FactureDto facture = new FactureDto();

		ReservationDto reservation = createReservation(new LocalDateTime(), new LocalDateTime().plusDays(3), "RAS",
				true, EnumStatutReservation.CONFIRMEE.getStatut(), Arrays.asList(app2, app3), 165D, new LocalDateTime(),
				null, client2, null);

		app1.setReservations(Arrays.asList(reservation));
		app2.setReservations(Arrays.asList(reservation));
		app3.setReservations(Arrays.asList(reservation));

		// Call services
		appartementService.createAppartement(app1);
		appartementService.createAppartement(app2);
		appartementService.createAppartement(app3);

		facture.setReservations(Arrays.asList(reservation));
		facture.setClient(client2);
		facture.setAdresseFacturation(adresse);
		facture.setRemise(2D);
		facture.setTaxeSejour(20D);
		FactureDto fDto = factureService.createFacture(facture);

		FactureDto result = factureService.findFactureById(fDto.getId());

		// Check
		assertThat(result, is(notNullValue()));
		assertThat(result.getId(), is(notNullValue()));
	}

	/**
	 * Tester le service findFacturetByCriteria avec le libellé comme critère
	 * d'entrée
	 * 
	 * @throws TechnicalException
	 * 
	 * @throws FunctionalException
	 */

	@Test
	public void testFindFactureByCriteriaLibelle() throws TechnicalException {

		FactureCriteria bienCriteria = new FactureCriteria();
		// Adresse
		AdresseDto adresse = createAdresse("12 cité Fadia", null, 9900, "Sacré coeur", "Sénégal");
		AdresseDto adresse2 = createAdresse("18 rue carnot", null, 9900, "Plateau", "Sénégal");

		// Bien
		BienDto bien = createBien("Wakeur Meissa", adresse);
		// Appartements
		AppartementDto app1 = createAppartement("Dalal Diam", bien, EnumTypeAppartement.T2.getType(), 50D);
		AppartementDto app2 = createAppartement("Dem Deloussi", bien, EnumTypeAppartement.T3.getType(), 70D);
		AppartementDto app3 = createAppartement("Tawfekh2", bien, EnumTypeAppartement.STUDIO.getType(), 45D);

		// Clients
		ClientDto client2 = createClient("Souane", "Amadou", "asoua@gmail.com", "987654321",
				EnumTypePieceIdentite.PERMIS_CONDUIRE.getType(), "+33756487921", adresse2);

		// Facture
		FactureDto facture = new FactureDto();

		ReservationDto reservation = createReservation(new LocalDateTime(), new LocalDateTime().plusDays(3), "RAS",
				true, EnumStatutReservation.CONFIRMEE.getStatut(), Arrays.asList(app2, app3), 165D, new LocalDateTime(),
				null, client2, null);

		app1.setReservations(Arrays.asList(reservation));
		app2.setReservations(Arrays.asList(reservation));
		app3.setReservations(Arrays.asList(reservation));

		// Call services
		appartementService.createAppartement(app1);
		appartementService.createAppartement(app2);
		appartementService.createAppartement(app3);

		facture.setReservations(Arrays.asList(reservation));
		facture.setClient(client2);
		facture.setAdresseFacturation(adresse);
		facture.setRemise(2D);
		facture.setTaxeSejour(20D);
		factureService.createFacture(facture);

		/************
		 * ************ Case parametter repected * *
		 ************/
		// Call service
		bienCriteria.setLibelle("Tawfekh2");
		List<FactureDto> result = factureService.findFactureByCriteria(bienCriteria);

		// Check
		assertThat(result, is(notNullValue()));
		assertThat(result, is(not(empty())));
		assertThat(result.size(), is(1));
		assertThat(result.get(0).getRemise(), is(2D));

	}

	/**
	 * Tester le service findFactureByCriteria avec les champs dde l' adresse
	 * comme critère d'entrée
	 * 
	 * @throws TechnicalException
	 * 
	 * @throws FunctionalException
	 */

	@Test
	public void testFindFactureByCriteriaDateChekIn_Out() throws TechnicalException {

		FactureCriteria factureCriteria = new FactureCriteria();
		// Adresse
		AdresseDto adresse = createAdresse("12 cité Fadia", null, 9900, "Sacré coeur", "Sénégal");
		AdresseDto adresse2 = createAdresse("18 rue carnot", null, 9900, "Plateau", "Sénégal");

		// Bien
		BienDto bien = createBien("Wakeur Meissa", adresse);
		// Appartements
		AppartementDto app2 = createAppartement("Dem Deloussi", bien, EnumTypeAppartement.T3.getType(), 70D);
		AppartementDto app3 = createAppartement("Tawfekh3", bien, EnumTypeAppartement.STUDIO.getType(), 45D);

		// Clients
		ClientDto client2 = createClient("Souane", "Amadou", "asoua@gmail.com", "987654321",
				EnumTypePieceIdentite.PERMIS_CONDUIRE.getType(), "+33756487921", adresse2);

		// Facture
		FactureDto facture = new FactureDto();

		ReservationDto reservation = createReservation(new LocalDateTime(2018, 8, 1, 0, 0), new LocalDateTime(2018, 8, 5, 0, 0), "RAS",
				true, EnumStatutReservation.CONFIRMEE.getStatut(), Arrays.asList(app2, app3), 165D, new LocalDateTime(),
				null, client2, null);
		
		//app2.setReservations(Arrays.asList(reservation));
		app3.setReservations(Arrays.asList(reservation));

		// Call services
		appartementService.createAppartement(app2);
		appartementService.createAppartement(app3);

		facture.setReservations(Arrays.asList(reservation));
		facture.setClient(client2);
		facture.setAdresseFacturation(adresse);
		facture.setRemise(200D);
		facture.setTaxeSejour(20D);
		factureService.createFacture(facture);

		/************
		 * ************ Case parametter repected * *
		 ************/
		// Call service
		factureCriteria = new FactureCriteria();
		factureCriteria.setLibelle("Tawfekh3");
		factureCriteria.setDateCheckIn(new LocalDateTime(2018, 8, 1, 0, 0));
		List<FactureDto> result = factureService.findFactureByCriteria(factureCriteria);

		// Check
		assertThat(result, is(notNullValue()));
		assertThat(result, is(not(empty())));
		assertThat(result.size(), is(greaterThanOrEqualTo(1)));

		/************
		 * ************ Parametter in Lower case * *
		 ************/
		// Call service
		factureCriteria = new FactureCriteria();
		factureCriteria.setDateCheckOut(new LocalDateTime(2018, 8, 5, 0, 0));
		result = factureService.findFactureByCriteria(factureCriteria);

		// Check
		assertThat(result, is(notNullValue()));
		assertThat(result, is(not(empty())));
		assertThat(result.size(), is(greaterThanOrEqualTo(1)));
		assertThat(result.get(0).getClient().getNom(), is("Souane"));

	}

}
