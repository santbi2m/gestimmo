/**
 * 
 */
package com.ag2m.gestimmo.metier.serviceImpl;

import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.joda.time.LocalDateTime;
import org.junit.Test;

import com.ag2m.gestimmo.metier.dto.AppartementDto;
import com.ag2m.gestimmo.metier.dto.BienDto;
import com.ag2m.gestimmo.metier.dto.ReservationDto;
import com.ag2m.gestimmo.metier.enumeration.EnumStatutReservation;
import com.ag2m.gestimmo.metier.enumeration.EnumTypeAppartement;

import static org.hamcrest.core.IsNull.*;
import static org.hamcrest.core.Is.*;

/**
 * @author mombaye
 *
 */
public class ReservationServiceImplTest extends AbstractcommonTest{

	@Test
	public void testSaveOrUpdate() {
		
		BienDto bien = createBien("Wakeur Meissa", "12 cité Fadia", null, "Sacré coeur", 99000, "Sénégal");
		AppartementDto app1 = createAppartement("Dalal Diam", bien, EnumTypeAppartement.T2.getType());
		AppartementDto app2 = createAppartement("Dem Deloussi", bien, EnumTypeAppartement.T3.getType());
		AppartementDto app3 = createAppartement("Tawfekh", bien, EnumTypeAppartement.STUDIO.getType());
		
		ReservationDto reservation1 = createReservation(new LocalDateTime(), new LocalDateTime().plusDays(10), "Avec lit bébé svp", true, EnumStatutReservation.ENREGISTREE.getStatut(), Arrays.asList(app1/*, app2, app3*/));
		ReservationDto reservation2 = createReservation(new LocalDateTime(), new LocalDateTime().plusDays(3), "RAS", true, EnumStatutReservation.ANNULEE.getStatut(), Arrays.asList(app2, app3));
		
		
		app1.setReservations(Arrays.asList(reservation1));
		app2.setReservations(Arrays.asList(reservation1, reservation2));
		app3.setReservations(Arrays.asList(reservation1, reservation2));
		
		appartementService.saveOrUpdate(app1);
		appartementService.saveOrUpdate(app2);
		appartementService.saveOrUpdate(app3);
		
		assertThat(reservation1.getId(), is(notNullValue()));
		assertThat(reservation2.getId(), is(notNullValue()));
		
	}
	
	
	@Test
	public void testDelete() {
		
		BienDto bien = createBien("Wakeur Meissa", "12 cité Fadia", null, "Sacré coeur", 99000, "Sénégal");
		AppartementDto app1 = createAppartement("Dalal Diam", bien, EnumTypeAppartement.T2.getType());
		AppartementDto app2 = createAppartement("Dem Deloussi", bien, EnumTypeAppartement.T3.getType());
		AppartementDto app3 = createAppartement("Tawfekh", bien, EnumTypeAppartement.STUDIO.getType());
		
		
		ReservationDto reservation1 = createReservation(new LocalDateTime(), new LocalDateTime().plusDays(10), "Avec lit bébé svp", true, EnumStatutReservation.ENREGISTREE.getStatut(), Arrays.asList(app1, app2, app3));
		ReservationDto reservation2 = createReservation(new LocalDateTime(), new LocalDateTime().plusDays(3), "RAS", true, EnumStatutReservation.ANNULEE.getStatut(), Arrays.asList(app2, app3));
		
		
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
