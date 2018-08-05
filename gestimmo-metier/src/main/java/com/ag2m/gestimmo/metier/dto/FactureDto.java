/**
 * 
 */
package com.ag2m.gestimmo.metier.dto;

import java.io.Serializable;
import java.util.List;


import org.joda.time.LocalDateTime;

import com.ag2m.gestimmo.metier.utils.CustomDateJsonDeserializer;
import com.ag2m.gestimmo.metier.utils.CustomDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author mombaye
 *
 * Classe représentant un dto d'une facture
 */
@Getter @Setter @ToString(exclude= {"reservations"})
public class FactureDto extends IdentifiantDto implements Serializable{

	private static final long serialVersionUID = -3277392184419996707L;

	private ClientDto client;
	
	private Double taxeSejour;
	
	private AdresseDto adresseFacturation;
	
	private Double tva;
	
	private Double remise;
	
	private String numeroFacture;
	
	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonDeserialize(using = CustomDateJsonDeserializer.class)
	private LocalDateTime dateCreation;
	
	private List<ReservationDto> reservations;
	
}
