/**
 * 
 */
package com.ag2m.gestimmo.metier.service;

import java.util.List;
import java.util.Map;

import org.joda.time.LocalDateTime;

import com.ag2m.gestimmo.metier.dto.ReservationDto;
import com.ag2m.gestimmo.metier.entite.Reservation;
import com.ag2m.gestimmo.metier.exception.FunctionalException;
import com.ag2m.gestimmo.metier.exception.TechnicalException;
import com.ag2m.gestimmo.metier.ioparam.ReservationCriteria;
import com.ag2m.gestimmo.metier.ioparam.UniteReservation;

/**
 * @author mombaye
 *
 */
public interface ReservationService {

	/**
	 *  Charge la reservation dont l'id 
	 *  est passé en paramètre.
	 * 
	 * @param id
	 * @return
	 * @throws TechnicalException 
	 */
	public ReservationDto findReservationById(Long id) throws TechnicalException;
	
	/**
	 * Retourne toutes les réservations en BDD, 
	 * sans filtre.
	 * 
	 * @return
	 */
	public List<ReservationDto> loadAllReservation();
	
	/**
	 * <p>
	 * Réserve un ou plusieurs appartements, 
	 * pour un client données.
	 * </p>
	 * 
	 * <p>
	 * @param entite
	 * @return
	 * @throws TechnicalException 
	 * @throws FunctionalException
	 * </p> 
	 */
	public ReservationDto createReservation(ReservationDto entite) throws TechnicalException, FunctionalException;
	
	/**
	 * <p>
	 * Permet de supprimer la Reservation en entrée.
	 * <p>
	 * 
	 * <p>
	 * @param entite
	 * @return
	 * @throws TechnicalException 
	 * </p>
	 */
	public boolean deleteReservation(ReservationDto entite) throws TechnicalException;
	
	/**
	 * <p>
	 * Permet de combiner tous les critères possibles
	 * de recherche de reservation et de retourner les
	 * reservations éligibles.
	 * </p>
	 * 
	 * <p>
	 * @param reservationCriteria
	 * @return
	 * @throws FunctionalException
	 * @throws TechnicalException
	 * </p>
	 */
	List<ReservationDto> findReservationByCriteria(ReservationCriteria reservationCriteria) throws TechnicalException, FunctionalException;

	/**
	 * <p>
	 * Permet de mettre à jour une réservation 
	 * </p>
	 * 
	 * <p>
	 * @param reservationDto
	 * @return
	 * @throws TechnicalException
	 * </p>
	 */
	ReservationDto updateReservation(ReservationDto reservationDto) throws TechnicalException;

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
	 * @return Une map d'id appartement et des reservations associées.
	 * </p>
	 * <p>
	 * @throws TechnicalException
	 * @throws FunctionalException
	 * </p>
	 */
	Map<Long, List<Reservation>> findReservationByPeriodAndBien(LocalDateTime dateDebut, LocalDateTime dateFin, Long idBien)
			throws TechnicalException, FunctionalException;

	/**
	 *  <p>
	 * Permet de vérifier un conflit
	 * de reservation.
	 * Renvoie true s'il existe une réservation dans la période
	 * avec un statut "Enregistré", "Confirmé", ou "En attente"
	 *  </p>
	 *  
	 *   <p>
	 * @param reservationDto
	 * @param statutAutorisee
	 * @return
	 *  </p>
	 *  
	 *   <p>
	 * @throws TechnicalException 
	 * @throws FunctionalException 
	 *  </p>
	 */
	boolean hasValideBookingInPeriod(ReservationDto reservationDto, List<String> statutAutorisee) throws FunctionalException, TechnicalException;

	
	/**
	 * <p>
	 * Service de génération du planning de réservation.
	 * Pour chacune des nuitées de la plage [dateDebut - dateFin],
	 * Ce service permet d'indiquer si la nuitée est réservée ou pas.
	 * </p> 
	 * 
	 * <p>
	 * @param dateDebut
	 * @param dateFin
	 * @param idBien
	 * @return
	 * </p>
	 * 
	 * <p>
	 * @throws FunctionalException
	 * @throws TechnicalException
	 * </p>
	 */
	Map<Long, List<UniteReservation>> loadSchedule(LocalDateTime dateDebut, LocalDateTime dateFin, Long idBien)
			throws FunctionalException, TechnicalException;

	/**
	 * Permet l’annulation d’une réservation Enrégistrée, confirmée ou En attente.
	 * @param reservationDto
	 * @return
	 * @throws TechnicalException 
	 */
	ReservationDto cancelReservation(ReservationDto reservationDto) throws TechnicalException;
}
