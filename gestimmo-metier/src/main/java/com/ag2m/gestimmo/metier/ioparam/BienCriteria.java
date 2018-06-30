package com.ag2m.gestimmo.metier.ioparam;

import java.util.List;

import org.joda.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author asouane
 * 
 * Classe contenant les critères de recherche 
 * d'appartements
 */
@Getter @Setter
@ToString
public class BienCriteria  {


	
	private String libelle;
	private String adresse;
	private String complement;
	private Integer codePostal;  
	private String ville; 
	private String pays;
}
