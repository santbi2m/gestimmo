/**
 * 
 */
package com.ag2m.gestimmo.metier.ioparam;

import java.io.Serializable;

import org.joda.time.LocalDateTime;

/**
 * 
 * Interface contenant les 
 * accesseurs d'une période de
 * réservation datechekin - datecheckout
 * @author mombaye
 *
 */
public interface IPeriode extends Serializable{

	/**
	 * Retourne la date de 
	 * checkin de la période.
	 * 
	 * @return date to return
	 */
	LocalDateTime getDateCheckin();
	
	/**
	 * Retourne la date de 
	 * checkout de la période.
	 * 
	 * @return date to return
	 */
	LocalDateTime getDateCheckout();
	
	/**
	 * initialise la date de 
	 * checkout en paramètre.
	 * 
	 * @param dateCheckin
	 */
	void setDateCheckin(LocalDateTime dateCheckin);
	
	
	/**
	 * initialise la date de 
	 * checkout en paramètre.
	 * 
	 * @param dateCheckout
	 */
	void setDateCheckout(LocalDateTime dateCheckout);
}
