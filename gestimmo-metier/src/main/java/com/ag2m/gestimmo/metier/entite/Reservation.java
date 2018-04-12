package com.ag2m.gestimmo.metier.entite;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;


/**
 * @author mombaye
 * 
 * Class pour la gesion des réservations d'appartements
 */
@Entity
@Table(name="RESERVATION")
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
	
	@ManyToMany(fetch=FetchType.LAZY, mappedBy="reservations")
	private List<Appartement> appartements;
	
	@Column(name="statut_reservation", nullable=false)
	private String statut;

	

	

	/**
	 * @return the dateCheckin
	 */
	public LocalDateTime getDateCheckin() {
		return dateCheckin;
	}

	/**
	 * @param dateCheckin the dateCheckin to set
	 */
	public void setDateCheckin(LocalDateTime dateCheckin) {
		this.dateCheckin = dateCheckin;
	}

	/**
	 * @return the dateCheckout
	 */
	public LocalDateTime getDateCheckout() {
		return dateCheckout;
	}

	/**
	 * @param dateCheckout the dateCheckout to set
	 */
	public void setDateCheckout(LocalDateTime dateCheckout) {
		this.dateCheckout = dateCheckout;
	}

	/**
	 * @return the statut
	 */
	public String getStatut() {
		return statut;
	}

	/**
	 * @param statut the statut to set
	 */
	public void setStatut(String statut) {
		this.statut = statut;
	}

	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * @param note the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * @return the petitDej
	 */
	public Boolean getPetitDej() {
		return petitDej;
	}

	/**
	 * @param petitDej the petitDej to set
	 */
	public void setPetitDej(Boolean petitDej) {
		this.petitDej = petitDej;
	}

	/**
	 * @return the appartements
	 */
	public List<Appartement> getAppartements() {
		return appartements;
	}

	/**
	 * @param appartements the appartements to set
	 */
	public void setAppartements(List<Appartement> appartements) {
		this.appartements = appartements;
	}
}
