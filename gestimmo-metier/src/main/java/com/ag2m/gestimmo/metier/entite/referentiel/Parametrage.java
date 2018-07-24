/**
 * 
 */
package com.ag2m.gestimmo.metier.entite.referentiel;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ag2m.gestimmo.metier.entite.Identifiant;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author mombaye
 *
 * Classe représentant les valeurs paramétrées en BDD
 */
@Entity
@Table(name="PARAMETRAGE")
@Getter @Setter @ToString
public class Parametrage extends Identifiant<Long> implements Serializable{

	private static final long serialVersionUID = 5459991556891864361L;

	@Column(name="type")
	private String type;
	
	@Column(name="valeur")
	private String valeur;
	
	@Column(name="description")
	private String description;
	
	
}
