/**
 * 
 */
package com.ag2m.gestimmo.metier.dto;

import java.io.Serializable;
import java.util.List;

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
	
	private List<ReservationDto> reservations;
	
	private List<AnomalieDto> anomalies;
	
}
