package com.ag2m.gestimmo.metier.daoimpl;


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ag2m.gestimmo.metier.dao.ParametrageDao;
import com.ag2m.gestimmo.metier.entite.referentiel.Taxe;
import com.ag2m.gestimmo.metier.exception.FunctionalException;

@Repository
public class ParametrageDaoImpl implements ParametrageDao {

	@Autowired
	SessionFactory session;
	

	/* (non-Javadoc)
	 * @see com.ag2m.gestimmo.metier.dao.ParametrageDao#loadCurrentTaxe()
	 */
	@Override
	public Taxe loadCurrentTaxe() {

		//Initialiser un builder de requête
		CriteriaBuilder criteriaBuilder = getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<Taxe> criteria = criteriaBuilder.createQuery(Taxe.class);
		
		//select from Reservation
		Root<Taxe> taxe = criteria.from(Taxe.class);
		criteria.select(taxe);
		
		//Conditions de filtre sur les paramètres d'entrée
		
		//Date de validité
		LocalDateTime currentDate = LocalDateTime.now();
		Predicate periodeValidite = criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(taxe.get("dateDebutValidite"), 
				currentDate), taxe.get("dateFinValite").isNull());
			
			criteria.where(periodeValidite);
			
		return  getCurrentSession().createQuery(criteria).uniqueResult();
	}
	
	
	

	/* (non-Javadoc)
	 * @see com.ag2m.gestimmo.metier.dao.ParametrageDao#loadTaxeByDate(org.joda.time.LocalDateTime)
	 */
	@Override
	public Taxe loadTaxeByDate(LocalDateTime date) throws FunctionalException {
		
		//Initialiser un builder de requête
		CriteriaBuilder criteriaBuilder = getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<Taxe> criteria = criteriaBuilder.createQuery(Taxe.class);
		
		//select from Reservation
		Root<Taxe> taxe = criteria.from(Taxe.class);
		criteria.select(taxe);
		
		//Conditions de filtre sur les paramètres d'entrée
		
		//Date de validité
		Predicate periodeValidite = criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(taxe.get("dateDebutValidite"), 
				date), criteriaBuilder.greaterThanOrEqualTo(taxe.get("dateFinValite"), 
						date));
			
			criteria.where(periodeValidite);
			
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
