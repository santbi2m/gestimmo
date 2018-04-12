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

/**
 * @author mombaye
 * Entite representant le role d'un 
 * utilisateur
 */
@Entity
@Table(name="ROLES")
public class Role extends Identifiant<Long> implements Serializable {

	private static final long serialVersionUID = 7582101515396779449L;

	@Column(name="role", nullable=false)
	private String role;
	
	
	@ManyToOne
	@JoinColumn(name="username", nullable=false)
	private Utilisateur utilisateur;


	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}


	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}


	/**
	 * @return the utilisateur
	 */
	public Utilisateur getUtilisateur() {
		return utilisateur;
	}


	/**
	 * @param utilisateur the utilisateur to set
	 */
	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}
	
	
}
