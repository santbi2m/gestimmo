/**
 * 
 */
package com.ag2m.gestimmo.metier.entite;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author mombaye
 *
 */
@Entity
@Table(name="APPARTEMENT")
public class Appartement extends Identifiant<Long> implements Serializable {

	private static final long serialVersionUID = -4633161879749573165L;

	@Column(name="libelle", nullable=false)
	private String libelle;
	
	@Column(name="type_appart", nullable=false)
	private String type;
	
	@ManyToOne
	@JoinColumn(name="id_bien", nullable=false)
	private Bien bien;
	
	@ManyToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinTable(name = "assoc_resa_appart", joinColumns = {
			@JoinColumn(name = "id_appartement", nullable = false, updatable = false) },
	inverseJoinColumns = { @JoinColumn(name = "id_reservation",
	nullable = false, updatable = false) })
	private List<Reservation> reservations;
	
	@OneToMany(fetch=FetchType.LAZY, orphanRemoval=true, mappedBy="appartement")
	private List<Anomalie> anomalies;
	

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
	public Bien getBien() {
		return bien;
	}

	/**
	 * @param bien the bien to set
	 */
	public void setBien(Bien bien) {
		this.bien = bien;
	}

	/**
	 * @return the reservations
	 */
	public List<Reservation> getReservations() {
		return reservations;
	}

	/**
	 * @param reservations the reservations to set
	 */
	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}

	/**
	 * @return the anomalies
	 */
	public List<Anomalie> getAnomalies() {
		return anomalies;
	}

	/**
	 * @param anomalies the anomalies to set
	 */
	public void setAnomalies(List<Anomalie> anomalies) {
		this.anomalies = anomalies;
	}

	
}
