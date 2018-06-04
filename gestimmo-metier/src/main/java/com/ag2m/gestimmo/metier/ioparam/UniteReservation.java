package com.ag2m.gestimmo.metier.ioparam;

import java.util.List;

import org.joda.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Cet objet représente une unité de réservation.
 * Une unité de réservation est simplement une cellule 
 * dans le planning. Autrement dit une journée donnée
 * du planning de réservation.
 * 
 * @author mombaye
 *
 */
@Getter
@Setter
@ToString(exclude= {"idReservations"})
public class UniteReservation {

	/** La journée pour laquelle le planning de reservation est affiché */
	private LocalDate journee;
	
	/** La nuité correspondant à la journée du plannig */
	private Nuitee nuite;
	
	/** La liste de réservations dans la nuité.
	 *  Une seule réservation en cas de nuité
	 *  une ou plusieurs réservations en cas de day-use*/
	private List<Long> idReservations;
	
	/** l'appartement courant */
	private Long idAppartement;
}
