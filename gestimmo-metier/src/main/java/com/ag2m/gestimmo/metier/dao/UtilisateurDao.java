package com.ag2m.gestimmo.metier.dao;

import com.ag2m.gestimmo.metier.entite.Utilisateur;

/**
 * 
 * @author mombaye
 *
 */
public interface UtilisateurDao {

	Utilisateur findByUsername(String username);
}
