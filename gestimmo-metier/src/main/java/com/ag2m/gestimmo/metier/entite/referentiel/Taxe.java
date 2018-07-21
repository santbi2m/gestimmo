/**
 * 
 */
package com.ag2m.gestimmo.metier.entite.referentiel;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import com.ag2m.gestimmo.metier.entite.Identifiant;

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
public class Taxe extends Identifiant<Long> implements Serializable{

	private static final long serialVersionUID = 8646467370194037083L;

	@Column(name="tva")
	private Double tva;
	
	@Column(name="taxe_sejour")
	private Double taxeSejour;
	
	@Column(name="date_debut_validite", nullable = false)
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime dateDebutValidite;
	
	@Column(name="date_fin_valite")
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime dateFinValite;
	
}
