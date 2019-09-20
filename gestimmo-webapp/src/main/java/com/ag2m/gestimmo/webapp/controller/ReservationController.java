package com.ag2m.gestimmo.webapp.controller;

import java.util.List;
import java.util.Optional;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ag2m.gestimmo.metier.constants.TechnicalErrorMessageConstants;
import com.ag2m.gestimmo.metier.dto.ReservationDto;
import com.ag2m.gestimmo.metier.exception.FunctionalException;
import com.ag2m.gestimmo.metier.exception.TechnicalException;
import com.ag2m.gestimmo.metier.ioparam.ReservationCriteria;
import com.ag2m.gestimmo.metier.service.ReservationService;

import lombok.extern.log4j.Log4j;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Controller
@Log4j
public class ReservationController {

	@Autowired
	private ReservationService reservationService;

	/**
	 * Retourne toutes les reservations sous format Json
	 * 
	 * @return
	 */
	@RequestMapping(value = "/reservations", method = RequestMethod.GET)
	public @ResponseBody List<ReservationDto> findAllReservation() {
		List<ReservationDto> reservations = reservationService.loadAllReservation();
		return reservations;
	}

	/**
	 * Retourne la reservation dont l'id est en paramètre, sous format Json
	 * 
	 * @return
	 * @throws TechnicalException
	 */
	@RequestMapping(value = "/reservations/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ReservationDto getReservation(@PathVariable("id") long id) throws TechnicalException {

		ReservationDto reservation = reservationService.findReservationById(id);

		return reservation;
	}
	
	
	/**
	 * Annuler la reservation dont l'id est en paramètre
	 * 
	 * @return
	 * @throws TechnicalException
	 */
	@RequestMapping(value = "/reservations/annuler/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ReservationDto getAnnulerReservation(@RequestBody ReservationDto reservation) throws TechnicalException {
	
		Optional.ofNullable(reservation).orElseThrow(() 
				-> new TechnicalException(TechnicalErrorMessageConstants.ERREUR_ENTREE_SUPP_NULL));
		
		
		return reservationService.cancelReservation(reservation);
	}


	/**
	 * Retourne la reservation dont l'id est en paramètre, sous format Json
	 * 
	 * @return
	 * @throws TechnicalException
	 */
	@RequestMapping(value = "/reservations/recherche", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<ReservationDto> getReservationCriteria(
			@RequestParam(value = "dateCheckin", required = true) final String dateCheckin,
			@RequestParam(value = "dateCheckout", required = true) final String dateCheckout,
			@RequestParam(value = "petitDej", required = false) final boolean petitDej,
			@RequestParam(value = "statut", required = false) final List<String> statut,
			@RequestParam(value = "prix", required = false) final Double prix
	) throws TechnicalException {

		DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

		ReservationCriteria reservationCriteria = new ReservationCriteria();
		reservationCriteria.setDateCheckin(LocalDateTime.parse(dateCheckin, formatter));
		reservationCriteria.setDateCheckout(LocalDateTime.parse(dateCheckout, formatter));
		reservationCriteria.setPetitDej(petitDej);
		reservationCriteria.setStatut(statut);
		

		List<ReservationDto> reservations = reservationService.findReservationByCriteria(reservationCriteria);

		return reservations;
	}

	/**
	 * Crée une nouvelle reservation
	 * @throws TechnicalException 
	 * @throws FunctionalException 
	 * 
	 */
	@RequestMapping(value = "/reservations/creation", method = RequestMethod.POST)
	public @ResponseBody ReservationDto createReservation(@RequestBody ReservationDto reservationDto) throws FunctionalException, TechnicalException  {

		Optional.ofNullable(reservationDto).orElseThrow(() 
				-> new TechnicalException(TechnicalErrorMessageConstants.ERREUR_ENTREE_SUPP_NULL));
		
		
		return reservationService.createReservation(reservationDto);
	}

	/**
	 * supprime le bien dont l'id est en paramètre
	 * 
	 * @throws TechnicalException
	 * 
	 */
	@RequestMapping(value = "/reservations/delete/id/{id}", method = RequestMethod.DELETE)
	public @ResponseBody void deleteAppartement(@PathVariable("id") long id) throws TechnicalException {
		ReservationDto reservation = reservationService.findReservationById(id);
		reservationService.deleteReservation(reservation);

	}
}
