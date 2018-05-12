/**
 * 
 */
package com.ag2m.gestimmo.metier.serviceImpl;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.ag2m.gestimmo.metier.dto.AdresseDto;
import com.ag2m.gestimmo.metier.dto.AppartementDto;
import com.ag2m.gestimmo.metier.dto.BienDto;
import com.ag2m.gestimmo.metier.enumeration.EnumTypeAppartement;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

/**
 * @author mombaye
 *
 */
public class AppartementServiceImplTest extends AbstractCommonTest{

	

	@Test
	public void testFindAll() {
		
		//Adresse
		AdresseDto adresse = createAdresse("12 cité Fadia", null, 9900, "Sacré coeur", "Sénégal");
		
		//Bien
		BienDto bien = createBien("Wakeur Meissa", adresse);
		
		//Appartements
		createAppartement("Dalal Diam", bien, EnumTypeAppartement.T2.getType(), 50D);
		createAppartement("Dem Deloussi", bien, EnumTypeAppartement.T3.getType(), 70D);
		createAppartement("Tawfekh", bien, EnumTypeAppartement.STUDIO.getType(), 45D);
		
		//Appel
		List<AppartementDto> appartements = appartementService.findAll();
		
		//Check results
		assertThat(appartements, is(notNullValue()));
		assertTrue(appartements.size() >= 3);
		
		//check cache
		assertThat(cacheManager.getObject().getCache("bien").getSize(), greaterThanOrEqualTo(2));
	}

	@Test
	public void testSaveOrUpdate() {

		int oldSize = cacheManager.getObject().getCache("bien").getSize();
		
		//Adresse
		AdresseDto adresse = createAdresse("12 cité Adja Mareme", "2ème porte", 9900, "Grand Mbao", "Sénégal");
		
		//Bien
		BienDto bien = createBien("Keur Naby",adresse);
		
		//Appartements
		AppartementDto appartement = createAppartement("Dalal Diam", bien, EnumTypeAppartement.T2.getType(), 50D);
		
		//Check results
		assertThat(appartement.getId(), is(notNullValue()));
		
		//Check cache
		int newSize = cacheManager.getObject().getCache("bien").getSize();
		assertThat(newSize, greaterThan(oldSize));
	}

	
	@Test
	public void testDelete() {
		
		// Adresse
		AdresseDto adresse = createAdresse("124 cité promocap", "2ème porte", 9900, "Petit Mbao", "Sénégal");

		// Bien		
		BienDto bien = createBien("Keur Dabakh", adresse);
		
		// Check results
		Long id = bien.getId();
		assertThat(bien.getId(), is(notNullValue()));
		
		//Appartement
		AppartementDto appartement = createAppartement("Dalal Diam", bien, EnumTypeAppartement.T2.getType(), 50D);
		
		// Check results
		assertThat(appartement.getId(), is(notNullValue()));
		
		//load and check previous appart
		AppartementDto entite = appartementService.findById(appartement.getId());
		assertThat(entite, is(notNullValue()));
		
		//Call services
		appartementService.delete(entite);
		entite = appartementService.findById(entite.getId());
		//Check
		assertThat(entite, is(nullValue()));
		
		//Call services
		bienService.delete(bien);
		bien = bienService.findById(id);
		//Check
		assertThat(bien, is(nullValue()));
	}		
}
