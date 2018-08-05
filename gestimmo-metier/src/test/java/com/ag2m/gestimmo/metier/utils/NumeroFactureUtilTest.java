/**
 * 
 */
package com.ag2m.gestimmo.metier.utils;

import static org.junit.Assert.assertThat;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.ag2m.gestimmo.metier.constants.TechnicalErrorMessageConstants;
import com.ag2m.gestimmo.metier.exception.TechnicalException;

import static org.hamcrest.CoreMatchers.is;


/**
 * Permet de tester les fonctions de génération de 
 * numéros de facture et devis.
 * 
 * @author mombaye
 *
 */
public class NumeroFactureUtilTest {
	
	@Rule
	public ExpectedException thrown= ExpectedException.none();
	
	/** FORMAT DATE yyyyMMdd */
	private static final String FORMAT_DATE = "yyyyMMdd";
	
	/** Préfixe des numéro de facture et devis*/
	private static final String PREFIXE = "GI";
	
	/** Sufixe des numéro de facture */
	public static final String SUFFIXE_FT = "FT";
	
	/** Sufixe des numéro de facture et devis*/
	public static final String SUFFIXE_DV = "DV";
	
	/**
	 * generateNextFactureNumberByActual
	 * Cas de génération d'une première facture.
	 * @throws TechnicalException 
	 */
	@Test
	public void generateNextFactureNumberByActual_FirstOne() throws TechnicalException {
		
		String generatedNumber = NumeroFactureUtil.generateNextFactureNumberByActual(null, SUFFIXE_FT);
		String firstPart = getFirstPartOfFactureDevisNumber(SUFFIXE_FT);
		
		assertThat(firstPart + "0001", is(generatedNumber));
	}
	
	/**
	 * generateNextFactureNumberByActual
	 * Cas de génération d'une facture quelconque.
	 * @throws TechnicalException 
	 */
	@Test
	public void generateNextFactureNumberByActual_AnyOne() throws TechnicalException {
		
		String generatedNumber = NumeroFactureUtil.generateNextFactureNumberByActual("GI20180805FT0099", SUFFIXE_FT);
		String firstPart = getFirstPartOfFactureDevisNumber(SUFFIXE_FT);
		
		assertThat(firstPart + "0100", is(generatedNumber));
	}
	
	
	/**
	 * generateNextFactureNumberByActual
	 * Cas de génération d'une facture en recommençant le décompte
	 * au niveau du code de numéro de facture.
	 *
	 * Pour rappel au bout de 9999 on repart au 1er code 0001
	 *
	 * @throws TechnicalException 
	 */
	@Test
	public void generateNextFactureNumberByActual_LastOne() throws TechnicalException {
		
		String generatedNumber = NumeroFactureUtil.generateNextFactureNumberByActual("GI20180805FT9999", SUFFIXE_FT);
		String firstPart = getFirstPartOfFactureDevisNumber(SUFFIXE_FT);
		
		assertThat(firstPart + "0001", is(generatedNumber));
	}
	
	
	/**
	 * generateNextFactureNumberByActual
	 * Cas d'un numéro de facture invalide en BDD.
	 * 
	 * une exception est lancée
	 *
	 * @throws TechnicalException 
	 */
	@Test
	public void generateNextFactureNumberByActual_WrongOne() throws TechnicalException {
		
		//Assert that an exception has thrown
		thrown.expect(TechnicalException.class);
		thrown.expectMessage(TechnicalErrorMessageConstants.ERREUR_FORMAT_NUM_FACTURE_INCORRECT);
		
		NumeroFactureUtil.generateNextFactureNumberByActual("GI20180805F9", SUFFIXE_FT);
	}
	
	
	/**
	 * generateNextFactureNumberByActual
	 * Cas de génération d'un premier Devis.
	 * @throws TechnicalException 
	 */
	@Test
	public void generateNexDevisNumberByActual_FirstOne() throws TechnicalException {
		
		String generatedNumber = NumeroFactureUtil.generateNextFactureNumberByActual(null, SUFFIXE_DV);
		String firstPart = getFirstPartOfFactureDevisNumber(SUFFIXE_DV);
		
		assertThat(firstPart + "0001", is(generatedNumber));
	}
	
	/**
	 * generateNextFactureNumberByActual
	 * Cas de génération d'une facture quelconque.
	 * @throws TechnicalException 
	 */
	@Test
	public void generateNextDevisNumberByActual_AnyOne() throws TechnicalException {
		
		String generatedNumber = NumeroFactureUtil.generateNextFactureNumberByActual("GI20180805DV0099", SUFFIXE_DV);
		String firstPart = getFirstPartOfFactureDevisNumber(SUFFIXE_DV);
		
		assertThat(firstPart + "0100", is(generatedNumber));
	}
	
	
	/**
	 * generateNextFactureNumberByActual
	 * Cas de génération d'une facture en recommençant le décompte
	 * au niveau du code de numéro de facture.
	 *
	 * Pour rappel au bout de 9999 on repart au 1er code 0001
	 *
	 * @throws TechnicalException 
	 */
	@Test
	public void generateNextDevisNumberByActual_LastOne() throws TechnicalException {
		
		String generatedNumber = NumeroFactureUtil.generateNextFactureNumberByActual("GI20180805DV9999", SUFFIXE_DV);
		String firstPart = getFirstPartOfFactureDevisNumber(SUFFIXE_DV);
		
		assertThat(firstPart + "0001", is(generatedNumber));
	}
	
	
	/**
	 * generateNextFactureNumberByActual
	 * Cas d'un numéro de facture invalide en BDD.
	 * 
	 * une exception est lancée
	 *
	 * @throws TechnicalException 
	 */
	@Test
	public void generateNextFactureDevisByActual_WrongOne() throws TechnicalException {
		
		//Assert that an exception has thrown
		thrown.expect(TechnicalException.class);
		thrown.expectMessage(TechnicalErrorMessageConstants.ERREUR_FORMAT_NUM_FACTURE_INCORRECT);
		
		NumeroFactureUtil.generateNextFactureNumberByActual("GI20180805D9", SUFFIXE_DV);
	}
	
	
	/**
	 * Génération de la première partie d'un numéro de facture ou devis.
	 */
	private String getFirstPartOfFactureDevisNumber(String suffixe) {
		
		DateTimeFormatter dtf = DateTimeFormat.forPattern(FORMAT_DATE);
		String currentDate =  LocalDateTime.now().toString(dtf);
		StringBuilder builder = new StringBuilder(PREFIXE).append(currentDate).append(suffixe);
		
		return builder.toString();
	}
}
