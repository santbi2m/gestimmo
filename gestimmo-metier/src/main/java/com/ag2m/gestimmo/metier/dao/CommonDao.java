package com.ag2m.gestimmo.metier.dao;

import java.io.Serializable;
import java.util.List;
import com.ag2m.gestimmo.metier.entite.Identifiant;


public interface CommonDao<ID extends Serializable, T extends Identifiant<ID>> {

	/**
	 * charge une entité via son id
	 * 
	 * @param id identifiant de l'entité à charger
	 * @param clazz class de l'entité
	 * 
	 * @return l'entité dont l'id est en paramètre
	 */
	public T findById(Class<T> clazz, ID id);
	
	/**
	 * Charge toutes les entités T 
	 * disponibles en BDD
	 * 
	 *  @param clazz class de l'entité
	 * @return tous les Objets T en BDD
	 */
	public List<T> findAll(Class<T> clazz);
	
	/**
	 * sauvegarde ou met à jour une entité T en base
	 * 
	 * @param entite
	 * @return
	 */
	public boolean saveOrUpdate(T entite);
	
	/**
	 * Supprime l'entité en entrée
	 * 
	 * @param entite
	 * @return true si l'entité a été supprimée et false autrement
	 */
	public boolean delete(T entite);
}
