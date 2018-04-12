package com.ag2m.gestimmo.metier.daoimpl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ag2m.gestimmo.metier.dao.UtilisateurDao;
import com.ag2m.gestimmo.metier.entite.Utilisateur;

/**
 * @author mombaye
 *
 */
@Repository
public class UtilisateurDaoImpl implements UtilisateurDao {

	
	@Autowired
	SessionFactory session;
	
	@Override
	public Utilisateur findByUsername(String username) {

		return session.getCurrentSession().find(Utilisateur.class, username);
	}

}
