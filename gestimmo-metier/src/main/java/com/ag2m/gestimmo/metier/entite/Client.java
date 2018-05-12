/**
 * 
 */
package com.ag2m.gestimmo.metier.entite;

import java.io.Serializable;
import java.util.List;

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
 * Entité représentant un client
 * d'un établissement
 */
@Entity
@Table(name="CLIENT")
@Getter @Setter 
@ToString(exclude= {"reservations", "factures"})
public class Client extends Identifiant<Long> implements Serializable {

	private static final long serialVersionUID = 7098095077896422587L;

	
	@Column(name="nom", nullable=false)
	private String nom;
	
	@Column(name="prenom", nullable=false)
	private String prenom;
	
	@Column(name="adresse_email", nullable=false)
	private String adresseEmail;
	
	@Column(name="numero_piece", nullable=false)
	private String numeroPieceIdentite;
	
	@Column(name="type_piece", nullable=false)
	private String typePieceIdentite;
	
	@Column(name="telephone")
	private String telephone;
	
	@ManyToOne
	@JoinColumn(name="id_adresse", nullable=false)
	private Adresse adresse;
	
	@OneToMany(fetch=FetchType.LAZY, orphanRemoval=true, mappedBy="client")
	private List<Reservation> reservations;
	
	@OneToMany(fetch=FetchType.LAZY, orphanRemoval=true, mappedBy="client")
	private List<Facture> factures;
}
