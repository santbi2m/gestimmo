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
 * Entité représentant un utilisateur
 * de l'application
 */
@Entity
@Table(name="UTILISATEURS")
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


	
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	
	/**
	 * @return the roles
	 */
	public List<Role> getRoles() {
		return roles;
	}

	/**
	 * @param roles the roles to set
	 */
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

}
