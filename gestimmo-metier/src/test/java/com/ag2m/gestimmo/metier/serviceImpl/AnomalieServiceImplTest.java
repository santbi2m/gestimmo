/**
 * 
 */
package com.ag2m.gestimmo.metier.serviceImpl;

import static org.junit.Assert.assertThat;

import java.util.List;

import org.joda.time.LocalDateTime;
import org.junit.Test;

import com.ag2m.gestimmo.metier.dto.AdresseDto;
import com.ag2m.gestimmo.metier.dto.AnomalieDto;
import com.ag2m.gestimmo.metier.dto.AppartementDto;
import com.ag2m.gestimmo.metier.dto.BienDto;
import com.ag2m.gestimmo.metier.enumeration.EnumStatutAnomalie;
import com.ag2m.gestimmo.metier.enumeration.EnumTypeAppartement;
import com.ag2m.gestimmo.metier.exception.TechnicalException;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

/**
 * @author mombaye
 *
 */
public class AnomalieServiceImplTest extends AbstractCommonTest{

	
	@Test
	public void testFindAnomalieByIdNull() throws FunctionalException {
		
		//Assert that an exception has thrown
		thrown.expect(FunctionalException.class);
		thrown.expectMessage("L'ID DE ENTITE SPECIFIER EST NULL");
		
	    anomalieService.findAnomalieById(null);
	}



	@Test
	public void testDelete() throws FunctionalException {
		
		//Create and save ano
		AnomalieDto anomalie = createNewAnomalie();
		
		//Assert
		assertThat(anomalie, is(notNullValue()));
		
		//Call services
		anomalieService.deleteAnomalie(anomalie);
		anomalie = anomalieService.findAnomalieById(anomalie.getId());
		
		//Assertions
		assertThat(anomalie, is(nullValue()));
	}	
	
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
		//anomalie = anomalieService.findAnomalieById(anomalie.getId());
		
		return anomalie;
	}
	
	/**
	 * Tester le service findAnomalieByCriteria
	 * sans critères d'entrée. Les données sont nulls.	 
	 *  
	 * @throws FunctionalException
	 */
	@Test
	public void testFindAnomalieByCriteriaAllCriteriaNull() throws FunctionalException {
		
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
		List<AnomalieDto> result = anomalieService.findAnomalieByCriteria(null, null, null, null, null);
		
		//Check
		assertThat(anomalie, is(notNullValue()));
		assertThat(result, is(notNullValue()));
		assertThat(result, is(not(empty())));
		assertThat(result.size(), is(greaterThanOrEqualTo(1)));		
	}

	
	/**
	 * Tester le service findAppartementByCriteria
	 * avec le titre comme critère d'entrée
	 * 
	 * @throws FunctionalException
	 */

	@Test
	public void testFindAnomalieByCriteriaTitre() throws FunctionalException {
		
		//Adresse
		AdresseDto adresse = createAdresse("12 cité Fadia", null, 9900, "Sacré coeur", "Sénégal");
		
		//Bien
		BienDto bien = createBien("Wakeur Meissa", adresse);
		
		//Appartements
		AppartementDto appartement = createAppartement("Dalal Diam", bien, EnumTypeAppartement.T2.getType(), 50D);
		
		//Anomalie
		String description =  "le lavabo est mal fixé";
		String titre =  "Pb de lavabo";
		
		/************   		   				************
		 * 			Casse respecter         			   *
		 * 												   *
		 ************ 							************/
		//Call service
		AnomalieDto anomalie = createAnomalie(appartement, "Responsabilité client", new LocalDateTime().minusDays(15), 
				new LocalDateTime(), description, EnumStatutAnomalie.EN_TRAITEMENT.getStatut(), titre);
		
		//Call service
		List<AnomalieDto> result = anomalieService.findAnomalieByCriteria(titre, null, null, null, null);
		
		//Check
		assertThat(anomalie, is(notNullValue()));
		assertThat(result, is(notNullValue()));
		assertThat(result, is(not(empty())));
		assertThat(result.size(), is(greaterThanOrEqualTo(1)));

		result.forEach(app -> {
			assertThat(app.getTitre(), is("Pb de lavabo"));
		});
		
		/************   		   				************
		 * 			Paramêtre titre en minuscule		   *
		 * 												   *
		 ************ 							************/
		//Call service
		result = anomalieService.findAnomalieByCriteria("pb de lavabo", null, null, null, null);
		
		//Check
		assertThat(result, is(notNullValue()));
		assertThat(result, is(not(empty())));
		assertThat(result.size(), is(greaterThanOrEqualTo(1)));

		result.forEach(app -> {
			assertThat(app.getTitre(), is("Pb de lavabo"));
		});
		
		/************   		   				************
		 * 			Paramêtre en majuscule et miniscule		   	   *
		 * 												   *
		 ************ 							************/
		
		result = anomalieService.findAnomalieByCriteria("pB dE lavabO", null, null, null, null);
			
			//Check
		    assertThat(anomalie, is(notNullValue()));
			assertThat(result, is(notNullValue()));
			assertThat(result, is(not(empty())));
			assertThat(result.size(), is(greaterThanOrEqualTo(1)));

			result.forEach(app -> {
				assertThat(app.getTitre(), is("Pb de lavabo"));
			});
	}

	
	/**
	 * Tester le service findAppartementByCriteria
	 * avec le statut comme critère d'entrée
	 * 
	 * @throws FunctionalException
	 */

	@Test
	public void testFindAnomalieByCriteriaStatut() throws FunctionalException {
		//Adresse
		AdresseDto adresse = createAdresse("12 cité Fadia", null, 9900, "Sacré coeur", "Sénégal");
		
		//Bien
		BienDto bien = createBien("Wakeur Meissa", adresse);
		
		//Appartements
		AppartementDto appartement = createAppartement("Dalal Diam", bien, EnumTypeAppartement.T2.getType(), 50D);
		
		//Anomalie
		String description =  "le lavabo est mal fixé";
		String titre =  "Pb de lavabo";
		
		/************   		   				************
		 * 		statut existant         			       *
		 * 												   *
		 ************ 							************/
		//Call service
		AnomalieDto anomalie = createAnomalie(appartement, "Responsabilité client", new LocalDateTime().minusDays(15), 
				new LocalDateTime(), description, EnumStatutAnomalie.EN_TRAITEMENT.getStatut(), titre);
		
		//Call service
		List<AnomalieDto> result = anomalieService.findAnomalieByCriteria(null, EnumStatutAnomalie.EN_TRAITEMENT.getStatut(), null, null, null);
		
		//Check
		assertThat(result, is(notNullValue()));
		assertThat(result, is(not(empty())));
		assertThat(result.size(), is(greaterThanOrEqualTo(1)));

		result.forEach(app -> {
			assertThat(app.getStatut(), is("En traitement"));
		});
		
	}
	
	/**
	 * Tester le service findAppartementByCriteria
	 * avec le statut comme critère d'entrée
	 * 
	 * @throws FunctionalException
	 */

	@Test
	public void testFindAnomalieByCriteriaStatutInexistant() throws FunctionalException {
		
		thrown.expect(FunctionalException.class);
		thrown.expectMessage("LE STATUT N'EXISTE PAS");
		//Call service
		 anomalieService.findAnomalieByCriteria(null, "Pré-traitement", null, null, null);
	}
	
	/**
	 * Tester le service findAppartementByCriteria
	 * avec la date d'ouverture comme critère d'entrée
	 * 
	 * @throws FunctionalException
	 */

	@Test
	public void testFindAnomalieByCriteriaDateOuverture() throws FunctionalException {
		
		//Adresse
		AdresseDto adresse = createAdresse("12 cité Fadia", null, 9900, "Sacré coeur", "Sénégal");
		
		//Bien
		BienDto bien = createBien("Wakeur Meissa", adresse);
		
		//Appartements
		AppartementDto appartement = createAppartement("Dalal Diam", bien, EnumTypeAppartement.T2.getType(), 50D);
		
		//Anomalie
		String description =  "le lavabo est mal fixé";
		String titre1 =  "Pb de lavabo";
		String titre2 =  "Pb de lumiére";
		
		/************   		   				************
		 * 		statut existant         			   *
		 * 												   *
		 ************ 							************/
		//Call service
		AnomalieDto anomalie1 = createAnomalie(appartement, "Responsabilité client", new LocalDateTime().minusDays(15), 
				new LocalDateTime(), description, EnumStatutAnomalie.EN_TRAITEMENT.getStatut(), titre1);
		
		AnomalieDto anomalie2 = createAnomalie(appartement, "Responsabilité client", new LocalDateTime().minusDays(5), 
				new LocalDateTime(), description, EnumStatutAnomalie.EN_TRAITEMENT.getStatut(), titre2);
		LocalDateTime dateOuverture = new LocalDateTime().minusDays(15);
		//Call service
		List<AnomalieDto> result = anomalieService.findAnomalieByCriteria(null,null,dateOuverture, null, null);
		
		//Check
		assertThat(result, is(notNullValue()));
		assertThat(result, is(not(empty())));
		//assertThat(result.size(), is(greaterThanOrEqualTo(2)));

		result.forEach(app -> {
			assertThat(app.getStatut(), is(EnumStatutAnomalie.EN_TRAITEMENT.getStatut()));
		});
		
	}
	
}
