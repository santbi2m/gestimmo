/**
 * 
 */
package com.ag2m.gestimmo.metier.serviceImpl;

import static org.junit.Assert.assertThat;

import org.joda.time.LocalDateTime;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

import com.ag2m.gestimmo.metier.config.ParamConfig;
import com.ag2m.gestimmo.metier.constants.FunctionnalErrorMessageConstants;
import com.ag2m.gestimmo.metier.constants.TechnicalErrorMessageConstants;
import com.ag2m.gestimmo.metier.entite.referentiel.Taxe;
import com.ag2m.gestimmo.metier.exception.FunctionalException;
import com.ag2m.gestimmo.metier.utils.CustomDateUtil;

/**
 * @author mombaye
 *
 *Classe de Tests des services de paramétrage
 */
public class ParametrageServiceTest extends AbstractCommonTest {
	
	/**
	 * Test le chargement de la taxe en cours de 
	 * validité
	 */
	@Test
	public void loadCurrentTaxeTest() {
		
		parametrageService.loadCurrentTaxe();
		
		assertThat(ParamConfig.TVA , is(Double.valueOf("20")));
		assertThat(ParamConfig.TAXE_SEJOUR , is(Double.valueOf("2")));
	}

	
	/**
	 * Test le chargement d'une taxe valide à date.
	 * Cas d'une date null
	 * @return
	 */
	@Test
	public void loadTaxeByDateNullTest() {
		
		//Assert that an exception has thrown
		thrown.expect(FunctionalException.class);
		thrown.expectMessage(FunctionnalErrorMessageConstants.ERREUR_PARAMETRAGE_DATE_NULL);
		
		//Call
		parametrageService.loadTaxeByDate(null);
	}
	
	
	/**
	 * Test le chargement d'une taxe valide à date.
	 * Cas d'une date sans taxe.
	 * @return
	 */
	@Test
	public void loadTaxeByDateNoResultFoundTest() {
		
		//Assert that an exception has thrown
		thrown.expect(FunctionalException.class);
		thrown.expectMessage(TechnicalErrorMessageConstants.TAXE_NON_PARAMETREE);
		
		//Date d'appel (hors période taxe)
		String date = "01-01-2017";
		LocalDateTime paramDate = CustomDateUtil.DD_MM_YYYY.parseLocalDateTime(date);
		
		//Appel
		parametrageService.loadTaxeByDate(paramDate);
	}
	
	/**
	 * Test le chargement d'une taxe valide à date.
	 * Cas d'une période avec présence de taxe.
	 * @return
	 */
	@Test
	public void loadTaxeByDateTest() {
		
		//Date d'appel (valide à la période taxe)
		String date = "01-05-2018";
		LocalDateTime paramDate = CustomDateUtil.DD_MM_YYYY.parseLocalDateTime(date);
		//Appel
		Taxe taxe = parametrageService.loadTaxeByDate(paramDate);
		
		//Vérifications
		assertThat(taxe, is(notNullValue()));
		assertThat(taxe.getDateDebutValidite(), is(notNullValue()));
		assertThat(taxe.getDateFinValite(), is(notNullValue()));
		boolean isDateBetween = CustomDateUtil.isBetweenInclusive(taxe.getDateDebutValidite(), taxe.getDateFinValite(), paramDate);
		assertThat(isDateBetween, is(true));
		assertThat(taxe.getTva() , is(Double.valueOf("18")));
		assertThat(taxe.getTaxeSejour() , is(Double.valueOf("5")));
	}
}
