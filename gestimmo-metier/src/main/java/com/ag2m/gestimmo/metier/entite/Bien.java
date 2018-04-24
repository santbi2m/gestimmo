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

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author mombaye
 *
 * Classe représentant un bien immobiler
 */
@Entity
@Table(name="BIEN")
@Getter @Setter @ToString(exclude= {"appartements"})
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
	
}
