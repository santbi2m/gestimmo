/**
 * 
 */
package com.ag2m.gestimmo.metier.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author mombaye
 *
 */
public class AppartementDto extends IdentifiantDto implements Serializable {

	private static final long serialVersionUID = -7712191825501565673L;

	private String libelle;
	
	private String type;
	
	private BienDto bien;
	
	private List<ReservationDto> reservations;
	
	private List<AnomalieDto> anomalies;
	

	/**
	 * @return the libelle
	 */
	public String getLibelle() {
		return libelle;
	}

	/**
	 * @param libelle the libelle to set
	 */
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the bien
	 */
	public BienDto getBien() {
		return bien;
	}

	/**
	 * @param bien the bien to set
	 */
	public void setBien(BienDto bien) {
		this.bien = bien;
	}

	/**
	 * @return the reservations
	 */
	public List<ReservationDto> getReservations() {
		return reservations;
	}

	/**
	 * @param reservations the reservations to set
	 */
	public void setReservations(List<ReservationDto> reservations) {
		this.reservations = reservations;
	}

	/**
	 * @return the anomalies
	 */
	public List<AnomalieDto> getAnomalies() {
		return anomalies;
	}

	/**
	 * @param anomalies the anomalies to set
	 */
	public void setAnomalies(List<AnomalieDto> anomalies) {
		this.anomalies = anomalies;
	}

	
}
