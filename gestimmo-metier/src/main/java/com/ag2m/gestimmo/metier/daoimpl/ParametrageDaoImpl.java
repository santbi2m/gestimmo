package com.ag2m.gestimmo.metier.daoimpl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ag2m.gestimmo.metier.dao.ParametrageDao;
import com.ag2m.gestimmo.metier.entite.referentiel.Taxe;

@Repository
public class ParametrageDaoImpl implements ParametrageDao {

	@Autowired
	SessionFactory session;
	

	@Override
	public Taxe loadTaxe() {
		Taxe taxe =  (Taxe) getCurrentSession()
				.createQuery("from "+ Taxe.class.getName()).uniqueResult();
		
		return taxe;
	}

	
	/**
	 * Retourne la seesion courrante
	 * 
	 * @return session
	 */
	protected Session getCurrentSession(){
		
		return session.getCurrentSession();
	}

}
