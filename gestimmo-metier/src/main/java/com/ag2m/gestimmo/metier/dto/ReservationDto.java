package com.ag2m.gestimmo.metier.dto;

import java.io.Serializable;
import java.util.List;

import org.joda.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author mombaye
 * 
 * Class pour la gesion des réservations d'appartements
 */
@Getter @Setter
@ToString(exclude= {"appartements"})
public class ReservationDto extends IdentifiantDto implements Serializable {

	private static final long serialVersionUID = 5157786246432635298L;

	private LocalDateTime dateCheckin;
	
	private LocalDateTime dateCheckout;
	
	private String note;
	
	private Boolean petitDej;
	
	private List<AppartementDto> appartements;
	
	private String statut;
	
}
