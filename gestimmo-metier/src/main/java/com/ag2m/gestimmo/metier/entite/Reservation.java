package com.ag2m.gestimmo.metier.entite;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
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
 * Class pour la gesion des réservations d'appartements
 */
@Entity
@Table(name="RESERVATION")
@Getter @Setter
@ToString(exclude= {"appartements"})
public class Reservation extends Identifiant<Long> implements Serializable {

	private static final long serialVersionUID = -5490847992909020809L;

	@Column(name="date_checkin", nullable=false)
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime dateCheckin;
	
	@Column(name="date_checkout", nullable=false)
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime dateCheckout;
	
	@Column(name="note")
	private String note;
	
	@Column(name="avec_petit_dej")
	private Boolean petitDej;
	
	@Column(name="statut_reservation", nullable=false)
	private String statut;
	
	@Column(name="prix", nullable=false)
	private Double prix;
	
	@Column(name="date_creation", nullable=false)
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime dateCreation;
	
	@Column(name="date_annulation")
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime dateAnnulation;	
	
	@ManyToMany(fetch=FetchType.LAZY, mappedBy="reservations")
	private List<Appartement> appartements;
	
	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.ALL})
	@JoinColumn(name="id_client", nullable=false)
	private Client client;
	
	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.ALL})
	@JoinColumn(name="id_facture")
	private Facture facture;
}
