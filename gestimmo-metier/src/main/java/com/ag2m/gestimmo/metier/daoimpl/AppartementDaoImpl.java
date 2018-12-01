package com.ag2m.gestimmo.metier.daoimpl;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.ag2m.gestimmo.metier.dao.AppartementDao;
import com.ag2m.gestimmo.metier.entite.Appartement;

/**
 * @author mombaye
 *
 */
@Repository
public class AppartementDaoImpl extends AbstractDao<Long, Appartement> implements AppartementDao{

	@Override
	@Cacheable
	public List<Appartement> findAppartementByCriteria(String libelle, String type, Long idBien) {
		
		List<Predicate> predicates = new ArrayList<>();
		//Initialiser un builder de requête
		CriteriaBuilder criteriaBuilder = getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<Appartement> criteria = criteriaBuilder.createQuery(Appartement.class);
		
		//select from appartement
		Root<Appartement> appartements = criteria.from(Appartement.class);
		criteria.select(appartements);
		
		//Conditions de filtre sur les paramètres d'entrée
		if(StringUtils.isNotEmpty(libelle)) {
			Predicate libelleCondition = criteriaBuilder.like(criteriaBuilder.upper(appartements.get("libelle")), "%"+libelle.toUpperCase()+"%");
			predicates.add(libelleCondition);
		}
		
		if(StringUtils.isNotEmpty(type)) {
			Predicate typeCondition = criteriaBuilder.and(criteriaBuilder.equal(appartements.get("type"), type));
			predicates.add(typeCondition);
		}
		
		if(idBien != null) {
			Predicate bienCondition = criteriaBuilder.and(criteriaBuilder.equal(appartements.get("bien").get("id"), idBien));
			predicates.add(bienCondition);
		}
		//where  condition1 and condition2 and condition3
		if(!predicates.isEmpty()) {
			Predicate[] allConditions = predicates.toArray(new Predicate[predicates.size()]);
			criteria.where(allConditions);
		}
		
		return getCurrentSession().createQuery(criteria).getResultList();
	}
	
	
	@Override
	@Cacheable
	public List<Appartement> findAppartByReservation(Long idReservation){
		
		//Initialiser un builder de requête
		CriteriaBuilder criteriaBuilder = getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<Appartement> criteria = criteriaBuilder.createQuery(Appartement.class);
		
		//select from appartement
		Root<Appartement> appartements = criteria.from(Appartement.class);
		criteria.select(appartements);
		
		//Join Reservations
		Join<Object, Object> reservations = appartements.join("reservations");
		
		//Conditions de filtre sur les paramètres d'entrée
		criteria.where(criteriaBuilder.equal(reservations.get("id"), idReservation));
		
		return getCurrentSession().createQuery(criteria).getResultList();
	}
	

}
