/**
 * 
 */
package com.ag2m.gestimmo.metier.entite.referentiel;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author mombaye
 *
 * Classe représentant un bien immobiler
 */
@Entity
@Table(name="TAXE")
@Getter @Setter @ToString
public class Taxe implements Serializable{

	private static final long serialVersionUID = 8646467370194037083L;

	@Id
	@Column(name="tva")
	private Double tva;
	
	@Column(name="taxe_sejour")
	private Double taxeSejour;
	
}
