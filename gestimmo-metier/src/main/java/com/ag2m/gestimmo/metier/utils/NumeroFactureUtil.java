/**
 * 
 */
package com.ag2m.gestimmo.metier.utils;

import java.util.Optional;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.ag2m.gestimmo.metier.constants.TechnicalErrorMessageConstants;
import com.ag2m.gestimmo.metier.exception.TechnicalException;

/**
 * Classe utilitaire permettant de gérer le numéro de facture et de devis.
 * Génère aussi le code de numéro de facture.
 * Celui-ci correspond aux 4 derniers caractères
 * du numéro de facture.
 * 
 * @author mombaye
 *
 */
public class NumeroFactureUtil {

	/** MIN_CODE code minimal d'un numéro de facture*/
	private static final String MIN_CODE = "0001";
	
	/** MAX_CODE code maximal d'un numéro de facture*/
	private static final String MAX_CODE = "9999";
	
	/** FORMAT format d'un code de numéro de facture*/
	private static final String FORMAT = "%04d";
	
	/** Préfixe des numéro de facture et devis*/
	private static final String PREFIXE = "GI";
	
	/** Sufixe des numéro de facture */
	public static final String SUFFIXE_FT = "FT";
	
	/** Sufixe des numéro de facture et devis*/
	public static final String SUFFIXE_DV = "DV";
	
	/** FORMAT DATE yyyyMMdd */
	private static final String FORMAT_DATE = "yyyyMMdd";
	
	
	
	/**
 	 *	Cette fonction permet de mettre ce code
	 * sur 4 caractères si celui ne l'est pas déjà.
	 * 
	 * @param code : Le dide à formater
	 * @return : Le code formaté
	 * @throws TechnicalException 
	 */
	private static String formateCodeFactureIn4Digit(String code) throws TechnicalException {
		//Le code ne devrait pas être null
		Optional.ofNullable(code).filter(cod 
				-> StringUtils.isNotEmpty(cod)).orElseThrow(() 
						-> new TechnicalException(TechnicalErrorMessageConstants.ERREUR_GEN_NUM_FACTURE));
	
		//Le code du numéro de facture en cours de correspond au précédent + 1
		int nextCode = Integer.valueOf(code) + 1;
		//Le code du numéro de facture est compléter avec 4 zéro devant
		return String.format(FORMAT, nextCode);
		}
	
	
	
	/**
	 * <p>
	 * Permet de générer ne numéro de facture ou devis suivant celui reçu en paramètre.
	 * Le format d'un numéro de facture / devis est  [PREFIXE + DATE COURANTE + SUFFIXE + CODE] (tout collé).
	 * Exemple "GI20180802FT0001" ou pour un devis "GI20180802DV0005"
	 * </p>
	 * 
	 * <p>
	 * @param lastFactureNumber : Dernier numéro de facture / devis en BDD 
	 * @param suffixe : suffixe du numéro de facture / devis (FT ou DV)
	 * @return
	 * </p>
	 * 
	 * @throws TechnicalException 
	 */
	public static String generateNextFactureNumberByActual(String lastFactureNumber, String suffixe) throws TechnicalException {
		
		DateTimeFormatter dtf = DateTimeFormat.forPattern(FORMAT_DATE);
		String currentDate =  LocalDateTime.now().toString(dtf);
		StringBuilder builder = new StringBuilder(PREFIXE).append(currentDate).append(suffixe);
		
		//Si aucune facture n'a été générée auparavant, il faut générer le numéro de la première facture avec MIN_CODE
		if(StringUtils.isEmpty(lastFactureNumber)) {
			return builder.append(MIN_CODE).toString();
		}
		
		//Récupérer le code du numéro de facture en paramétre
		String[] code = lastFactureNumber.split(suffixe);
		Optional.ofNullable(code).filter(cod 
				-> (cod.length > 1) ).orElseThrow(() 
						-> new TechnicalException(TechnicalErrorMessageConstants.ERREUR_FORMAT_NUM_FACTURE_INCORRECT));
		
		//Si le code du dernier numéro de fature est 9999, on recommence les code de facture avec 0001,
		//qui correspond à MIN_CODE.
		if(MAX_CODE.equals(code[1])) {
			return builder.append(MIN_CODE).toString();
		}
		
		//Si le code du dernier numéro de facture est entre [MIN_CODE et MAX_CODE] généré le suivant
		String nextCode = formateCodeFactureIn4Digit(code[1]);
		return builder.append(nextCode).toString();
	}
}
