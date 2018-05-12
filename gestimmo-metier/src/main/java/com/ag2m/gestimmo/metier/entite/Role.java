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

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author mombaye
 * Entite representant le rôle d'un 
 * utilisateur
 */
@Entity
@Table(name="ROLES")
@Getter @Setter @ToString
public class Role extends Identifiant<Long> implements Serializable {

	private static final long serialVersionUID = 7582101515396779449L;

	@Column(name="role", nullable=false)
	private String role;
	
	@ManyToOne
	@JoinColumn(name="username", referencedColumnName="username", nullable=false)
	private Utilisateur utilisateur;

}
