/**
 * 
 */
package com.ag2m.gestimmo.metier.serviceImpl;

import static org.junit.Assert.assertThat;

import java.util.Arrays;

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

import static org.hamcrest.core.IsNull.*;
import static org.hamcrest.core.Is.*;

/**
 * @author mombaye
 *
 */
public class ReservationServiceImplTest extends AbstractCommonTest{

	@Test
	public void testSaveOrUpdate() {
		
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
						true, EnumStatutReservation.ENREGISTREE.getStatut(), Arrays.asList(app1),
						50D, new LocalDateTime(), null, client, null);
		
		ReservationDto reservation2 = createReservation(new LocalDateTime(), new LocalDateTime().plusDays(3), "RAS", 
				true, EnumStatutReservation.ANNULEE.getStatut(), Arrays.asList(app2, app3), 
				120D, new LocalDateTime(), new LocalDateTime().plusDays(2), client2, facture);
		
		
		app1.setReservations(Arrays.asList(reservation1));
		app2.setReservations(Arrays.asList(reservation1, reservation2));
		app3.setReservations(Arrays.asList(reservation1, reservation2));
		
		//Call services
		appartementService.saveOrUpdate(app1);
		appartementService.saveOrUpdate(app2);
		appartementService.saveOrUpdate(app3);
		
		//Check results
		assertThat(reservation1.getId(), is(notNullValue()));
		assertThat(reservation2.getId(), is(notNullValue()));
		
	}
	
	
	@Test
	public void testDelete() {
		
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
				true, EnumStatutReservation.ANNULEE.getStatut(), Arrays.asList(app2, app3), 
				120D, new LocalDateTime(), new LocalDateTime().plusDays(2), client2, facture);		
		
		
		app1.setReservations(Arrays.asList(reservation1));
		app2.setReservations(Arrays.asList(reservation1, reservation2));
		app3.setReservations(Arrays.asList(reservation1, reservation2));
		
		appartementService.saveOrUpdate(app1);
		appartementService.saveOrUpdate(app2);
		appartementService.saveOrUpdate(app3);
		
		assertThat(reservation1.getId(), is(notNullValue()));
		assertThat(reservation2.getId(), is(notNullValue()));
		
		appartementService.delete(app2);
		
		ReservationDto resa1 = reservationService.findById(reservation2.getId());
		ReservationDto resa2 = reservationService.findById(reservation2.getId());
		ReservationDto resa3 = reservationService.findById(reservation1.getId());
		
		assertThat(resa1, is(nullValue()));
		assertThat(resa2, is(nullValue()));
		assertThat(resa3, is(nullValue()));
	}
}
