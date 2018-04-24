/**
 * 
 */
package com.ag2m.gestimmo.metier.serviceImpl;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

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
		
		
		BienDto bien = createBien("Wakeur Meissa", "12 cité Fadia", null, "Sacré coeur", 99000, "Sénégal");
		createAppartement("Dalal Diam", bien, EnumTypeAppartement.T2.getType());
		createAppartement("Dem Deloussi", bien, EnumTypeAppartement.T3.getType());
		createAppartement("Tawfekh", bien, EnumTypeAppartement.STUDIO.getType());
		
		List<AppartementDto> appartements = appartementService.findAll();
		assertThat(appartements, is(notNullValue()));
		assertTrue(appartements.size() >= 3);

		assertThat(cacheManager.getObject().getCache("bien").getSize(), greaterThanOrEqualTo(2));
	}

	@Test
	public void testSaveOrUpdate() {

		int oldSize = cacheManager.getObject().getCache("bien").getSize();
		
		BienDto bien = createBien("Keur Naby", "12 cité Adja Mareme", "2ème porte", "Grand Mbao", 99000, "Sénégal");
		
		AppartementDto appartement = createAppartement("Dalal Diam", bien, EnumTypeAppartement.T2.getType());
		
		assertThat(appartement.getId(), is(notNullValue()));
		
		int newSize = cacheManager.getObject().getCache("bien").getSize();
		assertThat(newSize, greaterThan(oldSize));
	}

	
	@Test
	public void testDelete() {
		BienDto bien = createBien("Keur Dabakh", "124 cité promocap", "2ème porte", "Petit Mbao", 99000, "Sénégal");
		Long id = bien.getId();
		assertThat(bien.getId(), is(notNullValue()));
		
		AppartementDto appartement = createAppartement("Dalal Diam", bien, EnumTypeAppartement.T2.getType());
		assertThat(appartement.getId(), is(notNullValue()));
		
		AppartementDto entite = appartementService.findById(appartement.getId());
		assertThat(entite, is(notNullValue()));
		
		appartementService.delete(entite);
		entite = appartementService.findById(entite.getId());
		assertThat(entite, is(nullValue()));
		
		bienService.delete(bien);
		bien = bienService.findById(id);
		assertThat(bien, is(nullValue()));
	}		
}
