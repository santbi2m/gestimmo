/**
 * 
 */
package com.ag2m.gestimmo.metier.entite;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * @author mombaye
 * 
 * Classe commune contenant les attribut
 * commune des entités, comme l'id par exemple
 */
@MappedSuperclass
@Getter @Setter @ToString
public abstract class Identifiant<ID extends Serializable> {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private ID id;

}
