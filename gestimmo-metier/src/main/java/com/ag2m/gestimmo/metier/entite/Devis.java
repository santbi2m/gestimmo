/**
 * 
 */
package com.ag2m.gestimmo.metier.entite;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author mombaye
 *
 * Classe représentant une facture
 */
@Entity
@Table(name="DEVIS")
@Getter @Setter @ToString(exclude= {"facture"})
public class Devis extends Identifiant<Long> implements Serializable{

	private static final long serialVersionUID = -1004275906364373313L;

	@Column(name="nom", nullable=false)
	private String nom;
	
	@Column(name="prenom", nullable=false)
	private String prenom;
	
	@Column(name="adresse_email", nullable=false)
	private String adresseEmail;
	
	@Column(name="telephone")
	private String telephone;
	
//	dateChekin
	
//	dateCheckout
	
//	numeroDevis
	
	
	@Lob
	@Column(nullable = false)
	private byte[] facture;	 
	
}
