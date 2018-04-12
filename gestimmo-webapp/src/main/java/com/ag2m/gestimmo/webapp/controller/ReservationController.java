package com.ag2m.gestimmo.webapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ag2m.gestimmo.metier.dto.ReservationDto;
import com.ag2m.gestimmo.metier.service.AppartementService;
import com.ag2m.gestimmo.metier.service.ReservationService;

@RestController
public class ReservationController {

	@Autowired
	private AppartementService appartementService;
	
	@Autowired
	private ReservationService reservationService;
	
	
	/**
	 * Retourne toutes les reservations sous format Json
	 * 
	 * @return
	 */
		@RequestMapping(value = "/reservations", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	    public @ResponseBody List<ReservationDto> findAllReservation() {
	        List<ReservationDto> reservations = reservationService.findAll();
	        return reservations;
	    }
	  
	     
		/**
		 * Retourne la reservation dont l'id est en paramètre, sous format Json
		 * 
		 * @return
		 */
	    @RequestMapping(value = "/reservations/id/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	    public @ResponseBody ReservationDto getReservation(@PathVariable("id") long id) {

	    	ReservationDto reservation = reservationService.findById(id);
	     
	    	return reservation;
	    }
	    
	    /**
		 * Crée une nouvelle reservation
		 * 
		 */
//	    @RequestMapping(value = "/reservations/save/idAppartement/{id}", method = RequestMethod.POST)
//	    public @ResponseBody void saveReservation(@PathVariable("id") long idBien) {
//
//	    	Bien bien = bienService.findById(idBien);
//	    	Reservation reservation = new Reservation();
//	    	reservation.setLibelle("Gokhou Mbath");
//	    	reservation.setBien(bien);
//	    	reservation.setType(EnumTypeAppartement.T2.getType());
//	    	reservationService.saveOrUpdate(appartement);
//	     
//	    }
	    
	    	/**
			 * supprime le bien dont l'id est en paramètre
			 * 
			 */
		    @RequestMapping(value = "/reservations/delete/id/{id}", method = RequestMethod.DELETE)
		    public @ResponseBody void deleteAppartement(@PathVariable("id") long id) {
		    	ReservationDto reservation = reservationService.findById(id);
		    	reservationService.delete(reservation);
		     
		    }
}
