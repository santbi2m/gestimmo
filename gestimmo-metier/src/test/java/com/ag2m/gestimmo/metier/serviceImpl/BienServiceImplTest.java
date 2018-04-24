/**
 * 
 */
package com.ag2m.gestimmo.metier.serviceImpl;

import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

import com.ag2m.gestimmo.metier.dto.AppartementDto;
import com.ag2m.gestimmo.metier.dto.BienDto;
import com.ag2m.gestimmo.metier.enumeration.EnumTypeAppartement;


import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.*;

/**
 * @author mombaye
 *
 */
public class BienServiceImplTest extends AbstractCommonTest{

	

	@Test
	public void testFindAll() {

		createBien("Dianna Firdawsi", "120 cité Azur", null, "Mermoz", 99000, "Sénégal");
		createBien("Dianna Mahwa", "150 avenue Malick Sy", null, "Point E", 99000, "Sénégal");
		
		List<BienDto> biens = bienService.findAll();
		assertThat(biens, is(notNullValue()));
		assertThat(biens.size(), greaterThanOrEqualTo(2));

		assertThat(cacheManager.getObject().getCache("bien").getSize(), greaterThanOrEqualTo(2));
		
	}

	@Test
	public void testSaveOrUpdate() {
		
		int oldSize = cacheManager.getObject().getCache("bien").getSize();
		
		BienDto bien = createBien("Keur Dabakh", "124 cité promocap", "2ème porte", "Petit Mbao", 99000, "Sénégal");
		bien = bienService.findById(bien.getId());
		assertThat(bien.getId(), is(notNullValue()));
		
		int newSize = cacheManager.getObject().getCache("bien").getSize();
		assertThat(newSize, greaterThan(oldSize));
	}

	
	@Test
	public void testDelete() {
		
		BienDto bien = createBien("Keur Dabakh", "124 cité promocap", "2ème porte", "Petit Mbao", 99000, "Sénégal");
		assertThat(bien.getId(), is(notNullValue()));
		
		BienDto entite = bienService.findById(bien.getId());
		assertThat(entite, is(notNullValue()));
		
		bienService.delete(entite);
		
		entite = bienService.findById(bien.getId());
		assertThat(entite, is(nullValue()));
		
	}	
	
	
	@Test
	public void testDeleteBienWithAppart() {
		
		BienDto bien = createBien("Wakeur Meissa", "12 cité Fadia", null, "Sacré coeur", 99000, "Sénégal");
		AppartementDto app1= createAppartement("Dalal Diam", bien, EnumTypeAppartement.T2.getType());
		AppartementDto app2= createAppartement("Dem Deloussi", bien, EnumTypeAppartement.T3.getType());
		AppartementDto app3= createAppartement("Tawfekh", bien, EnumTypeAppartement.STUDIO.getType());
		
		bienService.delete(bien);
		bien = bienService.findById(bien.getId());
		app1 = appartementService.findById(app1.getId());
		app2 = appartementService.findById(app2.getId());
		app3 = appartementService.findById(app3.getId());
		
		assertThat(bien, is(nullValue()));
		assertThat(app1, is(nullValue()));
		assertThat(app2, is(nullValue()));
		assertThat(app3, is(nullValue()));
	}
}
