/**
 * 
 */
package com.ag2m.gestimmo.metier.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author mombaye
 *
 * Dto représentant un utilisateur
 * de l'application
 */
@Getter @Setter 
@ToString(exclude = {"roles"})
public class UtilisateurDto extends IdentifiantDto implements Serializable {

	private static final long serialVersionUID = -6648817804281114271L;


	private String username;
	
	private String password;
	
	private boolean enabled;
	
	private List<RoleDto> roles;
	
	private String nom;
	
	private String prenom;
	
	private String adresseEmail;

}
