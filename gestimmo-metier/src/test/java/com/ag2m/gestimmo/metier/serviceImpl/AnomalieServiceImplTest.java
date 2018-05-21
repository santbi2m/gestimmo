/**
 * 
 */
package com.ag2m.gestimmo.metier.serviceImpl;

import static org.junit.Assert.assertThat;

import org.joda.time.LocalDateTime;
import org.junit.Test;

import com.ag2m.gestimmo.metier.dto.AdresseDto;
import com.ag2m.gestimmo.metier.dto.AnomalieDto;
import com.ag2m.gestimmo.metier.dto.AppartementDto;
import com.ag2m.gestimmo.metier.dto.BienDto;
import com.ag2m.gestimmo.metier.enumeration.EnumStatutAnomalie;
import com.ag2m.gestimmo.metier.enumeration.EnumTypeAppartement;
import com.ag2m.gestimmo.metier.exception.FunctionalException;

import static org.hamcrest.CoreMatchers.*;

/**
 * @author mombaye
 *
 */
public class AnomalieServiceImplTest extends AbstractCommonTest{

	

	@Test
	public void testSaveOrUpdate() throws FunctionalException {
		
		//Create and save
		AnomalieDto anomalie = createNewAnomalie();
		
		//Assertions
		assertThat(anomalie, is(notNullValue()));
	}



	
	@Test
	public void testDelete() throws FunctionalException {
		
		//Create and save ano
		AnomalieDto anomalie = createNewAnomalie();
		
		//Assert
		assertThat(anomalie, is(notNullValue()));
		
		//Call services
		anomalieService.delete(anomalie);
		anomalie = anomalieService.findById(anomalie.getId());
		
		//Assertions
		assertThat(anomalie, is(nullValue()));
	}	
	
	
	private AnomalieDto createNewAnomalie() throws FunctionalException {
		//Adresse
		AdresseDto adresse = createAdresse("12 cité Fadia", null, 9900, "Sacré coeur", "Sénégal");
		
		//Bien
		BienDto bien = createBien("Wakeur Meissa", adresse);
		
		//Appartement
		AppartementDto appartement = createAppartement("Dalal Diam", bien, EnumTypeAppartement.T2.getType(), 50D);
		
		//Anomalie
		String description =  "le lavabo est mal fixé";
		String titre =  "Pb de lavabo";
		AnomalieDto anomalie = createAnomalie(appartement, "Responsabilité client", new LocalDateTime().minusDays(15), 
				new LocalDateTime(), description, EnumStatutAnomalie.EN_TRAITEMENT.getStatut(), titre);
		//Call service
		anomalie = anomalieService.findById(anomalie.getId());
		
		//return
		return anomalie;
	}
}
