package com.ag2m.gestimmo.metier.dto;

import java.io.Serializable;
import java.util.List;

import org.joda.time.LocalDateTime;

/**
 * @author mombaye
 * 
 * Class pour la gesion des réservations d'appartements
 */
public class ReservationDto extends IdentifiantDto implements Serializable {

	private static final long serialVersionUID = 5157786246432635298L;

	private LocalDateTime dateCheckin;
	
	private LocalDateTime dateCheckout;
	
	private String note;
	
	private Boolean petitDej;
	
	private List<AppartementDto> appartements;
	
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
	public List<AppartementDto> getAppartements() {
		return appartements;
	}

	/**
	 * @param appartements the appartements to set
	 */
	public void setAppartements(List<AppartementDto> appartements) {
		this.appartements = appartements;
	}
}
