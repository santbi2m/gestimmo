package com.ag2m.gestimmo.metier.ioparam;

import java.util.List;

import org.joda.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author mombaye
 * 
 * Classe contenant les critères de recherche 
 * d'appartements
 */
@Getter @Setter
@ToString
public class ReservationCriteria implements IPeriode {


	private static final long serialVersionUID = 2925143857001064676L;

	private LocalDateTime dateCheckin;
	
	private LocalDateTime dateCheckout;
	
	private Boolean petitDej;
	
	private List<String> statut;

	private Double prix;
	
	private List<Long> idAppartements;
	
	private Long idClient;
	
	private Long idFacture;
}
