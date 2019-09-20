/**
 * 
 */
package com.ag2m.gestimmo.metier.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author mombaye
 *
 */
@Getter @Setter
@ToString(exclude= {"reservations", "anomalies"})
public class AppartementDto extends IdentifiantDto implements Serializable {

	
	private static final long serialVersionUID = -7712191825501565673L;

	private String libelle;
	
	private String type;
	
	private BienDto bien;
	
	private Double prix;
	
	@JsonIgnore
	private List<ReservationDto> reservations;
	
	@JsonIgnore
	private List<AnomalieDto> anomalies;
	
}
