/**
 * 
 */
package com.ag2m.gestimmo.metier.dto;

import java.io.Serializable;

import org.joda.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * @author mombaye
 *
 */
@Getter @Setter @ToString
public class AnomalieDto extends IdentifiantDto implements Serializable {


	private static final long serialVersionUID = 8737821249022500604L;

	private String titre;
	
	private String description;
	
	private String statut;
	
	private LocalDateTime dateOuverture;
	
	private LocalDateTime dateTraitement;
	
	private AppartementDto appartement;
	
	private String commentaire;
	
}
