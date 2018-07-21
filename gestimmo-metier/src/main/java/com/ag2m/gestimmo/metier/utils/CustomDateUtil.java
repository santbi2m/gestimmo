/**
 * 
 */
package com.ag2m.gestimmo.metier.utils;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.ag2m.gestimmo.metier.constants.TechnicalErrorMessageConstants;
import com.ag2m.gestimmo.metier.enumeration.EnumOptionFormatDate;
import com.ag2m.gestimmo.metier.exception.TechnicalException;

/**
 * Classe utilitaire permettant 
 * de gérer les traitement de Date. 
 * 
 * @author mombaye
 *
 */
public final class CustomDateUtil {

	/** Format de date dd-MM-yyyy hh:mm:ss */
	public static final DateTimeFormatter DATE_TIME_FORMAT 
    = DateTimeFormat.forPattern("dd-MM-yyyy HH:mm:ss") ;
	
	/** Format de date dd-MM-yyyy */
	public static final DateTimeFormatter DD_MM_YYYY 
    = DateTimeFormat.forPattern("dd-MM-yyyy") ;
	
	
	
	/**
	 * Retourne la date en paramètre
	 * En calculant les heures, minutes, secondes
	 * selon l'option de formatage choisie
	 *  
	 * @param date
	 * @param option
	 * @return 
	 * @throws TechnicalException 
	 */
	public static LocalDateTime formatDateByFormatOption(LocalDateTime date, EnumOptionFormatDate option) throws TechnicalException {
		if(date == null) {
			throw new TechnicalException(TechnicalErrorMessageConstants.ERREUR_FORMATTER_DATE_NULLE);
		}
		return date.withTime(option.getHour(), 0, 0, 0);
	}
	
	/**
	 * Vérifie si 2 dates 
	 * sont équivalentes sans tenir 
	 * comptes des heures, minutes, secondes
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 * @throws TechnicalException 
	 */
	public static boolean isSameDay(LocalDateTime date1, LocalDateTime date2) throws TechnicalException {
		
		//Si l'une des dates seulement est nulle
		//alors les dates sont différentes
		if(date1 != null && date2 == null) {
			return false;
		}
		
		if(date2 != null && date1 == null) {
			return false;
		}
		//Deux dates nulles sont équivalentes.
		if(date1 == null && date2 == null) {
			return true;
		}
		//Formatage des dates à 00:00:00
		date1 =  formatDateByFormatOption(date1, EnumOptionFormatDate.START_OF_DAY_FROMAT);
		date2 = formatDateByFormatOption(date2, EnumOptionFormatDate.START_OF_DAY_FROMAT);
		
		return date1.equals(date2);
	}
	
	
	/**
	 * Vérifie qu'une date est incluse
	 * dans une période ou égale à une des bornes
	 * de la période.
	 * 
	 * @param start
	 * @param end
	 * @param target
	 * @return
	 */
	public static boolean isBetweenInclusive(LocalDateTime start, LocalDateTime end, LocalDateTime target) {
		if(target == null) {
			return false;
		}
	    return !target.isBefore(start) && !target.isAfter(end);
	}
	
	/**
	 *  Vérifie qu'une période couvre une autre
	 * 
	 * @param start date de début de période
	 * @param end date de fin de période
	 * @param startTarget date de début à couvrir
	 * @param endTarget date de fin à couvrir
	 * @return
	 */
	public static boolean isPeriodBetweenInclusive(LocalDateTime start, LocalDateTime end,
			LocalDateTime startTarget, LocalDateTime endTarget){
		
		return isBetweenInclusive(start, end, startTarget)
		&& isBetweenInclusive(start, end, endTarget);
	}
}
