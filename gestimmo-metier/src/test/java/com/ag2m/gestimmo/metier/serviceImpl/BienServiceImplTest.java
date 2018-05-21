/**
 * 
 */
package com.ag2m.gestimmo.metier.serviceImpl;

import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

import com.ag2m.gestimmo.metier.dto.AdresseDto;
import com.ag2m.gestimmo.metier.dto.AppartementDto;
import com.ag2m.gestimmo.metier.dto.BienDto;
import com.ag2m.gestimmo.metier.enumeration.EnumTypeAppartement;
import com.ag2m.gestimmo.metier.exception.FunctionalException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.*;

/**
 * @author mombaye
 *
 */
public class BienServiceImplTest extends AbstractCommonTest{

	

	@Test
	public void testFindAll() throws FunctionalException {

		//Adresse
		AdresseDto adresse = createAdresse("120 cité Azur", null, 9900, "Mermoz", "Sénégal");
		AdresseDto adresse2 = createAdresse("150 avenue Malick Sy", null, 9900, "Point E", "Sénégal");
		
		//Bien
		createBien("Dianna Firdawsi", adresse);
		createBien("Dianna Mahwa", adresse2);
		
		//Call services
		List<BienDto> biens = bienService.loadAllBien();
		
		//Check results
		assertThat(biens, is(notNullValue()));
		assertThat(biens.size(), greaterThanOrEqualTo(2));
		
		//Check cache
		assertThat(cacheManager.getObject().getCache("gestimmo").getSize(), greaterThanOrEqualTo(2));
		
	}

	@Test
	public void testCreateBien() throws FunctionalException {
		
		int oldSize = cacheManager.getObject().getCache("gestimmo").getSize();
		
		// Adresse
		AdresseDto adresse = createAdresse("124 cité promocap", "2ème porte", 9900, "Petit Mbao", "Sénégal");
		
		//Bien
		BienDto bien = createBien("Keur Dabakh", adresse);
		
		//Call service
		bien = bienService.findBienById(bien.getId());
		
		//Check result
		assertThat(bien.getId(), is(notNullValue()));
		
		//Check cache
		int newSize = cacheManager.getObject().getCache("gestimmo").getSize();
		assertThat(newSize, greaterThan(oldSize));
	}
	
	@Test
	public void testUpdateBien() throws FunctionalException {

		BienDto bien = bienService.findBienById(1L);
		//Check results
		assertThat(bien, is(notNullValue()));
		assertThat(bien.getId(), is(1L));
		
		//Call service for updating bien
		bien.setLibelle("updated libelle");
		bien = bienService.updateBien(bien);
		
		//Check results
		assertThat(bien.getId(), is(1L));
		assertThat(bien.getLibelle(), is("updated libelle"));
	}
	

	
	@Test
	public void testDeleteBien() throws FunctionalException {
		
		// Adresse
		AdresseDto adresse = createAdresse("124 cité promocap", "2ème porte", 9900, "Petit Mbao", "Sénégal");
		
		//Bien
		BienDto bien = createBien("Keur Dabakh", adresse);
		
		//Check result
		assertThat(bien.getId(), is(notNullValue()));
		
		//Call service
		BienDto entite = bienService.findBienById(bien.getId());
		//Check result
		assertThat(entite, is(notNullValue()));
		
		//Call services
		bienService.deleteBien(entite);
		entite = bienService.findBienById(bien.getId());
		
		//Check result
		assertThat(entite, is(nullValue()));
		
	}	
	
	
	@Test
	public void testDeleteBienWithAppart() throws FunctionalException {
		
		// Adresse
		AdresseDto adresse = createAdresse("12 cité Fadia", null, 9900, "Sacré coeur", "Sénégal");
				
		//Bien
		BienDto bien = createBien("Wakeur Meissa", adresse);
		
		//Appartements
		AppartementDto app1= createAppartement("Dalal Diam", bien, EnumTypeAppartement.T2.getType(), 50D);
		AppartementDto app2= createAppartement("Dem Deloussi", bien, EnumTypeAppartement.T3.getType(), 70D);
		AppartementDto app3= createAppartement("Tawfekh", bien, EnumTypeAppartement.STUDIO.getType(), 45D);
		
		//Call Services
		bienService.deleteBien(bien);
		bien = bienService.findBienById(bien.getId());
		app1 = appartementService.findAppartementById(app1.getId());
		app2 = appartementService.findAppartementById(app2.getId());
		app3 = appartementService.findAppartementById(app3.getId());
		
		//Check results
		assertThat(bien, is(nullValue()));
		assertThat(app1, is(nullValue()));
		assertThat(app2, is(nullValue()));
		assertThat(app3, is(nullValue()));
	}
	
	/**
	 * Tester le service findAppartementByCriteria
	 * sans critères d'entrée
	 * 
	 * @throws FunctionalException
	 */

	@Test
	public void testFindBienByCriteriaAllCriteriaNull() throws FunctionalException {
		
		//Adresse
		AdresseDto adresse = createAdresse("12 cité Fadia", null, 9900, "Sacré coeur", "Sénégal");
		
		//Bien
		createBien("Wakeur Meissa", adresse);
		createBien("bien1", adresse);
		createBien("bien2", adresse);
		createBien("bien3", adresse);
		
		
		
		//Call service
		List<BienDto> result = bienService.findBienByCriteria(null, null, null, 0, null, null);
		
		//Check
		assertThat(result, is(notNullValue()));
		assertThat(result, is(not(empty())));
		assertThat(result.size(), is(greaterThanOrEqualTo(4)));
	}
	
	/**
	 * Tester le service findAppartementByCriteria
	 * avec le libellé comme critère d'entrée
	 * 
	 * @throws FunctionalException
	 */

	@Test
	public void testFindBienByCriteriaLibelle() throws FunctionalException {

		// Adresse
		AdresseDto adresse = createAdresse("12 cité Fadia", null, 9900, "Sacré coeur", "Sénégal");

		// Bien
		createBien("Wakeur Meissa", adresse);
		createBien("bien1", adresse);
		createBien("bien2", adresse);

		/************
		 * ************ Case parametter repected * *
		 ************/
		// Call service
		List<BienDto> result = bienService.findBienByCriteria("Wakeur Meissa", null, null, 0, null, null);

		// Check
		assertThat(result, is(notNullValue()));
		assertThat(result, is(not(empty())));
		assertThat(result.size(), is(greaterThanOrEqualTo(1)));

		result.forEach(app -> {
			assertThat(app.getLibelle(), is("Wakeur Meissa"));
		});

		/************
		 * ************ Parametter in Lower case * *
		 ************/
		// Call service
		result = bienService.findBienByCriteria("bien1", null, null, 0, null, null);

		// Check
		assertThat(result, is(notNullValue()));
		assertThat(result, is(not(empty())));
		assertThat(result.size(), is(greaterThanOrEqualTo(1)));

		result.forEach(app -> {
			assertThat(app.getLibelle(), is("bien1"));
		});

		/************
		 * ************ Parametter in any case * *
		 ************/

		result = bienService.findBienByCriteria("bien2", null, null, 0, null, null);

		// Check
		assertThat(result, is(notNullValue()));
		assertThat(result, is(not(empty())));
		assertThat(result.size(), is(greaterThanOrEqualTo(1)));

		result.forEach(app -> {
			assertThat(app.getLibelle(), is("bien2"));
		});
	}
	
	/**
	 * Tester le service findAppartementByCriteria
	 * avec les champs dde l' adresse comme critère d'entrée
	 * 
	 * @throws FunctionalException
	 */

	@Test
	public void testFindBienByCriteriaAdresse() throws FunctionalException {

		// Adresse
		AdresseDto adresse = createAdresse("12 cité Fadia", null, 9900, "Sacré coeur", "Sénégal");
		assertThat(adresse.getId(), is(notNullValue()));
		// Bien
		createBien("Wakeur Meissa", adresse);


		/************
		 * ************ Case parametter repected * *
		 ************/
		// Call service
		List<BienDto> result = bienService.findBienByCriteria(null, adresse.getAdresse(), null, 0, null, null);

		// Check
		assertThat(result, is(notNullValue()));
		assertThat(result, is(not(empty())));
		assertThat(result.size(), is(greaterThanOrEqualTo(1)));

		result.forEach(app -> {
			assertThat(app.getAdresse().getId(), is(notNullValue()));
			assertThat(app.getAdresse().getAdresse(), is("12 cité Fadia"));
		});

		/************
		 * ************ Parametter in Lower case * *
		 ************/
		// Call service
		result = bienService.findBienByCriteria(null, null, null, adresse.getCodePostal(), null, null);

		// Check
		assertThat(result, is(notNullValue()));
		assertThat(result, is(not(empty())));
		assertThat(result.size(), is(greaterThanOrEqualTo(1)));

		result.forEach(app -> {
			assertThat(app.getId(), is(notNullValue()));
			assertThat(app.getAdresse().getCodePostal(), is(9900));
		});

		/************
		 * ************ Parametter in any case * *
		 ************/

		result = bienService.findBienByCriteria(null, null, null, 0, adresse.getVille(), null);

		// Check
		assertThat(result, is(notNullValue()));
		assertThat(result, is(not(empty())));
		assertThat(result.size(), is(greaterThanOrEqualTo(1)));

		result.forEach(app -> {
			assertThat(app.getAdresse().getId(), is(notNullValue()));
			assertThat(app.getAdresse().getVille(), is("Sacré coeur"));
		});
		
		/************
		 * ************ Parametter in any case * *
		 ************/

		result = bienService.findBienByCriteria(null, null, null, 0, null, adresse.getPays());

		// Check
		assertThat(result, is(notNullValue()));
		assertThat(result, is(not(empty())));
		assertThat(result.size(), is(greaterThanOrEqualTo(1)));

		result.forEach(app -> {
			assertThat(app.getAdresse().getId(), is(notNullValue()));
			assertThat(app.getAdresse().getPays(), is("Sénégal"));
		});
	}

}
