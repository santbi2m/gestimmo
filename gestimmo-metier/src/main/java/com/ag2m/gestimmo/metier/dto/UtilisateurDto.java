/**
 * 
 */
package com.ag2m.gestimmo.metier.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author mombaye
 *
 * Dto représentant un utilisateur
 * de l'application
 */
public class UtilisateurDto extends IdentifiantDto implements Serializable {

	private static final long serialVersionUID = -6648817804281114271L;


	private String username;
	
	private String password;
	
	private boolean enabled;
	
	private List<RoleDto> roles;


	
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
	public List<RoleDto> getRoles() {
		return roles;
	}

	/**
	 * @param roles the roles to set
	 */
	public void setRoles(List<RoleDto> roles) {
		this.roles = roles;
	}

}
