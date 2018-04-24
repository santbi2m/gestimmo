/**
 * 
 */
package com.ag2m.gestimmo.metier.serviceImpl;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ag2m.gestimmo.metier.dao.ReservationDao;
import com.ag2m.gestimmo.metier.dto.ReservationDto;
import com.ag2m.gestimmo.metier.entite.Reservation;
import com.ag2m.gestimmo.metier.mapper.Mapper;
import com.ag2m.gestimmo.metier.service.ReservationService;

import lombok.extern.log4j.Log4j;

/**
 * @author mombaye
 *
 */
@Service("reservationService")
@Log4j
public class ReservationServiceImpl implements ReservationService {

	@Autowired
	ReservationDao reservationDao;
	
	@Autowired
	Mapper mapper;
	
	@Transactional(readOnly = true)
	public ReservationDto findById(Long id) {
		
		//TODO Ajouter les logs
		// ----------   Exemple d'utilisation du logger avec lombok   ---------- 
		//
		Reservation reservation =  reservationDao.findById(Reservation.class, id);
		return mapper.reservationToReservationDto(reservation);
	}

	@Transactional(readOnly = true)
	public List<ReservationDto> findAll() {
		List<Reservation> results = reservationDao.findAll(Reservation.class);
		return results.stream().map(reservation -> 
				mapper.reservationToReservationDto(reservation))
				.collect(Collectors.<ReservationDto> toList());
	}

	@Transactional
	public ReservationDto saveOrUpdate(ReservationDto reservationDto) {
		Reservation entite = mapper.reservationDtoToReservation(reservationDto);
		 reservationDao.saveOrUpdate(entite);
		 return mapper.reservationToReservationDto(entite);
	}

	@Transactional
	public boolean delete(ReservationDto reservationDto) {
		Reservation entite = mapper.reservationDtoToReservation(reservationDto);
		return reservationDao.delete(entite);
	}		
}
