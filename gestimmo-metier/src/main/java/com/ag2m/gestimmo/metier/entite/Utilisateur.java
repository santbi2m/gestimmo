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
 * Entité représentant un utilisateur
 * de l'application
 */
@Entity
@Table(name="UTILISATEURS")
@Getter @Setter 
@ToString(exclude = {"roles"})
public class Utilisateur extends Identifiant<Long> implements Serializable {

	private static final long serialVersionUID = -5995533176922319510L;
	
	@Column(name="username", unique=true)
	private String username;
	
	@Column(name="password", nullable=false)
	private String password;
	
	@Column(name="enabled", nullable=false)
	private boolean enabled;
	
	@OneToMany(fetch=FetchType.LAZY, orphanRemoval=true, mappedBy="utilisateur")
	private List<Role> roles;
	
	@Column(name="nom", nullable=false)
	private String nom;
	
	@Column(name="prenom", nullable=false)
	private String prenom;
	
	@Column(name="adresse_email", nullable=false)
	private String adresseEmail;

}
