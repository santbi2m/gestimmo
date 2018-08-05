package com.ag2m.gestimmo.metier.ioparam;

import org.joda.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author asouane
 * 
 * Classe contenant les critères communs de recherche 
 * de facture et devis
 */
@Getter @Setter
@ToString
public class CommonFactureCriteria  {


	private String nom;
	
	private String prenom;
	
	private String adresseEmail;
	
	private String telephone;
	
	private String numero;
	
	private LocalDateTime dateCheckin;
	
	private LocalDateTime dateCheckout;
}
