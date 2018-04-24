package com.ag2m.gestimmo.metier.dao;

import com.ag2m.gestimmo.metier.entite.Utilisateur;

/**
 * 
 * @author mombaye
 *
 */
public interface UtilisateurDao extends CommonDao<Long, Utilisateur>{

	/**
	 * findByUsername utilisé par Spring security
	 * pour retrouver l'utilisateur et l'authentifier
	 * 
	 * @param username
	 * @return
	 */
	Utilisateur findByUsername(String username);
	
}
