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
 * Classe représentant un bien immobiler
 */
@Getter @Setter @ToString(exclude= {"appartements"})
public class BienDto extends IdentifiantDto implements Serializable{

	private static final long serialVersionUID = 5713933136627087807L;

	private String libelle;
	
	@JsonIgnore
	private List<AppartementDto> appartements;
	
	private AdresseDto adresse;
}
