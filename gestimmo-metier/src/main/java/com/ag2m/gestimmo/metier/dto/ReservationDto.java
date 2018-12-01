package com.ag2m.gestimmo.metier.dto;

import java.util.List;

import org.joda.time.LocalDateTime;

import com.ag2m.gestimmo.metier.ioparam.IPeriode;
import com.ag2m.gestimmo.metier.utils.CustomDateJsonDeserializer;
import com.ag2m.gestimmo.metier.utils.CustomDateSerializer;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

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
public class ReservationDto extends IdentifiantDto implements IPeriode {

	private static final long serialVersionUID = 5157786246432635298L;

	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonDeserialize(using = CustomDateJsonDeserializer.class)
	private LocalDateTime dateCheckin;
	
	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonDeserialize(using = CustomDateJsonDeserializer.class)
	private LocalDateTime dateCheckout;
	
	private String note;
	
	private Boolean petitDej;
	
//	@JsonManagedReference
	private List<AppartementDto> appartements;
	
	private String statut;

	private Double prix;
	
	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonDeserialize(using = CustomDateJsonDeserializer.class)
	private LocalDateTime dateCreation;
	
	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonDeserialize(using = CustomDateJsonDeserializer.class)
	private LocalDateTime dateAnnulation;	
	
	private ClientDto client;
	
	private FactureDto facture;
}
