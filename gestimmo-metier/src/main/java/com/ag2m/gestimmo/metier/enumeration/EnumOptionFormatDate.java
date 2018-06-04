package com.ag2m.gestimmo.metier.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 
 * @author mombaye
 * 
 * Enumérateur des options
 * possibles pour formatter une date.
 * 
 */
@Getter
@AllArgsConstructor
public enum EnumOptionFormatDate {

	/** L'heure de chechin est à 12h*/
	CHECKIN_FORMAT(12),
	/** L'heure de chekout est à 11h*/
	CHECKOUT_FORMAT(11),
	/** la journée commence à 0h*/
	START_OF_DAY_FROMAT(0);
	
	private int hour;
}
