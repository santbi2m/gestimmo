/**
 * 
 */
package com.ag2m.gestimmo.metier.dto;

import java.io.Serializable;

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
	
}
