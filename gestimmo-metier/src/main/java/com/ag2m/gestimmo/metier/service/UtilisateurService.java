package com.ag2m.gestimmo.metier.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.ag2m.gestimmo.metier.dto.UtilisateurDto;


/**
 * 
 * @author mombaye
 *
 */
public interface UtilisateurService extends UserDetailsService {

	/**
	 * Sauvegarde ou met à jour l'utilisateur en paramètre
	 * 
	 * @param entite
	 * @return
	 */
	public UtilisateurDto saveOrUpdate(UtilisateurDto entite);
	
	
	/**
	 * Retrouve l'entité dont l'id est passé en paramètre
	 * 
	 * @param id
	 * @return
	 */
	public UtilisateurDto findById(Long id);
	
	/**
	 * Retourne une liste d'entités
	 * 
	 * @return
	 */
	public List<UtilisateurDto> findAll();
	
	/**
	 * Permet de supprimer l'entité en entrée
	 * 
	 * @param entite
	 * @return
	 */
	public boolean delete(UtilisateurDto entite);
	
}
