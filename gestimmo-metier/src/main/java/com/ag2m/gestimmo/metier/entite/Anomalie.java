/**
 * 
 */
package com.ag2m.gestimmo.metier.entite;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * @author mombaye
 * 
 * Classe représentant une anomalie
 * dans une appartement
 */
@Entity
@Table(name="ANOMALIE")
@Getter @Setter @ToString
public class Anomalie extends Identifiant<Long> implements Serializable {

	private static final long serialVersionUID = -177201689003582371L;

	@Column(name="titre", nullable=false)
	private String titre;
	
	@Column(name="description", nullable=false)
	private String description;
	
	@Column(name="statut_anomalie", nullable=false)
	private String statut;
	
	@Column(name="date_ouverture", nullable=false)
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime dateOuverture;
	
	@Column(name="date_traitement")
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime dateTraitement;
	
	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.ALL})
	@JoinColumn(name="id_appartement", nullable=false)
	private Appartement appartement;
	
	@Column(name="commentaire")
	private String commentaire;

}
