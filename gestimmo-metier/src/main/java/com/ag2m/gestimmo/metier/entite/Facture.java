/**
 * 
 */
package com.ag2m.gestimmo.metier.entite;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author mombaye
 *
 * Classe représentant une facture
 */
@Entity
@Table(name="FACTURE")
@Getter @Setter @ToString(exclude= {"reservations"})
public class Facture extends Identifiant<Long> implements Serializable{


	private static final long serialVersionUID = -5232771257316030940L;
	
	@ManyToOne
	@JoinColumn(name="id_client", nullable=false)
	private Client client;
	 
	@OneToMany(fetch=FetchType.LAZY, orphanRemoval=true, mappedBy="facture")
	private List<Reservation> reservations;
	
	@Transient
	private Double taxeSejour;
	
	@ManyToOne
	@JoinColumn(name="id_adresse_facturation", nullable=false)
	private Adresse adresseFacturation;
	
	@Transient
	private Double tva;
	
	@Column(name="remise")
	private Double remise;
	
}
