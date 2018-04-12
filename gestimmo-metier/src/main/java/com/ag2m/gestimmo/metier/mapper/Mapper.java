package com.ag2m.gestimmo.metier.mapper;

import java.util.Arrays;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.ag2m.gestimmo.metier.dto.AnomalieDto;
import com.ag2m.gestimmo.metier.dto.AppartementDto;
import com.ag2m.gestimmo.metier.dto.BienDto;
import com.ag2m.gestimmo.metier.dto.ReservationDto;
import com.ag2m.gestimmo.metier.entite.Anomalie;
import com.ag2m.gestimmo.metier.entite.Appartement;
import com.ag2m.gestimmo.metier.entite.Bien;
import com.ag2m.gestimmo.metier.entite.Reservation;

@org.mapstruct.Mapper(componentModel = "spring")
public interface Mapper {

	
	/**
	 * Map un Objet Bien en BienDto
	 * Ignore le mapping de Bien dans les objets Appartement afin d'éviter 
	 * un mapping cyclique.
	 * Ce mapping sera géré par processAppartementForBienDto
	 * 
	 * @param bien
	 * @return
	 */
	@Mapping(target = "appartements", ignore = true)
	public abstract BienDto bienToBienDto(Bien bien);
	
	/**
	 * Gère le mapping de l'objet Bien contenu dans Appartement.
	 * Elle sera appelée par bienToBienDto
	 * 
	 * @param bienDto
	 */
	 @AfterMapping
	default void processAppartementForBienDto(@MappingTarget BienDto bienDto) {
		 	if(bienDto.getAppartements() != null) {
		 		
		 		bienDto.getAppartements().forEach(app -> app.setBien(bienDto));
		 	}
	  }
	
 	/**
	 * Map un Objet BienDto en Bien
	 * Ignore le mapping de BienDto dans les objets AppartementDto afin d'éviter 
	 * un mapping cyclique.
	 * Ce mapping sera géré par processAppartementForBien
	 * 
	 * @param bien
	 * @return
	 */
	@Mapping(target = "appartements", ignore = true)
	Bien bienDtoToBien(BienDto bien);
	
	/**
	 * Gère le mapping de l'objet Bien contenu dans Appartement.
	 * Elle sera appelée par bienToBienDto
	 * 
	 * @param bien
	 */
	 @AfterMapping
		default void processAppartementForBien(@MappingTarget Bien bien) {
			 if(bien.getAppartements() != null) {
				 bien.getAppartements().forEach(app -> app.setBien(bien));
			 }
			        
		 }
	
	 	/**
		 * Map un Objet Appartement en AppartementDto
		 * Ignore le mapping de l'objet Appartement dans les objets Anomalie afin d'éviter 
		 * un mapping cyclique.
		 * Ce mapping sera géré par processAnomalieForAppartDto
		 * 
		 * @param appartement
		 * @return
		 */
	@Mapping(target = "anomalies", ignore = true)
	AppartementDto appartementToAppartementDto(Appartement appartement);
	
	 	/**
		 * Gère le mapping de l'objet Appartement contenu dans Anomalie.
		 * Elle sera appelée par appartementToAppartementDto
		 * 
		 * @param appartementDto
		 */
	 @AfterMapping
	default void processAnomalieForAppartDto(@MappingTarget AppartementDto appartementDto) {
		 	if(appartementDto.getAnomalies()!= null) {
		 		appartementDto.getAnomalies().forEach(ano -> ano.setAppartement(appartementDto));
		 	}
		 	
	  }

	 
	 	/**
	 	 * Map un Objet AppartementDto en Appartement
		 * Ignore le mapping de l'objet AppartementDto dans les objets AnomalieDto afin d'éviter 
		 * un mapping cyclique.
		 * Ce mapping sera géré par processAnomalieForAppart
	 	 * 
	 	 * @param appartementDto
	 	 * @return
	 	 */
	@Mapping(target = "anomalies", ignore = true)
	Appartement appartementDtoToAppartement(AppartementDto appartementDto);
	
	 /**
		 * Gère le mapping de l'objet AppartementDto contenu dans AnomalieDto.
		 * Elle sera appelée par appartementDtoToAppartement
		 * 
		 * @param appartementDto
		 */
	 @AfterMapping
		default void processAnomalieForAppart(@MappingTarget Appartement appartement) {
			if(appartement.getAnomalies()!= null) {
				appartement.getAnomalies().forEach(ano -> ano.setAppartement(appartement));
		 	}
			
		 }

	 
	/**
	 *  Map un Objet Anomalie en AnomalieDto
	 *  
	 * @param anomalie
	 * @return
	 */
	AnomalieDto anomalieToAnomalieDto(Anomalie anomalie);
	
	/**
	 *  Map un Objet AnomalieDto en Anomalie
	 *  
	 * @param anomalieDto
	 * @return
	 */
	Anomalie anomalieDtoToAnomalie(AnomalieDto anomalieDto);
	
	/**
	 *  Map un Objet Reservation en ReservationDto
	 *  
	 * @param reservation
	 * @return
	 */
	@Mapping(target = "appartements", ignore = true)
	ReservationDto reservationToReservationDto(Reservation reservation);
	
	@AfterMapping
	default void mapAppartIntoResaDto(@MappingTarget AppartementDto appartementDto) {
		
		if(appartementDto.getReservations() != null) {
			
			appartementDto.getReservations().forEach(resa -> {
				
				if(resa.getAppartements() != null) {
					resa.getAppartements().add(appartementDto);
				}else {
					resa.setAppartements(Arrays.asList(appartementDto));
				}
			});
		}
			
 	}
	
	
	/**
	 *  Map un Objet ReservationDto en Reservation
	 *  
	 * @param anomalieDto
	 * @return
	 */
	@Mapping(target = "appartements", ignore = true)
	Reservation reservationDtoToReservation(ReservationDto reservationDto);
	
	
	@AfterMapping
	default void mapAppartIntoResa(@MappingTarget Appartement appartement) {
		
		if(appartement.getReservations() != null) {
			
			appartement.getReservations().forEach(resa -> {
				
				if(resa.getAppartements() != null) {
					resa.getAppartements().add(appartement);
				}else {
					resa.setAppartements(Arrays.asList(appartement));
				}
			});
		}
			
 	}
}
