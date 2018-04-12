/**
 * 
 */
package com.ag2m.gestimmo.metier.entite;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;


/**
 * @author mombaye
 *
 */
@Entity
@Table(name="ANOMALIE")
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
	
	@ManyToOne
	@JoinColumn(name="id_appartement", nullable=false)
	private Appartement appartement;
	
	@Column(name="commentaire")
	private String commentaire;

	/**
	 * @return the titre
	 */
	public String getTitre() {
		return titre;
	}

	/**
	 * @param titre the titre to set
	 */
	public void setTitre(String titre) {
		this.titre = titre;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
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
	 * @return the dateOuverture
	 */
	public LocalDateTime getDateOuverture() {
		return dateOuverture;
	}

	/**
	 * @param dateOuverture the dateOuverture to set
	 */
	public void setDateOuverture(LocalDateTime dateOuverture) {
		this.dateOuverture = dateOuverture;
	}

	/**
	 * @return the dateTraitement
	 */
	public LocalDateTime getDateTraitement() {
		return dateTraitement;
	}

	/**
	 * @param dateTraitement the dateTraitement to set
	 */
	public void setDateTraitement(LocalDateTime dateTraitement) {
		this.dateTraitement = dateTraitement;
	}

	/**
	 * @return the appartement
	 */
	public Appartement getAppartement() {
		return appartement;
	}

	/**
	 * @param appartement the appartement to set
	 */
	public void setAppartement(Appartement appartement) {
		this.appartement = appartement;
	}

	/**
	 * @return the commentaire
	 */
	public String getCommentaire() {
		return commentaire;
	}

	/**
	 * @param commentaire the commentaire to set
	 */
	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}
	
}
