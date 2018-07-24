/**
 * 
 */
package com.ag2m.gestimmo.metier.serviceImpl;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.containsInAnyOrder;

import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import com.ag2m.gestimmo.metier.config.ParamConfig;
import com.ag2m.gestimmo.metier.exception.TechnicalException;

/**
 * @author mombaye
 *
 *Classe de Tests des services de paramétrage
 */
public class ParametrageServiceTest extends AbstractCommonTest {
	
	
	/**
	 * Test le chargement des remises
	 */
	@Test
	public void loadRemisesTest() {
		
		//Chargement des remises
		parametrageService.loadAllRemise();
		
		//Vérification que les remises sont bien initialisées dans ParamConfig
		assertThat(ParamConfig.REMISES , is(notNullValue()));
		assertThat(ParamConfig.REMISES , is(not(empty())));
		
		//Vérification que toutes les remises de la BDD sont bien chargées.
		List<Integer> remises = ParamConfig.REMISES.stream()
													.map(remise -> remise.getRemsie())
													.collect(Collectors.toList());
		assertThat(remises , containsInAnyOrder (5, 10, 15, 20));
	}
	
	
	
	/**
	 * Test le chargement du paramètre PENALITE
	 * @throws TechnicalException 
	 */
	@Test
	public void loadPenaliteTest() throws TechnicalException {
		
		//Chargement des remise
		parametrageService.loadPourcentagePenanlite();
		
		//Vérification que les remises sont bien initialisées dans ParamConfig
		assertThat(ParamConfig.POURCENTAGE_PENALITE , is(notNullValue()));
		assertThat(ParamConfig.POURCENTAGE_PENALITE , is(100));
		
	}
	
	
	/**
	 * Test le chargement du paramètre SEUIL_NON_PENALISABLE
	 * @throws TechnicalException 
	 */
	@Test
	public void loadSeuilAnnulationGratuiteTest() throws TechnicalException {
		
		//Chargement des remise
		parametrageService.loadSeuilAnnulationGratuite();
		
		//Vérification que les remises sont bien initialisées dans ParamConfig
		assertThat(ParamConfig.SEUIL_ANNULATION_GRATUITE , is(notNullValue()));
		assertThat(ParamConfig.SEUIL_ANNULATION_GRATUITE , is(24));
		
	}
}
