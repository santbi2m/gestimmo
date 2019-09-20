/**
 * 
 */
package com.ag2m.gestimmo.metier.serviceImpl;

import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

import com.ag2m.gestimmo.metier.constants.TechnicalErrorMessageConstants;
import com.ag2m.gestimmo.metier.dto.AdresseDto;
import com.ag2m.gestimmo.metier.dto.AppartementDto;
import com.ag2m.gestimmo.metier.dto.BienDto;
import com.ag2m.gestimmo.metier.enumeration.EnumTypeAppartement;
import com.ag2m.gestimmo.metier.exception.TechnicalException;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.empty;

/**
 * @author mombaye
 *
 */
public class AppartementServiceImplTest extends AbstractCommonTest{

	@Test
	public void testLoadAllAppartement() throws TechnicalException {
		
		//Adresse
		AdresseDto adresse = createAdresse("12 cité Fadia", null, 9900, "Sacré coeur", "Sénégal");
		
		//Bien
		BienDto bien = createBien("Wakeur Meissa", adresse);
		bien = bienService.createBien(bien);
		
		//Appartements
		createAppartement("Dalal Diam", bien, EnumTypeAppartement.T2.getType(), 50D);
		createAppartement("Dem Deloussi", bien, EnumTypeAppartement.T3.getType(), 70D);
		createAppartement("Tawfekh", bien, EnumTypeAppartement.STUDIO.getType(), 45D);
		
		//Appel
		List<AppartementDto> appartements = appartementService.loadAllAppartement();
		
		//Check results
		assertThat(appartements, is(notNullValue()));
		assertThat(appartements.size(), is(greaterThanOrEqualTo(3)));
		
		//check cache
		assertThat(cacheManager.getObject().getCache("gestimmo").getSize(), greaterThanOrEqualTo(2));
	}

	@Test
	public void testCreateAppartement() throws TechnicalException {

		int oldSize = cacheManager.getObject().getCache("gestimmo").getSize();
		
		//Adresse
		AdresseDto adresse = createAdresse("12 cité Adja Mareme", "2ème porte", 9900, "Grand Mbao", "Sénégal");
		
		//Bien
		BienDto bien = createBien("Keur Naby",adresse);
		bien = bienService.createBien(bien);
		
		//Appartements
		AppartementDto appartement = createAppartement("Dalal Diam", bien, EnumTypeAppartement.T2.getType(), 50D);
		
		//Check results
		assertThat(appartement.getId(), is(notNullValue()));
		
		//Check cache
		int newSize = cacheManager.getObject().getCache("gestimmo").getSize();
		assertThat(newSize, greaterThan(oldSize));
	}
	
	
	@Test
	public void testUpdateAppartement() throws TechnicalException {

		AppartementDto app = appartementService.findAppartementById(9999L);
		//Check results
		assertThat(app, is(notNullValue()));
		assertThat(app.getId(), is(9999L));
		
		//Call service for updating appartement
		app.setLibelle("updated libelle");
		app.setType(EnumTypeAppartement.CHAMBRE.getType());
		app = appartementService.updateAppartement(app);
		
		//Check results
		assertThat(app.getId(), is(9999L));
		assertThat(app.getLibelle(), is("updated libelle"));
		assertThat(app.getType(), is(EnumTypeAppartement.CHAMBRE.getType()));
	}
	
	
	@Test
	public void testUpdateAppartementIdNull() throws TechnicalException {

		AppartementDto app = appartementService.findAppartementById(9999L);
		//Check results
		assertThat(app, is(notNullValue()));
		assertThat(app.getId(), is(9999L));
		
		//Assert that an exception has thrown
		thrown.expect(TechnicalException.class);
		thrown.expectMessage(TechnicalErrorMessageConstants.ERREUR_ENTREE_MODIFICATION_NULL);
		
		//Call service for updating appartement
		app.setId(null);
		app = appartementService.updateAppartement(app);
		
	}

	
	@Test
	public void testDeleteApparetement() throws TechnicalException {
		
		// Adresse
		AdresseDto adresse = createAdresse("124 cité promocap", "2ème porte", 9900, "Petit Mbao", "Sénégal");

		// Bien		
		BienDto bien = createBien("Keur Dabakh", adresse);
		bien = bienService.createBien(bien);
		
		// Check results
		assertThat(bien.getId(), is(notNullValue()));
		
		//Appartement
		AppartementDto appartement = createAppartement("Dalal Diam", bien, EnumTypeAppartement.T2.getType(), 50D);
		
		// Check results
		assertThat(appartement.getId(), is(notNullValue()));
		
		//load and check previous appart
		AppartementDto entite = appartementService.findAppartementById(appartement.getId());
		assertThat(entite, is(notNullValue()));
		
		//Call services
		appartementService.deleteAppartement(entite);
		entite = appartementService.findAppartementById(entite.getId());
		//Check
		assertThat(entite, is(nullValue()));
	}	
	
	
	/**
	 * Tester le service findAppartementByCriteria
	 * sans critères d'entrée
	 * 
	 * @throws TechnicalException
	 */

	@Test
	public void testFindAppartementByCriteriaAllCriteriaNull() throws TechnicalException {
		
		//Adresse
		AdresseDto adresse = createAdresse("12 cité Fadia", null, 9900, "Sacré coeur", "Sénégal");
		
		//Bien
		BienDto bien = createBien("Wakeur Meissa", adresse);
		bien = bienService.createBien(bien);
		
		//Appartements
		createAppartement("Dalal Diam", bien, EnumTypeAppartement.T2.getType(), 50D);
		createAppartement("Dem Deloussi", bien, EnumTypeAppartement.T3.getType(), 70D);
		createAppartement("Tawfekh", bien, EnumTypeAppartement.STUDIO.getType(), 45D);
		
		//Call service
		List<AppartementDto> result = appartementService.findAppartementByCriteria(null, null, null);
		
		//Check
		assertThat(result, is(notNullValue()));
		assertThat(result, is(not(empty())));
		assertThat(result.size(), is(greaterThanOrEqualTo(3)));
	}
	
	
	/**
	 * Tester le service findAppartementByCriteria
	 * avec le libellé comme critère d'entrée
	 * 
	 * @throws TechnicalException
	 */

	@Test
	public void testFindAppartementByCriteriaLibelle() throws TechnicalException {
		
		//Adresse
		AdresseDto adresse = createAdresse("12 cité Fadia", null, 9900, "Sacré coeur", "Sénégal");
		
		//Bien
		BienDto bien = createBien("Wakeur Meissa", adresse);
		bien = bienService.createBien(bien);
		
		//Appartements
		createAppartement("Dalal Diam", bien, EnumTypeAppartement.T2.getType(), 50D);
		createAppartement("Dem Deloussi", bien, EnumTypeAppartement.T3.getType(), 70D);
		createAppartement("Tawfekh", bien, EnumTypeAppartement.STUDIO.getType(), 45D);
		
		/************   		   				************
		 * 			Case parametter repected			   *
		 * 												   *
		 ************ 							************/
		//Call service
		List<AppartementDto> result = appartementService.findAppartementByCriteria("Dalal Diam", null, null);
		
		//Check
		assertThat(result, is(notNullValue()));
		assertThat(result, is(not(empty())));
		assertThat(result.size(), is(greaterThanOrEqualTo(1)));

		result.forEach(app -> {
			assertThat(app.getLibelle(), is("Dalal Diam"));
		});
		
		/************   		   				************
		 * 			Parametter in Lower case			   *
		 * 												   *
		 ************ 							************/
		//Call service
		 result = appartementService.findAppartementByCriteria("dalal diam", null, null);
		
		//Check
		assertThat(result, is(notNullValue()));
		assertThat(result, is(not(empty())));
		assertThat(result.size(), is(greaterThanOrEqualTo(1)));

		result.forEach(app -> {
			assertThat(app.getLibelle(), is("Dalal Diam"));
		});
		
		
		/************   		   				************
		 * 			Parametter in any case			   	   *
		 * 												   *
		 ************ 							************/
		
		 result = appartementService.findAppartementByCriteria("dAlAl DiaM", null, null);
			
			//Check
			assertThat(result, is(notNullValue()));
			assertThat(result, is(not(empty())));
			assertThat(result.size(), is(greaterThanOrEqualTo(1)));

			result.forEach(app -> {
				assertThat(app.getLibelle(), is("Dalal Diam"));
			});
	}
	
	
	/**
	 * Tester le service findAppartementByCriteria
	 * avec le libellé comme critère d'entrée
	 * 
	 * @throws TechnicalException
	 */

	@Test
	public void testFindAppartementByCriteriaType() throws TechnicalException {
		
		//Adresse
		AdresseDto adresse = createAdresse("12 cité Fadia", null, 9900, "Sacré coeur", "Sénégal");
		
		//Bien
		BienDto bien = createBien("Wakeur Meissa", adresse);
		bien = bienService.createBien(bien);
		
		//Appartements
		createAppartement("Dalal Diam", bien, EnumTypeAppartement.T3.getType(), 50D);
		createAppartement("Dem Deloussi", bien, EnumTypeAppartement.T3.getType(), 70D);
		createAppartement("Tawfekh", bien, EnumTypeAppartement.STUDIO.getType(), 45D);
		
		//Call service with parametter Type = T2
		List<AppartementDto> result = appartementService.findAppartementByCriteria(null, EnumTypeAppartement.T3.getType(), null);
		
		//Check
		assertThat(result, is(notNullValue()));
		assertThat(result, is(not(empty())));
		assertThat(result.size(), is(greaterThanOrEqualTo(2)));

		result.forEach(app -> {
			assertThat(app.getType(), is(EnumTypeAppartement.T3.getType()));
		});
	}
	
	/**
	 * Tester le service findAppartementByCriteria
	 * avec l'id du Bien comme critère d'entrée
	 * 
	 * @throws TechnicalException
	 */

	@Test
	public void testFindAppartementByCriteriaBien() throws TechnicalException {
		
		//Adresse
		AdresseDto adresse = createAdresse("12 cité Fadia", null, 9900, "Sacré coeur", "Sénégal");
		
		//Bien
		BienDto bien = createBien("Wakeur Meissa", adresse);
		bien = bienService.createBien(bien);
		final Long idBien = bien.getId();
		assertThat(bien.getId(), is(notNullValue()));
		
		//Appartements
		createAppartement("Dalal Diam", bien, EnumTypeAppartement.T3.getType(), 50D);
		createAppartement("Dem Deloussi", bien, EnumTypeAppartement.T3.getType(), 70D);
		createAppartement("Tawfekh", bien, EnumTypeAppartement.STUDIO.getType(), 45D);
		
		//Call service with parametter idBien
		List<AppartementDto> result = appartementService.findAppartementByCriteria(null, null, bien.getId());
		
		//Check
		assertThat(result, is(notNullValue()));
		assertThat(result, is(not(empty())));
		assertThat(result.size(), is(greaterThanOrEqualTo(3)));

		result.forEach(app -> {
			assertThat(app.getBien(), is(notNullValue()));
			assertThat(app.getBien().getId(), is(idBien));
		});
	}
	
	/**
	 * Tester le service findAppartementByCriteria
	 * avec tous les paramètres d'entrée.
	 * 
	 * @throws TechnicalException
	 */

	@Test
	public void testFindAppartementByCriteria() throws TechnicalException {
		
		//Adresse
		AdresseDto adresse = createAdresse("12 cité Fadia", null, 9900, "Sacré coeur", "Sénégal");
		
		//Bien
		BienDto bien = createBien("Wakeur Meissa", adresse);
		bien = bienService.createBien(bien);
		assertThat(bien.getId(), is(notNullValue()));
		
		//Appartements
		createAppartement("Dalal Diam", bien, EnumTypeAppartement.T3.getType(), 50D);
		createAppartement("Dem Deloussi", bien, EnumTypeAppartement.T1.getType(), 70D);
		createAppartement("Tawfekh", bien, EnumTypeAppartement.STUDIO.getType(), 45D);
		createAppartement("Résidence 3W", bien, EnumTypeAppartement.MAISON.getType(), 200D);
		
		//Call service with parametter idBien
		List<AppartementDto> result = appartementService.findAppartementByCriteria("Résidence 3W", EnumTypeAppartement.MAISON.getType(), bien.getId());
		
		//Check
		assertThat(result, is(notNullValue()));
		assertThat(result, is(not(empty())));
		assertThat(result.size(), is(1));
		
		AppartementDto app = result.get(0);
		assertThat(app.getBien(), is(notNullValue()));
		assertThat(app.getBien().getId(), is(bien.getId()));
		assertThat(app.getLibelle(), is("Résidence 3W"));
		assertThat(app.getType(), is(EnumTypeAppartement.MAISON.getType()));
	}
	
	/**
	 * Tester le service findAppartementByCriteria
	 * avec le libellé comme critère d'entrée
	 * 
	 * @throws TechnicalException
	 */

	@Test
	public void testFindAppartementByCriteriaBadLibelle() throws TechnicalException {
		
		//Adresse
		AdresseDto adresse = createAdresse("12 cité Fadia", null, 9900, "Sacré coeur", "Sénégal");
		
		//Bien
		BienDto bien = createBien("Wakeur Meissa", adresse);
		bien = bienService.createBien(bien);
		
		//Appartements
		createAppartement("Dalal Diam", bien, EnumTypeAppartement.T3.getType(), 50D);
		createAppartement("Dem Deloussi", bien, EnumTypeAppartement.T3.getType(), 70D);
		createAppartement("Tawfekh", bien, EnumTypeAppartement.STUDIO.getType(), 45D);
		
		//Call service with bad parametter
		List<AppartementDto> result = appartementService.findAppartementByCriteria("lebelle bidon", EnumTypeAppartement.T3.getType(), null);
		
		//Check
		assertThat(result, is(notNullValue()));
		assertThat(result, is(empty()));
	}
	
	/**
	 * Tester le service findAppartementByCriteria
	 * Tester que le service utilise bien le cache
	 * 
	 * @throws TechnicalException
	 */

	@Test
	public void testFindAppartementByCriteriaCheckCache() throws TechnicalException {
		
		
		//Adresse
		AdresseDto adresse = createAdresse("12 cité Fadia", null, 9900, "Sacré coeur", "Sénégal");
		
		//Bien
		BienDto bien = createBien("Wakeur Meissa", adresse);
		bien = bienService.createBien(bien);
		
		//Appartements
		createAppartement("Dalal Diam", bien, EnumTypeAppartement.T3.getType(), 50D);
		createAppartement("Dem Deloussi", bien, EnumTypeAppartement.T3.getType(), 70D);
		createAppartement("Tawfekh", bien, EnumTypeAppartement.STUDIO.getType(), 45D);
		
		int oldSize = cacheManager.getObject().getCache("gestimmo").getSize();
		
		//Call service
		List<AppartementDto> result = appartementService.findAppartementByCriteria(null, EnumTypeAppartement.T3.getType(), null);
		
		//Check
		assertThat(result, is(notNullValue()));
		assertThat(result, is(not(empty())));
		assertThat(result.size(), is(greaterThanOrEqualTo(2)));
		
		//Vérifier que le cache a été mis à jour
		int newSize = cacheManager.getObject().getCache("gestimmo").getSize();
		assertThat(newSize, greaterThan(oldSize));
	}

}
