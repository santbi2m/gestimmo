/**
 * 
 */
package com.ag2m.gestimmo.metier.service;

import java.util.List;

import com.ag2m.gestimmo.metier.dto.RoleDto;

/**
 * @author mombaye
 *
 */
public interface RoleService {

	/**
	 * Retrouve l'entité dont l'id est passé en paramètre
	 * 
	 * @param id
	 * @return
	 */
	public RoleDto findById(Long id);
	
	/**
	 * Retourne une liste d'entités
	 * 
	 * @return
	 */
	public List<RoleDto> findAll();
	
	/**
	 * Sauvegarde ou met à jour l'entité en paramètre
	 * 
	 * @param entite
	 * @return
	 */
	public RoleDto saveOrUpdate(RoleDto entite);
	
	/**
	 * Permet de supprimer l'entité en entrée
	 * 
	 * @param entite
	 * @return
	 */
	public boolean delete(RoleDto entite);
}
