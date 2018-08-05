package com.ag2m.gestimmo.metier.daoimpl;

import org.joda.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.ag2m.gestimmo.metier.dao.AnomalieDao;
import com.ag2m.gestimmo.metier.entite.Anomalie;
import com.ag2m.gestimmo.metier.exception.FunctionalException;



@Repository
public class AnomalieDaoImpl extends AbstractDao<Long, Anomalie> implements AnomalieDao{

	/**
	 *  Permet de combiner tous les critères possibles
	 *  de recherche d’anomalie, de retourner 
	 *  le résultat et le met dans le cache.
	 *  
	 * @param titre
	 * @param statut
	 * @param dateOuverture
	 * @param dateTraitement
	 * @param idAppartement
	 * @return
	 * @throws FunctionalException 
	 */
	@Override
	@Cacheable
	public List<Anomalie> findAnomalieByCriteria(String titre, 
			String statut, LocalDateTime dateOuverture, LocalDateTime dateTraitement,
			Long idAppartement){
		
		List<Predicate> predicates = new ArrayList<>();
		
		//Initialiser un builder de requête
		CriteriaBuilder criteriaBuilder = getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<Anomalie> criteria = criteriaBuilder.createQuery(Anomalie.class);
		
		//select from ANOMALIE
		Root<Anomalie> anomalies = criteria.from(Anomalie.class);
		criteria.select(anomalies);
		
		//Conditions de filtre sur les paramètres d'entrée
		if(StringUtils.isNotEmpty(titre)) {
			Predicate titreCondition = criteriaBuilder.like(criteriaBuilder.upper(anomalies.get("titre")), titre.toUpperCase());
			predicates.add(titreCondition);
		}
		
		if(StringUtils.isNotEmpty(statut) ) {
			Predicate statutCondition = criteriaBuilder.and(criteriaBuilder.equal(anomalies.get("statut"), statut));
			predicates.add(statutCondition);
		}
		
		if(dateOuverture!=null) {
			Predicate dateOuvertureCondition = criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(anomalies.get("dateOuverture"), dateOuverture) );
			predicates.add(dateOuvertureCondition);
		}
		
		//where condition1 and condition2 and condition3
		if(!predicates.isEmpty()) {
			Predicate[] allConditions = predicates.toArray(new Predicate[predicates.size()]);
			criteria.where(allConditions);
		}
		
		return getCurrentSession().createQuery(criteria).getResultList();		
	
	}
}
