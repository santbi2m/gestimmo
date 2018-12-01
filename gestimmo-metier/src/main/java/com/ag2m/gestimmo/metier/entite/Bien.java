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
import javax.persistence.ManyToOne;
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
	
	@OneToMany(fetch=FetchType.LAZY, orphanRemoval=true, mappedBy="bien")
	private List<Appartement> appartements;
	
	@ManyToOne (cascade = {CascadeType.MERGE, CascadeType.ALL})
	@JoinColumn(name="id_adresse", nullable=false)
	private Adresse adresse;
	
}
