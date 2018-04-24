package com.ag2m.gestimmo.metier.daoimpl;


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.ag2m.gestimmo.metier.dao.UtilisateurDao;
import com.ag2m.gestimmo.metier.entite.Utilisateur;

/**
 * @author mombaye
 *
 */
@Repository
public class UtilisateurDaoImpl extends AbstractDao<Long, Utilisateur>implements UtilisateurDao {

	
	@Override
	public Utilisateur findByUsername(String username) {

		CriteriaBuilder criteriaBuilder = getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<Utilisateur> criteria = criteriaBuilder.createQuery(Utilisateur.class);
		Root<Utilisateur> user = criteria.from(Utilisateur.class);
		criteria.select(user);
		user.join(("roles"));
		
		Predicate condition = criteriaBuilder.like(user.get("username"), username);
		
		criteria.where(condition);
		
		return getCurrentSession().createQuery(criteria).getSingleResult();
	}

}
