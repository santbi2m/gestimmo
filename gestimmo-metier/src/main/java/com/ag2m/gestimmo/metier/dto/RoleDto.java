/**
 * 
 */
package com.ag2m.gestimmo.metier.dto;

import java.io.Serializable;

/**
 * @author mombaye
 * Dto representant le role d'un 
 * utilisateur
 */
public class RoleDto extends IdentifiantDto implements Serializable {

	private static final long serialVersionUID = -5129818836381313815L;

	private String role;
	
	private UtilisateurDto utilisateur;


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
	public UtilisateurDto getUtilisateur() {
		return utilisateur;
	}


	/**
	 * @param utilisateur the utilisateur to set
	 */
	public void setUtilisateur(UtilisateurDto utilisateur) {
		this.utilisateur = utilisateur;
	}
	
	
}
