/**
 * 
 */
package com.ag2m.gestimmo.metier.entite.referentiel;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ag2m.gestimmo.metier.entite.Identifiant;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author mombaye
 *
 * Classe représentant une remise
 */
@Entity
@Table(name="REF_REMISE")
@Getter @Setter @ToString
public class Remise extends Identifiant<Long> implements Serializable{

	private static final long serialVersionUID = 5514182122741526204L;

	@Column(name="remise")
	private Integer remsie;
	
	@Column(name="libelle")
	private String libelle;
	
}
