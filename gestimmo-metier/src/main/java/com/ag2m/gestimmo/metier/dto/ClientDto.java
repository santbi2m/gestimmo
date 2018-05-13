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
 * Entité représentant un DTO de l'entité Client
 * 
 */
@Getter @Setter 
@ToString(exclude= {"reservations", "factures"})
public class ClientDto extends IdentifiantDto implements Serializable {

	private static final long serialVersionUID = 4202781122098752992L;

	private String nom;
	
	private String prenom;

	private String adresseEmail;
	
	private String numeroPieceIdentite;
	
	private String typePieceIdentite;
	
	private String telephone;
	
	private AdresseDto adresse;
	
	@JsonIgnore
	private List<ReservationDto> reservations;
	
	@JsonIgnore
	private List<FactureDto> factures;
}
