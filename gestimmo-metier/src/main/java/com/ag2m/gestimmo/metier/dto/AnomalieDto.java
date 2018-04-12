/**
 * 
 */
package com.ag2m.gestimmo.metier.dto;

import java.io.Serializable;

import org.joda.time.LocalDateTime;


/**
 * @author mombaye
 *
 */
public class AnomalieDto extends IdentifiantDto implements Serializable {


	private static final long serialVersionUID = 8737821249022500604L;

	private String titre;
	
	private String description;
	
	private String statut;
	
	private LocalDateTime dateOuverture;
	
	private LocalDateTime dateTraitement;
	
	private AppartementDto appartement;
	
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
	public AppartementDto getAppartement() {
		return appartement;
	}

	/**
	 * @param appartement the appartement to set
	 */
	public void setAppartement(AppartementDto appartement) {
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
