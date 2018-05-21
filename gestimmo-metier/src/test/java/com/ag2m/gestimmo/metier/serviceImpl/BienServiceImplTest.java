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

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.*;

/**
 * @author mombaye
 *
 */
public class BienServiceImplTest extends AbstractCommonTest{

	

	@Test
	public void testFindAll() {

		//Adresse
		AdresseDto adresse = createAdresse("120 cité Azur", null, 9900, "Mermoz", "Sénégal");
		AdresseDto adresse2 = createAdresse("150 avenue Malick Sy", null, 9900, "Point E", "Sénégal");
		
		//Bien
		createBien("Dianna Firdawsi", adresse);
		createBien("Dianna Mahwa", adresse2);
		
		//Call services
		List<BienDto> biens = bienService.findAll();
		
		//Check results
		assertThat(biens, is(notNullValue()));
		assertThat(biens.size(), greaterThanOrEqualTo(2));
		
		//Check cache
		assertThat(cacheManager.getObject().getCache("gestimmo").getSize(), greaterThanOrEqualTo(2));
		
	}

	@Test
	public void testSaveOrUpdate() {
		
		int oldSize = cacheManager.getObject().getCache("gestimmo").getSize();
		
		// Adresse
		AdresseDto adresse = createAdresse("124 cité promocap", "2ème porte", 9900, "Petit Mbao", "Sénégal");
		
		//Bien
		BienDto bien = createBien("Keur Dabakh", adresse);
		
		//Call service
		bien = bienService.findById(bien.getId());
		
		//Check result
		assertThat(bien.getId(), is(notNullValue()));
		
		//Check cache
		int newSize = cacheManager.getObject().getCache("gestimmo").getSize();
		assertThat(newSize, greaterThan(oldSize));
	}

	
	@Test
	public void testDelete() {
		
		// Adresse
		AdresseDto adresse = createAdresse("124 cité promocap", "2ème porte", 9900, "Petit Mbao", "Sénégal");
		
		//Bien
		BienDto bien = createBien("Keur Dabakh", adresse);
		
		//Check result
		assertThat(bien.getId(), is(notNullValue()));
		
		//Call service
		BienDto entite = bienService.findById(bien.getId());
		
		//Check result
		assertThat(entite, is(notNullValue()));
		
		//Call services
		bienService.delete(entite);
		entite = bienService.findById(bien.getId());
		
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
		bienService.delete(bien);
		bien = bienService.findById(bien.getId());
		app1 = appartementService.findAppartementById(app1.getId());
		app2 = appartementService.findAppartementById(app2.getId());
		app3 = appartementService.findAppartementById(app3.getId());
		
		//Check results
		assertThat(bien, is(nullValue()));
		assertThat(app1, is(nullValue()));
		assertThat(app2, is(nullValue()));
		assertThat(app3, is(nullValue()));
	}

}
