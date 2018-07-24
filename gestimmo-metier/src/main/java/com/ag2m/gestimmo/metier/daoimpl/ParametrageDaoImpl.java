package com.ag2m.gestimmo.metier.daoimpl;



import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ag2m.gestimmo.metier.constants.TechnicalErrorMessageConstants;
import com.ag2m.gestimmo.metier.dao.ParametrageDao;
import com.ag2m.gestimmo.metier.entite.referentiel.Parametrage;
import com.ag2m.gestimmo.metier.entite.referentiel.Remise;
import com.ag2m.gestimmo.metier.exception.FunctionalException;

@Repository
public class ParametrageDaoImpl implements ParametrageDao {

	@Autowired
	SessionFactory session;
	

	/* (non-Javadoc)
	 * @see com.ag2m.gestimmo.metier.dao.ParametrageDao#loadAllRemise()
	 */
	@Override
	public List<Remise> loadAllRemise() {
		//Initialiser un builder de requête
		CriteriaBuilder criteriaBuilder = getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<Remise> criteria = criteriaBuilder.createQuery(Remise.class);
		
		//select from Remise
		Root<Remise> remise = criteria.from(Remise.class);
		criteria.select(remise);
		
		return  getCurrentSession().createQuery(criteria).list();
	}
	
	
	/* (non-Javadoc)
	 * @see com.ag2m.gestimmo.metier.dao.ParametrageDao#loadParametrageByType(java.lang.String)
	 */
	@Override
	public Parametrage loadParametrageByType(String type) {
		
		//Il faut que le type soit renseigné
		Optional.ofNullable(type).filter(typ -> !typ.isEmpty()).orElseThrow(() 
				 -> new FunctionalException(TechnicalErrorMessageConstants.PARAMETRAGE_TYPE_NULL));

		//Initialiser un builder de requête
		CriteriaBuilder criteriaBuilder = getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<Parametrage> criteria = criteriaBuilder.createQuery(Parametrage.class);
		
		//select from Parametrage
		Root<Parametrage> parametrage = criteria.from(Parametrage.class);
		criteria.select(parametrage);
		criteria.where(criteriaBuilder.like(parametrage.get("type"), type));
		
		return  getCurrentSession().createQuery(criteria).uniqueResult();
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
