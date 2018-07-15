/**
 * 
 */
package com.ag2m.gestimmo.metier.serviceImpl;



import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.*;

import java.util.Arrays;

import org.joda.time.LocalDateTime;

import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.ag2m.gestimmo.metier.dto.AdresseDto;
import com.ag2m.gestimmo.metier.dto.AppartementDto;
import com.ag2m.gestimmo.metier.dto.BienDto;
import com.ag2m.gestimmo.metier.dto.ClientDto;
import com.ag2m.gestimmo.metier.dto.DevisDto;
import com.ag2m.gestimmo.metier.dto.FactureDto;
import com.ag2m.gestimmo.metier.dto.ReservationDto;
import com.ag2m.gestimmo.metier.enumeration.EnumStatutReservation;
import com.ag2m.gestimmo.metier.enumeration.EnumTypeAppartement;
import com.ag2m.gestimmo.metier.enumeration.EnumTypePieceIdentite;
import com.ag2m.gestimmo.metier.exception.TechnicalException;

/**
 * @author mombaye
 *
 */
public class DevisServiceImplTest extends AbstractCommonTest{

	@Test
	public void createDevis() throws TechnicalException {
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
		
		//Facture
		FactureDto facture = createFacture(client, 2D, adresse2, 20D, 15D, "AG2MGI2018F6");
		
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
		
		assertThat(reservation1.getId(), is(notNullValue()));
		assertThat(reservation2.getId(), is(notNullValue()));
		
		
		//Création de devis 
		DevisDto devisDto = createDevis(client.getNom(), client.getPrenom(), 
				client.getAdresseEmail(), client.getTelephone(), "AG2MGI2018FP1", 
				new LocalDateTime(), new LocalDateTime().plusDays(3),
				facture);
		assertThat(devisDto, is(notNullValue()));
		assertThat(devisDto.getId(), is(notNullValue()));
	}

}
