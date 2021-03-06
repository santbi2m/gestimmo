package com.ag2m.gestimmo.metier.exception;

import lombok.NoArgsConstructor;

/**
 * Classe personnalisée permettant d'encapsuler
 * toutes les exceptions fonctionnelles.
 * 
 * @author mombaye
 *
 */
@NoArgsConstructor
public class FunctionalException extends RuntimeException {

	private static final long serialVersionUID = 390557600009674348L;
	
	
	/**
	 * Permet de lancer une exception 
	 * avec un message personnalisé
	 * 
	 * @param message
	 */
	 public FunctionalException (String message) {
	        super (message);
	    }

	 /**
	  * Permet de lancer une exception
	  * fonctionnel avec un objet Throwable
	  * qui représente l'origine de l'exception
	  * 
	  * @param cause
	  */
	 public FunctionalException (Throwable cause) {
	        super (cause);
	 	}

	  
	 /**
	  * Permet de lancer une exception
	  * fonctionnel avec un objet Throwable
	  * qui représente l'origine de l'exception,
	  * et un message d'erreur personnalisé.
	  * 
	  * @param message
	  * @param cause
	  */
	 public FunctionalException (String message, Throwable cause) {
	        super (message, cause);
	    }

}
