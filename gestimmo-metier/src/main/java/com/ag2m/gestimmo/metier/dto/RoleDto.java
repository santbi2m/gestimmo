/**
 * 
 */
package com.ag2m.gestimmo.metier.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author mombaye
 * Dto representant le role d'un 
 * utilisateur
 */
@Getter @Setter @ToString
public class RoleDto extends IdentifiantDto implements Serializable {

	private static final long serialVersionUID = -5129818836381313815L;

	private String role;
	
	private UtilisateurDto utilisateur;


}
