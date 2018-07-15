/**
 * 
 */
package com.ag2m.gestimmo.metier.dto;

import java.io.Serializable;

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
 * Classe représentant une facture
 */
@Getter @Setter @ToString
public class DevisDto extends IdentifiantDto implements Serializable{

	private static final long serialVersionUID = 6333876141428450789L;

	private String nom;
	
	private String prenom;
	
	private String adresseEmail;
	
	private String telephone;
	
	private FactureDto facture;	 
	
	private String numeroDevis;
	
	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonDeserialize(using = CustomDateJsonDeserializer.class)
	private LocalDateTime dateChekin;
	
	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonDeserialize(using = CustomDateJsonDeserializer.class)
	private LocalDateTime dateCheckout;
	
	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonDeserialize(using = CustomDateJsonDeserializer.class)
	private LocalDateTime dateCreation;
	
}
