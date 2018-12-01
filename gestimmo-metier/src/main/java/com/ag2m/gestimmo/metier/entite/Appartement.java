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

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author mombaye
 *
 * Classe représentant un appartement
 * dans un bien
 */
@Entity
@Table(name="APPARTEMENT")
@Getter @Setter
@ToString(exclude= {"reservations", "anomalies"})
public class Appartement extends Identifiant<Long> implements Serializable {

	private static final long serialVersionUID = -4633161879749573165L;

	@Column(name="libelle", nullable=false)
	private String libelle;
	
	@Column(name="type_appart", nullable=false)
	private String type;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="id_bien", nullable=false)
	private Bien bien;
	
	@Column(name="prix", nullable=false)
	private Double prix;

	@ManyToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinTable(name = "assoc_resa_appart", joinColumns = {
			@JoinColumn(name = "id_appartement", nullable = false, updatable = false) },
	inverseJoinColumns = { @JoinColumn(name = "id_reservation",
	nullable = false, updatable = false) })
	private List<Reservation> reservations;
	
	@OneToMany(fetch=FetchType.LAZY, orphanRemoval=true, mappedBy="appartement")
	private List<Anomalie> anomalies;
	
	
}
