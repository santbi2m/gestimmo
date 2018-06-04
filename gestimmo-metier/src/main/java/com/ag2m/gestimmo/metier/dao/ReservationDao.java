/**
 * 
 */
package com.ag2m.gestimmo.metier.dao;

import java.util.List;

import org.joda.time.LocalDateTime;

import com.ag2m.gestimmo.metier.entite.Reservation;
import com.ag2m.gestimmo.metier.ioparam.ReservationCriteria;

/**
 * @author mombaye
 *
 */
public interface ReservationDao extends CommonDao<Long, Reservation>{

	
	/**
	 * Permet de combiner tous les critères possibles
	 * de recherche de reservation et de retourner les
	 * reservations éligibles.
	 * 
	 * @param reservationCriteria
	 * @return
	 */
	List<Reservation> findReservationByCriteria(ReservationCriteria reservationCriteria);

	/**
	 * <p>
	 * Pour un Bien donnée,
	 * Ce service permet de récupérer la liste de réservations
	 * de chaque appartement pour une période donnée.
	 * La période couvre au max un mois.
	 * </p>
	 * 
	 * <p>
	 * @param dateDebut
	 * @param dateFin
	 * @param idBien
	 * @return Liste des reservations éligibles.
	 * </p>
	 */
	List<Reservation> findReservationByPeriodAndBien(LocalDateTime dateDebut, LocalDateTime dateFin, Long idBien);

}
