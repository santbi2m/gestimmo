package com.ag2m.gestimmo.metier.ioparam;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author asouane
 * 
 * Classe contenant les critères de recherche 
 * d'appartements
 */
@Getter @Setter
@ToString
public class ClientCriteria  {


	private String nom;
	private String prenom;
	private String adresseEmail;
	private String numeroPiece;
	private String typePiece;
	private String telephone;
	private String adresse;
	private String complement;
	private Integer codePostal;  
	private String ville; 
	private String pays;
}
