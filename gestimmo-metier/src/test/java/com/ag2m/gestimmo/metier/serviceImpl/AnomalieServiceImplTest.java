/**
 * 
 */
package com.ag2m.gestimmo.metier.serviceImpl;

import static org.junit.Assert.assertThat;

import org.joda.time.LocalDateTime;
import org.junit.Test;

import com.ag2m.gestimmo.metier.dto.AnomalieDto;
import com.ag2m.gestimmo.metier.dto.AppartementDto;
import com.ag2m.gestimmo.metier.dto.BienDto;
import com.ag2m.gestimmo.metier.enumeration.EnumStatutAnomalie;
import com.ag2m.gestimmo.metier.enumeration.EnumTypeAppartement;

import static org.hamcrest.CoreMatchers.*;

/**
 * @author mombaye
 *
 */
public class AnomalieServiceImplTest extends AbstractcommonTest{

	

	@Test
	public void testSaveOrUpdate() {

		AnomalieDto anomalie = createNewAnomalie();
		assertThat(anomalie, is(notNullValue()));
	}



	
	@Test
	public void testDelete() {
		AnomalieDto anomalie = createNewAnomalie();
		assertThat(anomalie, is(notNullValue()));
		
		anomalieService.delete(anomalie);
		anomalie = anomalieService.findById(anomalie.getId());
		assertThat(anomalie, is(nullValue()));
	}	
	
	
	private AnomalieDto createNewAnomalie() {
		BienDto bien = createBien("Wakeur Meissa", "12 cité Fadia", null, "Sacré coeur", 99000, "Sénégal");
		AppartementDto appartement = createAppartement("Dalal Diam", bien, EnumTypeAppartement.T2.getType());
		String description =  "le lavabo est mal fixé";
		String titre =  "Pb de lavabo";
		AnomalieDto anomalie = createAnomalie(appartement, "Responsabilité client", new LocalDateTime().minusDays(15), 
				new LocalDateTime(), description, EnumStatutAnomalie.EN_TRAITEMENT.getStatut(), titre);
		
		anomalie = anomalieService.findById(anomalie.getId());
		return anomalie;
	}
}
