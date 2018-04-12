/**
 * 
 */
package com.ag2m.gestimmo.metier.entite;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author mombaye
 *
 * Classe représentant un bien immobiler
 */
@Entity
@Table(name="BIEN")
public class Bien extends Identifiant<Long> implements Serializable{

	private static final long serialVersionUID = -6162562418965591611L;

	@Column(name="libelle")
	private String libelle;
	
	@Column(name="adresse", nullable = false)
	private String adresse;
	
	@Column(name="complement_adresse")
	private String complementAdresse;
	
	@Column(name="code_postal", nullable = false)
	private Integer codePostal;
	
	@Column(name="ville", nullable = false)
	private String ville;
	
	@Column(name="pays", nullable = false)
	private String pays;
	
	
	@OneToMany(fetch=FetchType.LAZY, orphanRemoval=true, mappedBy="bien")
	private List<Appartement> appartements;
	
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
	 * @return the adresse
	 */
	public String getAdresse() {
		return adresse;
	}

	/**
	 * @param adresse the adresse to set
	 */
	public void setAdresse(String adresse) {
		this.adresse = adresse;
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

	/**
	 * @return the complementAdresse
	 */
	public String getComplementAdresse() {
		return complementAdresse;
	}

	/**
	 * @param complementAdresse the complementAdresse to set
	 */
	public void setComplementAdresse(String complementAdresse) {
		this.complementAdresse = complementAdresse;
	}

	/**
	 * @return the codePostal
	 */
	public Integer getCodePostal() {
		return codePostal;
	}

	/**
	 * @param codePostal the codePostal to set
	 */
	public void setCodePostal(Integer codePostal) {
		this.codePostal = codePostal;
	}

	/**
	 * @return the ville
	 */
	public String getVille() {
		return ville;
	}

	/**
	 * @param ville the ville to set
	 */
	public void setVille(String ville) {
		this.ville = ville;
	}

	/**
	 * @return the pays
	 */
	public String getPays() {
		return pays;
	}

	/**
	 * @param pays the pays to set
	 */
	public void setPays(String pays) {
		this.pays = pays;
	}

	
}
