package com.ag2m.gestimmo.metier.daoimpl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.ag2m.gestimmo.metier.dao.BienDao;
import com.ag2m.gestimmo.metier.entite.Bien;
import com.ag2m.gestimmo.metier.exception.FunctionalException;
import com.ag2m.gestimmo.metier.ioparam.BienCriteria;

/**
 * @author mombaye
 *
 */
@Repository
public class BienDaoImpl extends AbstractDao<Long, Bien> implements BienDao {

	@Override
	@Cacheable
	public List<Bien> findBienByCriteria(BienCriteria bienCriteria) throws FunctionalException {
		
			List<Predicate> predicates = new ArrayList<>();
			//Initialiser un builder de requête
			CriteriaBuilder criteriaBuilder = getCurrentSession().getCriteriaBuilder();
			CriteriaQuery<Bien> criteria = criteriaBuilder.createQuery(Bien.class);
			
			//select from appartement
			Root<Bien> biens = criteria.from(Bien.class);
			criteria.select(biens);
			
			//Conditions de filtre sur les paramètres d'entrée
			if(StringUtils.isNotEmpty(bienCriteria.getLibelle())) {
				Predicate libelleCondition = criteriaBuilder.like(criteriaBuilder.upper(biens.get("libelle")), bienCriteria.getLibelle().toUpperCase());
				predicates.add(libelleCondition);
			}
			
			if(StringUtils.isNotEmpty(bienCriteria.getAdresse())) {
				Predicate adresseCondition = criteriaBuilder.like(criteriaBuilder.upper(biens.get("adresse").get("adresse")), bienCriteria.getAdresse().toUpperCase());
				predicates.add(adresseCondition);
			}
			
			if(bienCriteria.getCodePostal() != null) {
				Predicate codePostalCondition = criteriaBuilder.and(criteriaBuilder.equal(biens.get("adresse").get("codePostal"), bienCriteria.getCodePostal()));
				predicates.add(codePostalCondition);
			}
			
			if(StringUtils.isNotEmpty(bienCriteria.getVille())) {
				Predicate villeCondition = criteriaBuilder.like(criteriaBuilder.upper(biens.get("adresse").get("ville")), bienCriteria.getVille().toUpperCase());
				predicates.add(villeCondition);
			}
			
			if(StringUtils.isNotEmpty(bienCriteria.getPays())) {
				Predicate paysCondition = criteriaBuilder.like(criteriaBuilder.upper(biens.get("adresse").get("pays")), bienCriteria.getPays().toUpperCase());
				predicates.add(paysCondition);
			}
			//where 
			if(!predicates.isEmpty()) {
				Predicate[] allConditions = predicates.toArray(new Predicate[predicates.size()]);
				criteria.where(allConditions);
			}
			
			return getCurrentSession().createQuery(criteria).getResultList();
		}

}
